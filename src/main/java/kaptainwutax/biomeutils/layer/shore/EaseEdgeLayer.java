package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class EaseEdgeLayer extends CrossLayer {

	public EaseEdgeLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public EaseEdgeLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		int[] is = new int[1];
		if (!this.method_15841(is, n, e, s, w, center, Biome.MOUNTAINS, Biome.MOUNTAIN_EDGE)
				&& !this.method_15840(is, n, e, s, w, center, Biome.WOODED_BADLANDS_PLATEAU, Biome.BADLANDS)
				&& !this.method_15840(is, n, e, s, w, center, Biome.BADLANDS_PLATEAU, Biome.BADLANDS)
				&& !this.method_15840(is, n, e, s, w, center, Biome.GIANT_TREE_TAIGA, Biome.TAIGA)) {

			if(center == Biome.DESERT.getId() && anyMatch(Biome.SNOWY_TUNDRA, n, e, w, s)) {
				return Biome.WOODED_MOUNTAINS.getId();
			} else {
				if(center == Biome.SWAMP.getId()) {
					if(anyMatch(Biome.DESERT, n, e, w, s) || anyMatch(Biome.SNOWY_TUNDRA, n, e, w, s) || anyMatch(Biome.SNOWY_TAIGA, n, e, w, s)) {
						return Biome.PLAINS.getId();
					}

					if(anyMatch(Biome.JUNGLE, n, e, w, s) || anyMatch(Biome.BAMBOO_JUNGLE, n, e, w, s)) {
						return Biome.JUNGLE_EDGE.getId();
					}
				}

				return center;
			}
		}

		return is[0];
	}

	public static boolean anyMatch(Biome biome, int... values) {
		for(int value: values) {
			if(value == biome.getId())return true;
		}

		return false;
	}

	private boolean method_15841(int[] is, int i, int j, int k, int l, int m, Biome n, Biome o) {
		if (!BiomeSource.areSimilar(m, n)) {
			return false;
		} else {
			if (this.method_15839(i, n) && this.method_15839(j, n) && this.method_15839(l, n) && this.method_15839(k, n)) {
				is[0] = m;
			} else {
				is[0] = o.getId();
			}

			return true;
		}
	}

	private boolean method_15840(int[] is, int i, int j, int k, int l, int m, Biome n, Biome o) {
		if(m != n.getId())return false;

		if(BiomeSource.areSimilar(i, n) && BiomeSource.areSimilar(j, n)
				&& BiomeSource.areSimilar(l, n) && BiomeSource.areSimilar(k, n)) {
			is[0] = m;
		} else {
			is[0] = o.getId();
		}

		return true;
	}

	private boolean method_15839(int id, Biome b2) {
		if (BiomeSource.areSimilar(id, b2))return true;

		Biome biome = Biome.REGISTRY.get(id);

		if(biome != null && b2 != null) {
			Biome.Temperature t = biome.getTemperatureGroup();
			Biome.Temperature t2 = b2.getTemperatureGroup();
			return t == t2 || t == Biome.Temperature.MEDIUM || t2 == Biome.Temperature.MEDIUM;
		}

		return false;
	}

}

