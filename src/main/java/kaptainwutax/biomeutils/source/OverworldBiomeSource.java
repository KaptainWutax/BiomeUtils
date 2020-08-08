package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.land.*;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;
import kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer;
import kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer;
import kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer;
import kaptainwutax.biomeutils.layer.temperature.ClimateLayer;
import kaptainwutax.biomeutils.layer.water.DeepOceanLayer;
import kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer;
import kaptainwutax.biomeutils.layer.water.OceanTemperatureLayer;
import kaptainwutax.biomeutils.layer.water.RiverLayer;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.UnsupportedVersion;

import java.util.function.BiFunction;

public class OverworldBiomeSource extends BiomeSource {

    public BiomeLayer base;
    public BiomeLayer ocean;
    public BiomeLayer noise;
    public BiomeLayer variants;
    public BiomeLayer biomes;
    public BiomeLayer river;
    public BiomeLayer full;
    public VoronoiLayer voronoi;

    public final int biomeSize;
    public final int riverSize;

    protected final LayerStack<BiomeLayer> layers = new LayerStack<>();

    public OverworldBiomeSource(MCVersion version, long worldSeed) {
        this(version, worldSeed, 4, 4);
    }

    public OverworldBiomeSource(MCVersion version, long worldSeed, int biomeSize, int riverSize) {
        super(version, worldSeed);

        if (this.getVersion().isOlderThan(MCVersion.v1_13)) {
            throw new UnsupportedVersion(this.getVersion(), "overworld biomes");
        }

        this.biomeSize = biomeSize;
        this.riverSize = riverSize;
        this.build();
    }

    @Override
    public LayerStack<BiomeLayer> getLayers() {
        return this.layers;
    }

    protected void build() {
        BiFunction<Long, BiomeLayer, BiomeLayer> NORMAL_SCALE = (s, p) -> new ScaleLayer(this.getVersion(), this.getWorldSeed(), s, ScaleLayer.Type.NORMAL, p);

        // first legacy stack
        //4096
        this.layers.add(this.base = new ContinentLayer(this.getVersion(), this.getWorldSeed(), 1L));
        //2048
        this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2000L, ScaleLayer.Type.FUZZY, this.base));
        this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 1L, this.base));
        //1024
        this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2001L, ScaleLayer.Type.NORMAL, this.base));
        this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 2L, this.base));
        this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 50L, this.base));
        this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 70L, this.base));
        this.layers.add(this.base = new IslandLayer(this.getVersion(), this.getWorldSeed(), 2L, this.base));
        this.layers.add(this.base = new ClimateLayer.Cold(this.getVersion(), this.getWorldSeed(), 2L, this.base));
        this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.base));
        this.layers.add(this.base = new ClimateLayer.Temperate(this.getVersion(), this.getWorldSeed(), 2L, this.base));
        this.layers.add(this.base = new ClimateLayer.Cool(this.getVersion(), this.getWorldSeed(), 2L, this.base));
        this.layers.add(this.base = new ClimateLayer.Special(this.getVersion(), this.getWorldSeed(), 3L, this.base));
        //512
        this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2002L, ScaleLayer.Type.NORMAL, this.base));
        //256
        this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2003L, ScaleLayer.Type.NORMAL, this.base));
        this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 4L, this.base));
        this.layers.add(this.base = new MushroomLayer(this.getVersion(), this.getWorldSeed(), 5L, this.base));
        this.layers.add(this.base = new DeepOceanLayer(this.getVersion(), this.getWorldSeed(), 4L, this.base));

        // new biomes chain
        this.layers.add(this.biomes = new BaseBiomesLayer(this.getVersion(), this.getWorldSeed(), 200L, this.base));

        if (!this.getVersion().isOlderThan(MCVersion.v1_14)) {
            this.layers.add(this.biomes = new BambooJungleLayer(this.getVersion(), this.getWorldSeed(), 1001L, this.biomes));
        }

        this.biomes = this.stack(1000L, NORMAL_SCALE, this.biomes, 2);
        this.layers.add(this.biomes = new EaseEdgeLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.biomes));

        // noise generation for variant and river
        this.layers.add(this.noise = new NoiseLayer(this.getVersion(), this.getWorldSeed(), 100L, this.base));
        this.noise = this.stack(1000L, NORMAL_SCALE, this.noise, 2);

        // hills and variants chain
        this.layers.add(this.variants = new HillsLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.biomes, this.noise));
        this.layers.add(this.variants = new SunflowerPlainsLayer(this.getVersion(), this.getWorldSeed(), 1001L, this.variants));

        for(int i = 0; i < this.biomeSize; i++) {
            this.layers.add(this.variants = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L + i, ScaleLayer.Type.NORMAL, this.variants));

            if(i == 0) {
                this.layers.add(this.variants = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.variants));
            }

            if(i == 1 || this.biomeSize == 1) {
                this.layers.add(this.variants = new EdgeBiomesLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));
            }
        }

        this.layers.add(this.variants = new SmoothScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));

        // river chain
        this.river = this.stack(1000L, NORMAL_SCALE, this.noise, 4);
        this.layers.add(this.river = new NoiseToRiverLayer(this.getVersion(), this.getWorldSeed(), 1L, this.river));
        this.layers.add(this.river = new SmoothScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.river));

        // mixing of the river with the hills and variants
        this.layers.add(this.full = new RiverLayer(this.getVersion(), this.getWorldSeed(), 100L, this.variants, this.river));

        // ocean chains
        this.layers.add(this.ocean = new OceanTemperatureLayer(this.getVersion(), this.getWorldSeed(), 2L));
        this.ocean = this.stack(2001L, NORMAL_SCALE, this.ocean, 6);

        // mixing of the two firsts stacks with the ocean chain
        this.layers.add(this.full = new OceanTemperatureLayer.Apply(this.getVersion(), this.getWorldSeed(), 100L, this.full, this.ocean));
        this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full));

        this.layers.setScales();
    }

    public BiomeLayer stack(long salt, BiFunction<Long, BiomeLayer, BiomeLayer> layer, BiomeLayer parent, int count) {
        for (int i = 0; i < count; i++) {
            this.layers.add(parent = layer.apply(salt + i, parent));
        }

        return parent;
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return Biome.REGISTRY.get(this.voronoi.get(x, 0, z));
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return Biome.REGISTRY.get(this.full.get(x, 0, z));
    }

}
