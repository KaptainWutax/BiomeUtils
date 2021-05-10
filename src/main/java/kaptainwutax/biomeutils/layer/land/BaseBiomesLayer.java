package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.ComputeLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class BaseBiomesLayer extends ComputeLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
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

	@Override
	public int compute(int value, int x, int z) {
		this.setSeed(x, z);
		int specialBits = (value >> 8) & 15; //the nextInt(15) + 1 in ClimateLayer.Special
		value &= ~0xF00; //removes the 4 special bits and keeps everything else

		if (is1_6down.call()) {
			// adapting the code below is too hard for this one
			return this.sampleOld(value);
		}

		if (Biome.isOcean(value) || value == Biomes.MUSHROOM_FIELDS.getId()) return value;

		if (value == Biomes.PLAINS.getId()) {
			if (specialBits > 0) {
				return this.nextInt(3) == 0 ? Biomes.BADLANDS_PLATEAU.getId() : Biomes.WOODED_BADLANDS_PLATEAU.getId();
			}

			return DRY_BIOMES[this.nextInt(DRY_BIOMES.length)].getId();
		} else if (value == Biomes.DESERT.getId()) {
			if (specialBits > 0) {
				return Biomes.JUNGLE.getId();
			}

			return TEMPERATE_BIOMES[this.nextInt(TEMPERATE_BIOMES.length)].getId();
		} else if (value == Biomes.MOUNTAINS.getId()) {
			if (specialBits > 0) {
				return Biomes.GIANT_TREE_TAIGA.getId();
			}

			return COOL_BIOMES[this.nextInt(COOL_BIOMES.length)].getId();
		} else if (value == Biomes.FOREST.getId()) {
			return SNOWY_BIOMES[this.nextInt(SNOWY_BIOMES.length)].getId();
		}

		return Biomes.MUSHROOM_FIELDS.getId();
	}

	private int sampleOld(int center) {
		if (Biome.isShallowOcean(center, this)) {
			return Biomes.OCEAN.getId();
		}
		// this will not be hit in b1.8.1-
		if (center == Biomes.MUSHROOM_FIELDS.getId()) {
			return Biomes.MUSHROOM_FIELDS.getId();
		}
		Biome[] biomeList = isDefault1_1 ? OLD_BIOMES_DEFAULT_1_1 : OLD_BIOMES;
		Biome oldBiome = biomeList[this.nextInt(biomeList.length)];
		if (is_beta_1_8_1down.call()){
			return oldBiome.getId();
		}
		if (center == Biomes.PLAINS.getId()) {
			return oldBiome.getId();
		}
		if (is1_2down.call()) {
			return Biomes.SNOWY_TUNDRA.getId();
		}
		if (oldBiome == Biomes.TAIGA) {
			return Biomes.TAIGA.getId();
		} else {
			return Biomes.SNOWY_TUNDRA.getId();
		}
	}

}
