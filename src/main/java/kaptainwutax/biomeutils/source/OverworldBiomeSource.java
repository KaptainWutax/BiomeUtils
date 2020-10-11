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
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.BPos;
import kaptainwutax.seedutils.util.UnsupportedVersion;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public class OverworldBiomeSource extends BiomeSource {

    public static final List<Biome> SPAWN_BIOMES = Arrays.asList(Biome.FOREST, Biome.PLAINS, Biome.TAIGA,
            Biome.TAIGA_HILLS, Biome.WOODED_HILLS, Biome.JUNGLE, Biome.JUNGLE_HILLS);

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

        if (this.getVersion().isOlderThan(MCVersion.v1_8)) {
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

    @Override
    public Dimension getDimension() {
        return Dimension.OVERWORLD;
    }

    protected void build() {
        BiFunction<Long, BiomeLayer, BiomeLayer> NORMAL_SCALE = (salt, parent) -> new ScaleLayer(this.getVersion(), this.getWorldSeed(), salt, ScaleLayer.Type.NORMAL, parent);

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

        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_14)) {
            this.layers.add(this.biomes = new BambooJungleLayer(this.getVersion(), this.getWorldSeed(), 1001L, this.biomes));
        }

        this.biomes = this.stack(1000L, NORMAL_SCALE, this.biomes, 2);
        this.layers.add(this.biomes = new EaseEdgeLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.biomes));

        // noise generation for variant and river
        this.layers.add(this.noise = new NoiseLayer(this.getVersion(), this.getWorldSeed(), 100L, this.base));


        if (this.getVersion().isOlderThan(MCVersion.v1_13)) {
            // this line needs an explanation : basically back when the stack was recursively initialized, only one parent was
            // initialized with its world seed but the hills layer had 2 parents so the noise was never initialized recursively,
            // we simulate this stuff here by scaling with world seed equals to 0
            // the river branch still use the full scaling with its normal layer seed
            this.river = this.stack(1000L, NORMAL_SCALE, this.noise, 2);
            // Passing 0 for worldSeed and salt leads to the layerSeed being equal to 0.
            this.layers.add(this.noise = new ScaleLayer(this.getVersion(), 0L, 0L, ScaleLayer.Type.NORMAL, this.noise));
            this.layers.add(this.noise = new ScaleLayer(this.getVersion(), 0L, 0L, ScaleLayer.Type.NORMAL, this.noise));
        } else {
            this.layers.add(this.noise = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, ScaleLayer.Type.NORMAL, this.noise));
            this.layers.add(this.noise = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 1001L, ScaleLayer.Type.NORMAL, this.noise));
        }

        // hills and variants chain
        this.layers.add(this.variants = new HillsLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.biomes, this.noise));
        this.layers.add(this.variants = new SunflowerPlainsLayer(this.getVersion(), this.getWorldSeed(), 1001L, this.variants));

        for (int i = 0; i < this.biomeSize; i++) {
            this.layers.add(this.variants = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L + i, ScaleLayer.Type.NORMAL, this.variants));

            if (i == 0) {
                this.layers.add(this.variants = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.variants));
            }

            if (i == 1 || this.biomeSize == 1) {
                this.layers.add(this.variants = new EdgeBiomesLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));
            }
        }

        this.layers.add(this.variants = new SmoothScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));

        // river chain
        // basically for 1.13+ the stack 2 for the biomes and the river was the same but for 1.12 there was a difference
        // for the hills the noise was sampled with layer seed=0 but the river stack was normal
        this.river = this.stack(1000L, NORMAL_SCALE, this.getVersion().isOlderThan(MCVersion.v1_13) ? this.river : this.noise, 4);
        this.layers.add(this.river = new NoiseToRiverLayer(this.getVersion(), this.getWorldSeed(), 1L, this.river));
        this.layers.add(this.river = new SmoothScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.river));

        // mixing of the river with the hills and variants
        this.layers.add(this.full = new RiverLayer(this.getVersion(), this.getWorldSeed(), 100L, this.variants, this.river));

        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_13)) {
            // ocean chains
            this.layers.add(this.ocean = new OceanTemperatureLayer(this.getVersion(), this.getWorldSeed(), 2L));
            this.ocean = this.stack(2001L, NORMAL_SCALE, this.ocean, 6);
            // mixing of the two firsts stacks with the ocean chain
            this.layers.add(this.full = new OceanTemperatureLayer.Apply(this.getVersion(), this.getWorldSeed(), 100L, this.full, this.ocean));
        }

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

    public BPos getSpawnPoint(Collection<Biome> spawnBiomes) {
        if (this.getVersion().isOlderThan(MCVersion.v1_13)) {
            return getSpawnPoint12(spawnBiomes, false);
        }
        JRand rand = new JRand(this.getWorldSeed());
        BPos spawnPos = this.locateBiome(0, 0, 0, 256, spawnBiomes, rand);
        return spawnPos == null ? new BPos(8, 0, 8) : spawnPos.add(8, 0, 8);
    }

    public double getGrassStats(Biome biome) {
        if (Biome.PLAINS.equals(biome)) {
            return 1.0;
        } else if (Biome.MOUNTAINS.equals(biome)) {
            return 0.8; // height dependent
        } else if (Biome.FOREST.equals(biome)) {
            return 1.0;
        } else if (Biome.TAIGA.equals(biome)) {
            return 1.0;
        } else if (Biome.SWAMP.equals(biome)) {
            return 0.6; // height dependent
        } else if (Biome.RIVER.equals(biome)) {
            return 0.2;
        } else if (Biome.BEACH.equals(biome)) {
            return 0.1;
        } else if (Biome.WOODED_HILLS.equals(biome)) {
            return 1.0;
        } else if (Biome.TAIGA_HILLS.equals(biome)) {
            return 1.0;
        } else if (Biome.MOUNTAIN_EDGE.equals(biome)) {
            return 1.0; // height dependent
        } else if (Biome.JUNGLE.equals(biome)) {
            return 1.0;
        } else if (Biome.JUNGLE_HILLS.equals(biome)) {
            return 1.0;
        } else if (Biome.JUNGLE_EDGE.equals(biome)) {
            return 1.0;
        } else if (Biome.BIRCH_FOREST.equals(biome)) {
            return 1.0;
        } else if (Biome.BIRCH_FOREST_HILLS.equals(biome)) {
            return 1.0;
        } else if (Biome.DARK_FOREST.equals(biome)) {
            return 0.9;
        } else if (Biome.SNOWY_TAIGA.equals(biome)) {
            return 0.1; // below trees
        } else if (Biome.SNOWY_TAIGA_HILLS.equals(biome)) {
            return 0.1; // below trees
        } else if (Biome.GIANT_TREE_TAIGA.equals(biome)) {
            return 0.6;
        } else if (Biome.GIANT_TREE_TAIGA_HILLS.equals(biome)) {
            return 0.6;
        } else if (Biome.MODIFIED_GRAVELLY_MOUNTAINS.equals(biome)) {
            return 0.2; // height dependent
        } else if (Biome.SAVANNA.equals(biome)) {
            return 1.0;
        } else if (Biome.SAVANNA_PLATEAU.equals(biome)) {
            return 1.0;
        } else if (Biome.BADLANDS.equals(biome)) {
            return 0.1; // height dependent
        } else if (Biome.BADLANDS_PLATEAU.equals(biome)) {
            return 0.1; // height dependent
            // NOTE: in rare circumstances you can get also get grass islands that are
            // completely ocean variants...
        }
        return 0;
    }

    public boolean isValidPos(int x, int z, boolean trueSpawn) {
        // TODO tricky part, check biomes valid + gen terain == GRASS

        // void check not usable
        // for now lets just do the proba tables then we can move to full terrain for true spawn
        if (!trueSpawn) {
            return getGrassStats(this.getBiome(x, 0, z)) >= 0.5;
        } else {
            throw new UnsupportedVersion(getVersion(), "The true spawn is not yet implemented");
        }
    }

    public BPos getSpawnPoint12(Collection<Biome> spawnBiomes, boolean trueSpawn) {
        JRand rand = new JRand(this.getWorldSeed());
        BPos spawnPos = this.locateBiome12(0, 0, 256, spawnBiomes, rand);
        int x = 8;
        int z = 8;
        if (spawnPos != null) {
            x = spawnPos.getX();
            z = spawnPos.getZ();
        }
        int counter = 0;
        // wiggle
        while (!isValidPos(x, z, trueSpawn)) {
            x += rand.nextInt(64) - rand.nextInt(64);
            z += rand.nextInt(64) - rand.nextInt(64);
            ++counter;
            if (counter == 1000) {
                break;
            }
        }
        return new BPos(x, 64, z);
    }

    public BPos getSpawnPoint() {
        return this.getSpawnPoint(SPAWN_BIOMES);
    }

}
