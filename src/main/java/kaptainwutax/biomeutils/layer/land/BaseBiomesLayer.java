package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class BaseBiomesLayer extends IntBiomeLayer {
	public boolean useDefault1_1 = false;

	public static final Biome[] DRY_BIOMES = new Biome[] {
		Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.SAVANNA, Biomes.SAVANNA, Biomes.PLAINS
	};

	public static final Biome[] TEMPERATE_BIOMES = new Biome[] {
		Biomes.FOREST, Biomes.DARK_FOREST, Biomes.MOUNTAINS, Biomes.PLAINS, Biomes.BIRCH_FOREST, Biomes.SWAMP
	};

	public static final Biome[] COOL_BIOMES = new Biome[] {
		Biomes.FOREST, Biomes.MOUNTAINS, Biomes.TAIGA, Biomes.PLAINS
	};

	public static final Biome[] SNOWY_BIOMES = new Biome[] {
		Biomes.SNOWY_TUNDRA, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_TAIGA
	};

	public static final Biome[] OLD_BIOMES = new Biome[] {
		Biomes.DESERT, Biomes.FOREST, Biomes.MOUNTAINS, Biomes.SWAMP, Biomes.PLAINS, Biomes.TAIGA, Biomes.JUNGLE
	};

	public static final Biome[] OLD_BIOMES_DEFAULT_1_1 = new Biome[] {
		Biomes.DESERT, Biomes.FOREST, Biomes.MOUNTAINS, Biomes.SWAMP, Biomes.PLAINS, Biomes.TAIGA
	};

	public BaseBiomesLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	public BaseBiomesLayer setDefault1_1(boolean useDefault1_1) {
		this.useDefault1_1 = useDefault1_1;
		return this;
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int center = this.getParent(IntBiomeLayer.class).get(x, y, z);
		int specialBits = (center >> 8) & 15; //the nextInt(15) + 1 in ClimateLayer.Special
		center &= ~0xF00; //removes the 4 special bits and keeps everything else

		if(this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_4)) {
			// adapting the code below is too hard for this one
			return this.sampleOld(center);
		}

		if(Biome.isOcean(center) || center == Biomes.MUSHROOM_FIELDS.getId()) {
			return center;
		}

		if(center == Biomes.PLAINS.getId()) {
			if(specialBits > 0) {
				return this.nextInt(3) == 0 ? Biomes.BADLANDS_PLATEAU.getId() : Biomes.WOODED_BADLANDS_PLATEAU.getId();
			}
			if(this.useDefault1_1) {
				return OLD_BIOMES_DEFAULT_1_1[this.nextInt(OLD_BIOMES_DEFAULT_1_1.length)].getId();
			}
			return DRY_BIOMES[this.nextInt(DRY_BIOMES.length)].getId();
		} else if(center == Biomes.DESERT.getId()) {
			if(specialBits > 0) {
				return Biomes.JUNGLE.getId();
			}

			return TEMPERATE_BIOMES[this.nextInt(TEMPERATE_BIOMES.length)].getId();
		} else if(center == Biomes.MOUNTAINS.getId()) {
			if(specialBits > 0) {
				return Biomes.GIANT_TREE_TAIGA.getId();
			}

			return COOL_BIOMES[this.nextInt(COOL_BIOMES.length)].getId();
		} else if(center == Biomes.FOREST.getId()) {
			return SNOWY_BIOMES[this.nextInt(SNOWY_BIOMES.length)].getId();
		}

		return Biomes.MUSHROOM_FIELDS.getId();
	}

	private int sampleOld(int center) {
		if(Biome.isShallowOcean(center, this.getVersion())) {
			return Biomes.OCEAN.getId();
		}

		// this will not be hit in b1.8.1-
		if(center == Biomes.MUSHROOM_FIELDS.getId()) {
			return Biomes.MUSHROOM_FIELDS.getId();
		}

		Biome[] biomeList = this.getVersion().isOlderOrEqualTo(MCVersion.v1_1) ? OLD_BIOMES_DEFAULT_1_1 : OLD_BIOMES;
		Biome oldBiome = biomeList[this.nextInt(biomeList.length)];
		if(this.getVersion().isOlderOrEqualTo(MCVersion.vb1_8_1)) {
			return oldBiome.getId();
		}
		if(center == Biomes.PLAINS.getId()) {
			return oldBiome.getId();
		}
		if(this.getVersion().isOlderOrEqualTo(MCVersion.v1_2_5)) {
			return Biomes.SNOWY_TUNDRA.getId();
		}
		if(oldBiome == Biomes.TAIGA) {
			return Biomes.TAIGA.getId();
		} else {
			return Biomes.SNOWY_TUNDRA.getId();
		}
	}

}
