package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class HillsLayer extends IntBiomeLayer {

	public HillsLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		IntBiomeLayer biomesLayer = this.getParent(0, IntBiomeLayer.class); // biomes
		IntBiomeLayer noiseLayer = this.getParent(1, IntBiomeLayer.class); // noise (river)
		int i = biomesLayer.get(x, y, z);
		boolean toHills = this.nextInt(3) == 0;

		int k = -1;
		Biome biome3;

		if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_7_2)) {
			int j = noiseLayer.get(x, y, z);
			k = (j - 2) % 29;
			if(!Biome.isShallowOcean(i, this.getVersion()) && j >= 2 && k == 1) {
				Biome biome = Biomes.REGISTRY.get(i);

				if(biome == null || !biome.hasParent()) {
					biome3 = biome == null ? null : biome.getChild();
					return biome3 == null ? i : biome3.getId();
				}
			}
			toHills |= (k == 0);
		}


		if(toHills) {
			int l = i;
			if(i == Biomes.DESERT.getId()) {
				l = Biomes.DESERT_HILLS.getId();
			} else if(i == Biomes.FOREST.getId()) {
				l = Biomes.WOODED_HILLS.getId();
			} else if(i == Biomes.BIRCH_FOREST.getId()) {
				l = Biomes.BIRCH_FOREST_HILLS.getId();
			} else if(i == Biomes.DARK_FOREST.getId()) {
				l = Biomes.PLAINS.getId();
			} else if(i == Biomes.TAIGA.getId()) {
				l = Biomes.TAIGA_HILLS.getId();
			} else if(i == Biomes.GIANT_TREE_TAIGA.getId()) {
				l = Biomes.GIANT_TREE_TAIGA_HILLS.getId();
			} else if(i == Biomes.SNOWY_TAIGA.getId()) {
				l = Biomes.SNOWY_TAIGA_HILLS.getId();
			} else if(i == Biomes.PLAINS.getId()) {
				boolean forestHills = false;

				if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_7_2)) {
					forestHills = this.nextInt(3) == 0;
				}

				l = forestHills ? Biomes.WOODED_HILLS.getId() : Biomes.FOREST.getId();
			} else if(i == Biomes.SNOWY_TUNDRA.getId()) {
				l = Biomes.SNOWY_MOUNTAINS.getId();
			} else if(i == Biomes.JUNGLE.getId()) {
				l = Biomes.JUNGLE_HILLS.getId();
			} else if(i == Biomes.BAMBOO_JUNGLE.getId()) {
				l = Biomes.BAMBOO_JUNGLE_HILLS.getId();
			} else if(i == Biomes.OCEAN.getId()) {
				l = this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_4) ? Biomes.OCEAN.getId() : Biomes.DEEP_OCEAN.getId();
			} else if(i == Biomes.LUKEWARM_OCEAN.getId()) {
				l = Biomes.DEEP_LUKEWARM_OCEAN.getId();
			} else if(i == Biomes.COLD_OCEAN.getId()) {
				l = Biomes.DEEP_COLD_OCEAN.getId();
			} else if(i == Biomes.FROZEN_OCEAN.getId()) {
				l = Biomes.DEEP_FROZEN_OCEAN.getId();
			} else if(i == Biomes.MOUNTAINS.getId()) {
				l = this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_4) ? Biomes.MOUNTAINS.getId() : Biomes.WOODED_MOUNTAINS.getId();
			} else if(i == Biomes.SAVANNA.getId()) {
				l = Biomes.SAVANNA_PLATEAU.getId();
			} else if(Biome.areSimilar(i, Biomes.WOODED_BADLANDS_PLATEAU, this.getVersion())) {
				l = Biomes.BADLANDS.getId();
			}
			// in 1.12 this check is only for DEEP_OCEAN but since the other can't spawn, its ok
			else if((i == Biomes.DEEP_OCEAN.getId() || i == Biomes.DEEP_LUKEWARM_OCEAN.getId()
				|| i == Biomes.DEEP_COLD_OCEAN.getId() || i == Biomes.DEEP_FROZEN_OCEAN.getId())
				&& this.nextInt(3) == 0) {
				l = this.nextInt(2) == 0 ? Biomes.PLAINS.getId() : Biomes.FOREST.getId();
			}

			if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_7_2)) {
				if(k == 0 && l != i) {
					biome3 = Biomes.REGISTRY.get(l).getChild();
					l = biome3 == null ? i : biome3.getId();
				}
			}

			if(l != i) {
				int m = 0;
				Biome b = Biomes.REGISTRY.get(i);
				if(Biome.areSimilar(biomesLayer.get(x, y, z - 1), b, this.getVersion()))m++;
				if(Biome.areSimilar(biomesLayer.get(x + 1, y, z), b, this.getVersion()))m++;
				if(Biome.areSimilar(biomesLayer.get(x - 1, y, z), b, this.getVersion()))m++;
				if(Biome.areSimilar(biomesLayer.get(x, y, z + 1), b, this.getVersion()))m++;
				if(this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_4)) {
					if(m == 4) return l;
				} else if(m >= 3) return l;
			}
		}

		return i;
	}

}
