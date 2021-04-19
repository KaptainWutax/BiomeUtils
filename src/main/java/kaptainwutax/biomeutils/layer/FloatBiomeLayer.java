package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.layer.cache.FloatLayerCache;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class FloatBiomeLayer extends BiomeLayer {

    private final FloatLayerCache layerCache = new FloatLayerCache(1024);

    public FloatBiomeLayer(MCVersion version, BiomeLayer... parents) {
        super(version, parents);
    }

    public FloatBiomeLayer(MCVersion version) {
        super(version);
    }

    public FloatBiomeLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
        super(version, worldSeed, salt, parents);
    }

    public FloatBiomeLayer(MCVersion version, long worldSeed, long salt) {
        super(version, worldSeed, salt);
    }

    public float get(int x, int y, int z) {
        return this.layerCache.get(x, y, z, this::sample);
    }

    public abstract float sample(int x, int y, int z);

    public float[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
        throw new UnsupportedOperationException();
    }

}
