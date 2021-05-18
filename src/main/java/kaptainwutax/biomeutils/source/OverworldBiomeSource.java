package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.land.*;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;
import kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer;
import kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer;
import kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer;
import kaptainwutax.biomeutils.layer.temperature.ClimateLayer;
import kaptainwutax.biomeutils.layer.water.*;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.mcutils.version.UnsupportedVersion;
import kaptainwutax.seedutils.rand.JRand;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public class OverworldBiomeSource extends LayeredBiomeSource<IntBiomeLayer> {
	public static final List<Biome> SPAWN_BIOMES = Arrays.asList(Biomes.FOREST, Biomes.PLAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.WOODED_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS);
	public final int biomeSize;
	public final int riverSize;
	public IntBiomeLayer base;
	public IntBiomeLayer ocean;
	public IntBiomeLayer noise;
	public IntBiomeLayer variants;
	public IntBiomeLayer biomes;
	public IntBiomeLayer river;
	public IntBiomeLayer full;
	public VoronoiLayer voronoi;
	public IntBiomeLayer debug;

	public OverworldBiomeSource(MCVersion version, long worldSeed) {
		this(version, worldSeed, 4, 4);
	}

	public OverworldBiomeSource(MCVersion version, long worldSeed, int biomeSize, int riverSize) {
		super(version, worldSeed);

		if (this.getVersion().isOlderThan(MCVersion.vb1_8_1)) {
			throw new UnsupportedVersion(this.getVersion(), "overworld biomes");
		}
		if (this.getVersion().isOlderThan(MCVersion.vb1_8_1)) {
			System.out.println("WARNING USING TEMPORARY BIOME STACK (NOT VERIFIED)");
		}

		this.biomeSize = biomeSize;
		this.riverSize = riverSize;
		this.build();
	}

	@Override
	public Dimension getDimension() {
		return Dimension.OVERWORLD;
	}

	protected void build() {
		BiFunction<Long, IntBiomeLayer, IntBiomeLayer> NORMAL_SCALE = (salt, parent) -> new ScaleLayer(this.getVersion(), this.getWorldSeed(), salt, ScaleLayer.Type.NORMAL, parent);

		// first legacy stack
		//4096
		this.layers.add(this.base = new ContinentLayer(this.getVersion(), this.getWorldSeed(), 1L));
		//2048
		this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2000L, ScaleLayer.Type.FUZZY, this.base));
		this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 1L, this.base));

		//1024
		this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2001L, ScaleLayer.Type.NORMAL, this.base));
		this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 2L, this.base));

		if (is1_7up.call()) {
			this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 50L, this.base));
			this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 70L, this.base));
			this.layers.add(this.base = new IslandLayer(this.getVersion(), this.getWorldSeed(), 2L, this.base));
		}
		if (is_1_0_up.call()){
			this.layers.add(this.base = new ClimateLayer.Cold(this.getVersion(), this.getWorldSeed(), 2L, this.base));
		}

		if (is1_7up.call()) {
			this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.base));
			this.layers.add(this.base = new ClimateLayer.Temperate(this.getVersion(), this.getWorldSeed(), 2L, this.base));
			this.layers.add(this.base = new ClimateLayer.Cool(this.getVersion(), this.getWorldSeed(), 2L, this.base));
			this.layers.add(this.base = new ClimateLayer.Special(this.getVersion(), this.getWorldSeed(), 3L, this.base));
		}

		//512
		this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2002L, ScaleLayer.Type.NORMAL, this.base));
		if (is1_6down.call()) {
			this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.base));
		}

		//256
		this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2003L, ScaleLayer.Type.NORMAL, this.base));
		this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), is_1_0_up.call()?4L:3L, this.base));

		if (is_1_0_up.call()){
			this.layers.add(this.base = new MushroomLayer(this.getVersion(), this.getWorldSeed(), 5L, this.base));
		}else{
			this.layers.add(this.base = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 2004L, ScaleLayer.Type.NORMAL, this.base));
			this.layers.add(this.base = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.base));
		}

		if (is1_7up.call()) {
			this.layers.add(this.base = new DeepOceanLayer(this.getVersion(), this.getWorldSeed(), 4L, this.base));
		}

		// new biomes chain
		this.layers.add(this.biomes = new BaseBiomesLayer(this.getVersion(), this.getWorldSeed(), 200L, this.base));

		if (is1_14up.call()) {
			this.layers.add(this.biomes = new BambooJungleLayer(this.getVersion(), this.getWorldSeed(), 1001L, this.biomes));
		}
		this.biomes = this.stack(1000L, NORMAL_SCALE, this.biomes, 2);
		if (is1_7up.call()) {
			this.layers.add(this.biomes = new EaseEdgeLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.biomes));
		}

		// noise generation for variant and river
		this.layers.add(this.noise = new NoiseLayer(this.getVersion(), this.getWorldSeed(), 100L, this.base));
		if (is1_12down.call()) {
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
		if (is1_1up.call()) {
			this.layers.add(this.variants = new HillsLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.biomes, this.noise));
		} else {
			this.variants = this.biomes;
		}
		if (is1_7up.call()) {
			this.layers.add(this.variants = new SunflowerPlainsLayer(this.getVersion(), this.getWorldSeed(), 1001L, this.variants));
		}

		// TODO add the temperature and rainfall layers here
		for (int i = 0; i < this.biomeSize; i++) {
			this.layers.add(this.variants = new ScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L + i, ScaleLayer.Type.NORMAL, this.variants));
			if (i == 0) {
				this.layers.add(this.variants = new LandLayer(this.getVersion(), this.getWorldSeed(), 3L, this.variants));
			}

			if ((is1_1up.call() && (i == 1 || (this.biomeSize == 1 && is1_8up.call())))
					|| (is1_0down.call() && i == 0 && is_1_0_up.call())) {
				// this will trigger for i==1 for 1.1+
				// or for biomesize=1 if 1.8+
				// or for i==0 for 1.0 only
				this.layers.add(this.variants = new EdgeBiomesLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));

			}
			if (i == 1 && is1_6down.call() && is1_1up.call()) {
				// 1.6.4- rivers (introduced in 1.1)
				this.layers.add(this.variants = new OldRiverInBiomes(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));
			}
		}
		this.layers.add(this.variants = new SmoothScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.variants));

		// river chain
		// basically for 1.13+ the stack 2 for the biomes and the river was the same but for 1.12 there was a difference
		// for the hills the noise was sampled with layer seed=0 but the river stack was normal
		// for 1.7 they don't have the notion of river size so biome size was used
		// for 1.6.4- we have a stack of 6 so here 2+4 that we offset by 2
		this.river = this.stack(
				is1_7up.call() ? 1000L : 1002L, NORMAL_SCALE,
				is1_12down.call() ? this.river : this.noise,
				is1_8up.call() ? riverSize : biomeSize
		);
		this.layers.add(this.river = new NoiseToRiverLayer(this.getVersion(), this.getWorldSeed(), 1L, this.river));
		this.layers.add(this.river = new SmoothScaleLayer(this.getVersion(), this.getWorldSeed(), 1000L, this.river));

		// mixing of the river with the hills and variants
		this.layers.add(this.full = new RiverLayer(this.getVersion(), this.getWorldSeed(), 100L, this.variants, this.river));

		if (is1_13up.call()) {
			// ocean chains
			this.layers.add(this.ocean = new OceanTemperatureLayer(this.getVersion(), this.getWorldSeed(), 2L));
			this.ocean = this.stack(2001L, NORMAL_SCALE, this.ocean, 6);
			// mixing of the two firsts stacks with the ocean chain
			this.layers.add(this.full = new OceanTemperatureLayer.Apply(this.getVersion(), this.getWorldSeed(), 100L, this.full, this.ocean));
		}

		this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full));
		this.layers.setScales();
	}

	public IntBiomeLayer stack(long salt, BiFunction<Long, IntBiomeLayer, IntBiomeLayer> layer, IntBiomeLayer parent, int count) {
		for (int i = 0; i < count; i++) {
			this.layers.add(parent = layer.apply(salt + i, parent));
		}

		return parent;
	}

	@Override
	public Biome getBiome(BPos bpos) {
		return Biomes.REGISTRY.get(this.voronoi.get(bpos.getX(), 0, bpos.getZ()));
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biomes.REGISTRY.get(this.voronoi.get(x, 0, z));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biomes.REGISTRY.get(this.full.get(x, 0, z));
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
		if (Biomes.PLAINS.equals(biome)) {
			return 1.0;
		} else if (Biomes.MOUNTAINS.equals(biome)) {
			return 0.8; // height dependent
		} else if (Biomes.FOREST.equals(biome)) {
			return 1.0;
		} else if (Biomes.TAIGA.equals(biome)) {
			return 1.0;
		} else if (Biomes.SWAMP.equals(biome)) {
			return 0.6; // height dependent
		} else if (Biomes.RIVER.equals(biome)) {
			return 0.2;
		} else if (Biomes.BEACH.equals(biome)) {
			return 0.1;
		} else if (Biomes.WOODED_HILLS.equals(biome)) {
			return 1.0;
		} else if (Biomes.TAIGA_HILLS.equals(biome)) {
			return 1.0;
		} else if (Biomes.MOUNTAIN_EDGE.equals(biome)) {
			return 1.0; // height dependent
		} else if (Biomes.JUNGLE.equals(biome)) {
			return 1.0;
		} else if (Biomes.JUNGLE_HILLS.equals(biome)) {
			return 1.0;
		} else if (Biomes.JUNGLE_EDGE.equals(biome)) {
			return 1.0;
		} else if (Biomes.BIRCH_FOREST.equals(biome)) {
			return 1.0;
		} else if (Biomes.BIRCH_FOREST_HILLS.equals(biome)) {
			return 1.0;
		} else if (Biomes.DARK_FOREST.equals(biome)) {
			return 0.9;
		} else if (Biomes.SNOWY_TAIGA.equals(biome)) {
			return 0.1; // below trees
		} else if (Biomes.SNOWY_TAIGA_HILLS.equals(biome)) {
			return 0.1; // below trees
		} else if (Biomes.GIANT_TREE_TAIGA.equals(biome)) {
			return 0.6;
		} else if (Biomes.GIANT_TREE_TAIGA_HILLS.equals(biome)) {
			return 0.6;
		} else if (Biomes.MODIFIED_GRAVELLY_MOUNTAINS.equals(biome)) {
			return 0.2; // height dependent
		} else if (Biomes.SAVANNA.equals(biome)) {
			return 1.0;
		} else if (Biomes.SAVANNA_PLATEAU.equals(biome)) {
			return 1.0;
		} else if (Biomes.BADLANDS.equals(biome)) {
			return 0.1; // height dependent
		} else if (Biomes.BADLANDS_PLATEAU.equals(biome)) {
			return 0.1; // height dependent
			// NOTE: in rare circumstances you can get also get grass islands that are
			// completely ocean variants...
		}
		return 0;
	}

	public boolean isValidPos(int x, int z, boolean trueSpawn) {
		// TODO tricky part, check biomes valid + gen terain == GRASS

		// void check not usable
		// for now lets just do the proba tables then we can move to full terrain for true spawn (see terrainUtils)
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
