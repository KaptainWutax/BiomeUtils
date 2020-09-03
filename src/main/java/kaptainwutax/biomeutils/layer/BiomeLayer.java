package kaptainwutax.biomeutils.layer;

import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.seed.SeedMixer;

public abstract class BiomeLayer {

    private final MCVersion version;
    private final BiomeLayer[] parents;

    public long salt;
    public long layerSeed;
    public long localSeed;

    protected int scale = -1;
    protected int layerId = -1;

    private final LayerCache layerCache = new LayerCache(1024);

    public BiomeLayer(MCVersion version, BiomeLayer... parents) {
        this.version = version;
        this.parents = parents;
    }

    public BiomeLayer(MCVersion version) {
        this(version, (BiomeLayer)null);
    }

    public BiomeLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
        this(version, parents);
        this.salt = salt;
        this.layerSeed = getLayerSeed(worldSeed, this.salt);
    }

    public BiomeLayer(MCVersion version, long worldSeed, long salt) {
        this(version, worldSeed, salt, (BiomeLayer)null);
    }

    public MCVersion getVersion() {
        return this.version;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setLayerId(int layerId) {
        this.layerId = layerId;
    }

    public int getScale() {
        return this.scale;
    }

    public boolean hasParent() {
        return this.parents.length > 0;
    }

    public int getLayerId() {
        return layerId;
    }

    public BiomeLayer getParent() {
        return this.getParent(0);
    }

    public BiomeLayer getParent(int id) {
        return this.parents[id];
    }

    public boolean isMergingLayer() {
        return this.parents.length > 1;
    }

    public BiomeLayer[] getParents() {
        return this.parents;
    }

    public int get(int x, int y, int z) {
        return this.layerCache.get(x, y, z, this::sample);
    }

    public abstract int sample(int x, int y, int z);

    public static long getLayerSeed(long worldSeed, long salt) {
        long midSalt = SeedMixer.mixSeed(salt, salt);
        midSalt = SeedMixer.mixSeed(midSalt, salt);
        midSalt = SeedMixer.mixSeed(midSalt, salt);
        long layerSeed = SeedMixer.mixSeed(worldSeed, midSalt);
        layerSeed = SeedMixer.mixSeed(layerSeed, midSalt);
        layerSeed = SeedMixer.mixSeed(layerSeed, midSalt);
        return layerSeed;
    }

    public static long getLocalSeed(long layerSeed, int x, int z) {
        layerSeed = SeedMixer.mixSeed(layerSeed, x);
        layerSeed = SeedMixer.mixSeed(layerSeed, z);
        layerSeed = SeedMixer.mixSeed(layerSeed, x);
        layerSeed = SeedMixer.mixSeed(layerSeed, z);
        return layerSeed;
    }

    public static long getLocalSeed(long worldSeed, long salt, int x, int z) {
        return getLocalSeed(getLayerSeed(worldSeed, salt), x, z);
    }

    public void setSeed(int x, int z) {
        this.localSeed = BiomeLayer.getLocalSeed(this.layerSeed, x, z);
    }

    public int nextInt(int bound) {
        // warning for JDK lower than 1.9 its important to left the (long) cast
        // @formatter:off
        int i = (int)Math.floorMod(this.localSeed >> 24, (long)bound);
        // @formatter:on
        this.localSeed = SeedMixer.mixSeed(this.localSeed, this.layerSeed);
        return i;
    }

    public int choose(int a, int b) {
        return this.nextInt(2) == 0 ? a : b;
    }

    public int choose(int a, int b, int c, int d) {
        int i = this.nextInt(4);
        return i == 0 ? a : i == 1 ? b : i == 2 ? c : d;
    }

}
