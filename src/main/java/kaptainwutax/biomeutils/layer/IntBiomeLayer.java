package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.layer.cache.IntLayerCache;
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
		int id = this.layerCache.get(x, y, z, this::sample);
		if (DEBUG) {
			System.out.printf("Layer id: %d at (x,z):(%d,%d), got parent id : %d%n", this.layerId, x, z, id);
		}
		return id;
	}

	public abstract int sample(int x, int y, int z);

	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		throw new UnsupportedOperationException();
	}

}
