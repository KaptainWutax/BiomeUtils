package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.concurrent.Callable;

public class HillsLayer extends IntBiomeLayer {

	public HillsLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
	}

	@Override
	public int sample(int x, int y, int z) {

		IntBiomeLayer biomesLayer = this.getParent(0, IntBiomeLayer.class); // biomes
		IntBiomeLayer noiseLayer = this.getParent(1, IntBiomeLayer.class); // noise (river)
		int value = biomesLayer.get(x, y, z);
		this.setSeed(x, z);
		boolean toHills = this.nextInt(3) == 0;
		int mutation = -1;

		if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_7_2)) {
			int j = noiseLayer.get(x, y, z);
			mutation = (j - 2) % 29;
			if (!Biome.isShallowOcean(value, this) && j >= 2 && mutation == 1) {
				Biome biome = Biomes.REGISTRY.get(value);

				if (biome == null || !biome.hasParent()) {
					Biome mutated = biome == null ? null : biome.getChild();
					return mutated == null ? value : mutated.getId();
				}
			}
			toHills |= (mutation == 0);
		}

		return compute(toHills,value,mutation,()->computeCrossLayer(biomesLayer,value,x,y,z));
	}

	private int compute(boolean toHills, int value, int mutation, Callable<Integer> getCrossLayer){
		if (toHills) {
			int l = value;
			if (value == Biomes.DESERT.getId()) {
				l = Biomes.DESERT_HILLS.getId();
			} else if (value == Biomes.FOREST.getId()) {
				l = Biomes.WOODED_HILLS.getId();
			} else if (value == Biomes.BIRCH_FOREST.getId()) {
				l = Biomes.BIRCH_FOREST_HILLS.getId();
			} else if (value == Biomes.DARK_FOREST.getId()) {
				l = Biomes.PLAINS.getId();
			} else if (value == Biomes.TAIGA.getId()) {
				l = Biomes.TAIGA_HILLS.getId();
			} else if (value == Biomes.GIANT_TREE_TAIGA.getId()) {
				l = Biomes.GIANT_TREE_TAIGA_HILLS.getId();
			} else if (value == Biomes.SNOWY_TAIGA.getId()) {
				l = Biomes.SNOWY_TAIGA_HILLS.getId();
			} else if (value == Biomes.PLAINS.getId()) {
				boolean forestHills = false;
				if (is1_7up.call()) forestHills = this.nextInt(3) == 0;
				l = forestHills ? Biomes.WOODED_HILLS.getId() : Biomes.FOREST.getId();
			} else if (value == Biomes.SNOWY_TUNDRA.getId()) {
				l = Biomes.SNOWY_MOUNTAINS.getId();
			} else if (value == Biomes.JUNGLE.getId()) {
				l = Biomes.JUNGLE_HILLS.getId();
			} else if (value == Biomes.BAMBOO_JUNGLE.getId()) {
				l = Biomes.BAMBOO_JUNGLE_HILLS.getId();
			} else if (value == Biomes.OCEAN.getId()) {
				l = is1_6down.call() ? Biomes.OCEAN.getId() : Biomes.DEEP_OCEAN.getId();
			} else if (value == Biomes.LUKEWARM_OCEAN.getId()) {
				l = Biomes.DEEP_LUKEWARM_OCEAN.getId();
			} else if (value == Biomes.COLD_OCEAN.getId()) {
				l = Biomes.DEEP_COLD_OCEAN.getId();
			} else if (value == Biomes.FROZEN_OCEAN.getId()) {
				l = Biomes.DEEP_FROZEN_OCEAN.getId();
			} else if (value == Biomes.MOUNTAINS.getId()) {
				l = is1_6down.call() ? Biomes.MOUNTAINS.getId() : Biomes.WOODED_MOUNTAINS.getId();
			} else if (value == Biomes.SAVANNA.getId()) {
				l = Biomes.SAVANNA_PLATEAU.getId();
			} else if (Biome.areSimilar(value, Biomes.WOODED_BADLANDS_PLATEAU, this)) {
				l = Biomes.BADLANDS.getId();
			}
			// in 1.12 this check is only for DEEP_OCEAN but since the other can't spawn, its ok
			else if ((value == Biomes.DEEP_OCEAN.getId() || value == Biomes.DEEP_LUKEWARM_OCEAN.getId()
					|| value == Biomes.DEEP_COLD_OCEAN.getId() || value == Biomes.DEEP_FROZEN_OCEAN.getId())
					&& this.nextInt(3) == 0) {
				l = this.nextInt(2) == 0 ? Biomes.PLAINS.getId() : Biomes.FOREST.getId();
			}

			if (is1_7up.call()) {
				if (mutation == 0 && l != value) {
					Biome mutated = Biomes.REGISTRY.get(l).getChild();
					l = mutated == null ? value : mutated.getId();
				}
			}

			if (l != value) {
				int m=0;
				try {
					m=getCrossLayer.call();
				}catch (Exception e){
					e.printStackTrace();
				}
				if (is1_6down.call()) {
					if (m == 4) return l;
				} else if (m >= 3) return l;
			}
		}

		return value;
	}

	private int computeCrossLayer(IntBiomeLayer biomesLayer, int value, int x,int y,int z){
		int m = 0;
		Biome b = Biomes.REGISTRY.get(value);
		if (Biome.areSimilar(biomesLayer.get(x, y, z - 1), b, this)) m++;
		if (Biome.areSimilar(biomesLayer.get(x + 1, y, z), b, this)) m++;
		if (Biome.areSimilar(biomesLayer.get(x - 1, y, z), b, this)) m++;
		if (Biome.areSimilar(biomesLayer.get(x, y, z + 1), b, this)) m++;
		return m;
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		// TODO very complex
		System.out.println(this.getClass().getName()+" "+x+" "+z+" "+xSize+" "+zSize+" : "+this.getScale());
		for (int offsetX = -2; offsetX <= 2; offsetX++) {
			for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {

			}
		}
		return new int[xSize*ySize*zSize];
	}

}
