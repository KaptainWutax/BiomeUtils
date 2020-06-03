package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.land.*;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;
import kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer;
import kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer;
import kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer;
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
    public BiomeLayer hills;
    public BiomeLayer full;
    public VoronoiLayer voronoi;

    public final int biomeSize;
    public final int riverSize;

    public OverworldBiomeSource(MCVersion version, long worldSeed) {
        this(version, worldSeed, 4, 4);

        if (this.getVersion().isOlderThan(MCVersion.v1_14)) {
            throw new UnsupportedVersion(this.getVersion(), "overworld biomes");
        }
    }

    public OverworldBiomeSource(MCVersion version, long worldSeed, int biomeSize, int riverSize) {
        super(version, worldSeed);
        this.biomeSize = biomeSize;
        this.riverSize = riverSize;
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return Biome.REGISTRY.get(this.voronoi.get(x, z));
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return Biome.REGISTRY.get(this.full.get(x, z));
    }

    public OverworldBiomeSource build() {
        BiFunction<Long, BiomeLayer, BiomeLayer> NORMAL_SCALE = (s, p) -> new ScaleLayer(this.getWorldSeed(), s, ScaleLayer.Type.NORMAL, p);

        this.base = new ContinentLayer(this.getWorldSeed(), 1L);
        this.base = new ScaleLayer(this.getWorldSeed(), 2000L, ScaleLayer.Type.FUZZY, this.base);
        this.base = new LandLayer(this.getWorldSeed(), 1L, this.base);
        this.base = new ScaleLayer(this.getWorldSeed(), 2001L, ScaleLayer.Type.NORMAL, this.base);
        this.base = new LandLayer(this.getWorldSeed(), 2L, this.base);
        this.base = new LandLayer(this.getWorldSeed(), 50L, this.base);
        this.base = new LandLayer(this.getWorldSeed(), 70L, this.base);
        this.base = new IslandLayer(this.getWorldSeed(), 2L, this.base);
        this.base = new ClimateLayer.Cold(this.getWorldSeed(), 2L, this.base);
        this.base = new LandLayer(this.getWorldSeed(), 3L, this.base);
        this.base = new ClimateLayer.Temperate(this.getWorldSeed(), 2L, this.base);
        this.base = new ClimateLayer.Cool(this.getWorldSeed(), 2L, this.base);
        this.base = new ClimateLayer.Special(this.getWorldSeed(), 3L, this.base);
        this.base = new ScaleLayer(this.getWorldSeed(), 2002L, ScaleLayer.Type.NORMAL, this.base);
        this.base = new ScaleLayer(this.getWorldSeed(), 2003L, ScaleLayer.Type.NORMAL, this.base);
        this.base = new LandLayer(this.getWorldSeed(), 4L, this.base);
        this.base = new MushroomLayer(this.getWorldSeed(), 5L, this.base);
        this.base = new DeepOceanLayer(this.getWorldSeed(), 4L, this.base);
        this.base = stack(1000L, NORMAL_SCALE, this.base, 0);

        this.noise = stack(1000L, NORMAL_SCALE, this.base, 0);
        this.noise = new NoiseLayer(this.getWorldSeed(), 100L, this.base);

        this.full = new BaseBiomesLayer(this.getWorldSeed(), 200L, this.base);
        this.full = new BambooJungleLayer(this.getWorldSeed(), 1001L, this.full);
        this.full = stack(1000L, NORMAL_SCALE, this.full, 2);
        this.full = new EaseEdgeLayer(this.getWorldSeed(), 1000L, this.full);

        this.hills = stack(1000L, NORMAL_SCALE, this.noise, 2);
        this.full = new HillsLayer(this.getWorldSeed(), 1000L, this.full, this.hills);

        this.noise = stack(1000L, NORMAL_SCALE, this.noise, 2);
        this.noise = stack(1000L, NORMAL_SCALE, this.noise, this.riverSize);
        this.noise = new NoiseToRiverLayer(this.getWorldSeed(), 1L, this.noise);
        this.noise = new SmoothScaleLayer(this.getWorldSeed(), 1000L, this.noise);

        this.full = new SunflowerPlainsLayer(this.getWorldSeed(), 1001L, this.full);

        for (int i = 0; i < this.biomeSize; i++) {
            this.full = new ScaleLayer(this.getWorldSeed(), 1000L + i, ScaleLayer.Type.NORMAL, this.full);
            if (i == 0) this.full = new LandLayer(this.getWorldSeed(), 3L, this.full);

            if (i == 1 || this.biomeSize == 1) {
                this.full = new EdgeBiomesLayer(this.getWorldSeed(), 1000L, this.full);
            }
        }

        this.full = new SmoothScaleLayer(this.getWorldSeed(), 1000L, this.full);
        this.full = new RiverLayer(this.getWorldSeed(), 100L, this.full, this.noise);

        this.ocean = new OceanTemperatureLayer(this.getWorldSeed(), 2L);
        this.ocean = stack(2001L, NORMAL_SCALE, this.ocean, 6);

        this.full = new OceanTemperatureLayer.Apply(this.getWorldSeed(), 100L, this.full, this.ocean);
        this.voronoi = new VoronoiLayer(this.getWorldSeed(), !this.getVersion().isOlderThan(MCVersion.v1_15), this.full);
        return this;
    }

    public static BiomeLayer stack(long salt, BiFunction<Long, BiomeLayer, BiomeLayer> layer, BiomeLayer parent, int count) {
        for (int i = 0; i < count; i++) {
            parent = layer.apply(salt + i, parent);
        }

        return parent;
    }

}
