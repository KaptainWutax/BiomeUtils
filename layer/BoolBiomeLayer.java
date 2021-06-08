package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.layer.cache.BoolLayerCache;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class BoolBiomeLayer extends BiomeLayer {

	private final BoolLayerCache layerCache = new BoolLayerCache(1024);

	public BoolBiomeLayer(MCVersion version, BiomeLayer... parents) {
		super(version, parents);
	}

	public BoolBiomeLayer(MCVersion version) {
		super(version);
	}

	public BoolBiomeLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
	}

	public BoolBiomeLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
	}

	public boolean get(int x, int y, int z) {
		return this.layerCache.get(x, y, z, this::sample);
	}

	public abstract boolean sample(int x, int y, int z);

	public boolean[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		throw new UnsupportedOperationException();
	}

}
