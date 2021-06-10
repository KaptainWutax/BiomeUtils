package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.layer.cache.IntLayerCache;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class IntBiomeLayer extends BiomeLayer {

	private final IntLayerCache layerCache = new IntLayerCache(1024);

	public IntBiomeLayer(MCVersion version, BiomeLayer... parents) {
		super(version, parents);
	}

	public IntBiomeLayer(MCVersion version) {
		super(version);
	}

	public IntBiomeLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
	}

	public IntBiomeLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
	}

	public int get(int x, int y, int z) {
		return this.layerCache.get(x, y, z, this::sample);
	}

	public abstract int sample(int x, int y, int z);

	public void _sample(int x, int z, int xSize, int zSize) throws UnsupportedOperationException {
		if(this.getParents() == null) {
			throw new UnsupportedOperationException("Error, in our model, this should not happen (we use a null parent array)");
		}
		for(BiomeLayer biomeLayer : this.getParents()) {
			int shift = 0;
			if(this instanceof ScaleLayer) shift = 1;
			else if(this instanceof VoronoiLayer) shift = 2;
			if(biomeLayer == null) {
				for(int offX = 0; offX < xSize; offX++) {
					for(int offZ = 0; offZ < zSize; offZ++) {
						this.layerCache.forceStoreAndGet(x + offX, 0, z + offZ, this::sample);
					}
				}
				return;
			}

			((IntBiomeLayer)biomeLayer)._sample((x >> shift) - 1, (z >> shift) - 1, xSize + 2, zSize + 2);
			for(int offX = 0; offX < xSize; offX++) {
				for(int offZ = 0; offZ < zSize; offZ++) {
					this.layerCache.get(x + offX, 0, z + offZ, this::sample);
				}
			}
			return;
		}
	}

	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		try {
			this._sample(x, z, xSize, zSize);
		} catch(UnsupportedOperationException e) {
			e.printStackTrace();
		}
		int[] ids = new int[xSize * zSize];
		for(int offX = 0; offX < xSize; offX++) {
			for(int offZ = 0; offZ < zSize; offZ++) {
				ids[offX * zSize + offZ] = this.layerCache.get(x + offX, 0, z + offZ, this::sample);
			}
		}
		return ids;
	}

}
