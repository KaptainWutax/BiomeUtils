package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class BaseBiomesLayer extends BiomeLayer {

	public static final Biome[] DRY_BIOMES = new Biome[] {Biome.DESERT, Biome.DESERT, Biome.DESERT,
			Biome.SAVANNA, Biome.SAVANNA, Biome.PLAINS};
	public static final Biome[] TEMPERATE_BIOMES = new Biome[] {Biome.FOREST, Biome.DARK_FOREST, 
			Biome.MOUNTAINS, Biome.PLAINS, Biome.BIRCH_FOREST, Biome.SWAMP};
	public static final Biome[] COOL_BIOMES = new Biome[] {Biome.FOREST, Biome.MOUNTAINS, 
			Biome.TAIGA, Biome.PLAINS};
	public static final Biome[] SNOWY_BIOMES = new Biome[] {Biome.SNOWY_TUNDRA, Biome.SNOWY_TUNDRA, 
			Biome.SNOWY_TUNDRA, Biome.SNOWY_TAIGA};
	
	public BaseBiomesLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public BaseBiomesLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);
		int value = this.parent.sample(x, z);
		int i = (value & 3840) >> 8;
		value &= -3841;

		if(!BiomeSource.isOcean(value) && value != Biome.MUSHROOM_FIELDS.getId()) {
			switch(value) {
				case 1:
					if(i > 0) {
						return this.nextInt(3) == 0 ? Biome.BADLANDS_PLATEAU.getId() : Biome.WOODED_BADLANDS_PLATEAU.getId();
					}

					return DRY_BIOMES[this.nextInt(DRY_BIOMES.length)].getId();
				case 2:
					if(i > 0) {
						return Biome.JUNGLE.getId();
					}

					return TEMPERATE_BIOMES[this.nextInt(TEMPERATE_BIOMES.length)].getId();
				case 3:
					if(i > 0) {
						return Biome.GIANT_TREE_TAIGA.getId();
					}

					return COOL_BIOMES[this.nextInt(COOL_BIOMES.length)].getId();
				case 4:
					return SNOWY_BIOMES[this.nextInt(SNOWY_BIOMES.length)].getId();
				default:
					return Biome.MUSHROOM_FIELDS.getId();
			}
		}

		return value;
	}

}
