package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.nether.NetherLayer;
import kaptainwutax.biomeutils.noise.MixedNoisePoint;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class NetherBiomeSource extends BiomeSource {

    private static final MixedNoisePoint[] DEFAULT_BIOME_POINTS = {
            new MixedNoisePoint(Biome.NETHER_WASTES, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
            new MixedNoisePoint(Biome.SOUL_SAND_VALLEY, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
            new MixedNoisePoint(Biome.CRIMSON_FOREST, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F),
            new MixedNoisePoint(Biome.WARPED_FOREST, 0.0F, 0.5F, 0.0F, 0.0F, 0.375F),
            new MixedNoisePoint(Biome.BASALT_DELTAS, -0.5F, 0.0F, 0.0F, 0.0F, 0.175F)
    };
    protected final LayerStack<BiomeLayer> layers = new LayerStack<>();
    private final MixedNoisePoint[] biomePoints;
    public NetherLayer full;
    public VoronoiLayer voronoi;
    private boolean threeDimensional;

    public NetherBiomeSource(MCVersion version, long worldSeed) {
        this(version, worldSeed, DEFAULT_BIOME_POINTS);
    }

    public NetherBiomeSource(MCVersion version, long worldSeed, MixedNoisePoint... biomePoints) {
        super(version, worldSeed);
        this.biomePoints = biomePoints;
        this.build();
    }

    @Override
    public LayerStack<BiomeLayer> getLayers() {
        return this.layers;
    }

    @Override
    public Dimension getDimension() {
        return Dimension.NETHER;
    }

    public NetherBiomeSource addDimension() {
        this.threeDimensional = true;
        this.full = null;
        this.layers.clear();
        this.build();
        return this;
    }

    protected void build() {
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_16)) {
            this.layers.add(this.full = new NetherLayer(this.getVersion(), this.getWorldSeed(), this.threeDimensional, this.biomePoints));
            this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), true, this.full));
        } else {
            this.layers.add(this.full = new NetherLayer(this.getVersion(), this.getWorldSeed(), false, null));
            this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full) {
                @Override
                public int sample(int x, int y, int z) {
                    return this.getVersion().isOlderThan(MCVersion.v1_16) ? Biome.NETHER_WASTES.getId() : super.sample(x, y, z);
                }
            });
        }

        this.layers.setScales();
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return Biome.REGISTRY.get(this.voronoi.get(x, y, z));
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return Biome.REGISTRY.get(this.full.get(x, y, z));
    }

}
