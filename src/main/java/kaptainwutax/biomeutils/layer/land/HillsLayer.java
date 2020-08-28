package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class HillsLayer extends BiomeLayer {

	public HillsLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int i = this.getParent(0).get(x, y, z); // biomes
		int j = this.getParent(1).get(x, y, z); // noise (river)

		int k = (j - 2) % 29;
		Biome biome3;

		if(!Biome.isShallowOcean(i) && j >= 2 && k == 1) {
			Biome biome = Biome.REGISTRY.get(i);

			if(biome == null || !biome.hasParent()) {
				biome3 = biome == null ? null : biome.getChild();
				return biome3 == null ? i : biome3.getId();
			}
		}

		if(this.nextInt(3) == 0 || k == 0) {
			int l = i;
			if (i == Biome.DESERT.getId()) {
				l = Biome.DESERT_HILLS.getId();
			} else if(i == Biome.FOREST.getId()) {
				l = Biome.WOODED_HILLS.getId();
			} else if(i == Biome.BIRCH_FOREST.getId()) {
				l = Biome.BIRCH_FOREST_HILLS.getId();
			} else if(i == Biome.DARK_FOREST.getId()) {
				l = Biome.PLAINS.getId();
			} else if(i == Biome.TAIGA.getId()) {
				l = Biome.TAIGA_HILLS.getId();
			} else if(i == Biome.GIANT_TREE_TAIGA.getId()) {
				l = Biome.GIANT_TREE_TAIGA_HILLS.getId();
			} else if(i == Biome.SNOWY_TAIGA.getId()) {
				l = Biome.SNOWY_TAIGA_HILLS.getId();
			} else if(i == Biome.PLAINS.getId()) {
				l = this.nextInt(3) == 0 ? Biome.WOODED_HILLS.getId() : Biome.FOREST.getId();
			} else if(i == Biome.SNOWY_TUNDRA.getId()) {
				l = Biome.SNOWY_MOUNTAINS.getId();
			} else if(i == Biome.JUNGLE.getId()) {
				l = Biome.JUNGLE_HILLS.getId();
			} else if(i == Biome.BAMBOO_JUNGLE.getId()) {
				l = Biome.BAMBOO_JUNGLE_HILLS.getId();
			} else if(i == Biome.OCEAN.getId()) {
				l = Biome.DEEP_OCEAN.getId();
			} else if(i == Biome.LUKEWARM_OCEAN.getId()) {
				l = Biome.DEEP_LUKEWARM_OCEAN.getId();
			} else if(i == Biome.COLD_OCEAN.getId()) {
				l = Biome.DEEP_COLD_OCEAN.getId();
			} else if(i == Biome.FROZEN_OCEAN.getId()) {
				l = Biome.DEEP_FROZEN_OCEAN.getId();
			} else if(i == Biome.MOUNTAINS.getId()) {
				l = Biome.WOODED_MOUNTAINS.getId();
			} else if(i == Biome.SAVANNA.getId()) {
				l = Biome.SAVANNA_PLATEAU.getId();
			} else if(Biome.areSimilar(i, Biome.WOODED_BADLANDS_PLATEAU)) {
				l = Biome.BADLANDS.getId();
			}
			// in 1.12 this check is only for DEEP_OCEAN but since the other can't spawn, its ok
			else if((i == Biome.DEEP_OCEAN.getId() || i == Biome.DEEP_LUKEWARM_OCEAN.getId()
					|| i == Biome.DEEP_COLD_OCEAN.getId() || i == Biome.DEEP_FROZEN_OCEAN.getId())
					&& this.nextInt(3) == 0) {
				l = this.nextInt(2) == 0 ? Biome.PLAINS.getId() : Biome.FOREST.getId();
			}

			if(k == 0 && l != i) {
				biome3 = Biome.REGISTRY.get(l).getChild();
				l = biome3 == null ? i : biome3.getId();
			}

			if(l != i) {
				int m = 0;
				Biome b = Biome.REGISTRY.get(i);
				if(Biome.areSimilar(this.getParent(0).get(x, y,z - 1), b))m++;
				if(Biome.areSimilar(this.getParent(0).get(x + 1, y, z), b))m++;
				if(Biome.areSimilar(this.getParent(0).get(x - 1, y, z), b))m++;
				if(Biome.areSimilar(this.getParent(0).get(x, y,z + 1), b))m++;
				if(m >= 3)return l;
			}
		}

		return i;
	}
}
