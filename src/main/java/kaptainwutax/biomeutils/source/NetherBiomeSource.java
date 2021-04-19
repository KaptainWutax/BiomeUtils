package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.nether.NetherLayer;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;

public class NetherBiomeSource extends LayeredBiomeSource<IntBiomeLayer> {

    private static final Biome.BiomePoint[] DEFAULT_BIOME_POINTS = {
            new Biome.BiomePoint(Biome.NETHER_WASTES, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
            new Biome.BiomePoint(Biome.SOUL_SAND_VALLEY, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
            new Biome.BiomePoint(Biome.CRIMSON_FOREST, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F),
            new Biome.BiomePoint(Biome.WARPED_FOREST, 0.0F, 0.5F, 0.0F, 0.0F, 0.375F),
            new Biome.BiomePoint(Biome.BASALT_DELTAS, -0.5F, 0.0F, 0.0F, 0.0F, 0.175F)
    };
    protected final LayerStack<IntBiomeLayer> layers = new LayerStack<>();
    private final Biome.BiomePoint[] biomePoints;
    public NetherLayer full;
    public VoronoiLayer voronoi;
    private boolean threeDimensional;

    public NetherBiomeSource(MCVersion version, long worldSeed) {
        this(version, worldSeed, DEFAULT_BIOME_POINTS);
    }

    public NetherBiomeSource(MCVersion version, long worldSeed, Biome.BiomePoint... biomePoints) {
        super(version, worldSeed);
        this.biomePoints = biomePoints;
        this.build();
    }

    @Override
    public LayerStack<IntBiomeLayer> getLayers() {
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
    public Biome getBiome(BPos bpos) {
        return Biome.REGISTRY.get(this.voronoi.get(bpos.getX(), bpos.getY(), bpos.getZ()));
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
