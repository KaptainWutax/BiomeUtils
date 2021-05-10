package kaptainwutax.biomeutils.layer.temperature;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.ComputeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class ClimateLayer {

	public static class Cold extends ComputeLayer {
		public Cold(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		public int compute(int value,int x,int z){
			if (Biome.isShallowOcean(value, this)) {
				return value;
			}

			this.setSeed(x, z);

			if (is1_6down.call()) {
				int i = this.nextInt(5);
				return i == 0 ? Biomes.SNOWY_TUNDRA.getId() : Biomes.PLAINS.getId();
			}
			int i = this.nextInt(6);
			if (i == 0) return Biomes.FOREST.getId();
			return i == 1 ? Biomes.MOUNTAINS.getId() : Biomes.PLAINS.getId();
		}
	}

	public static class Temperate extends CrossLayer {
		public Temperate(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int sample(int n, int e, int s, int w, int center) {
			return center != Biomes.PLAINS.getId() || n != Biomes.MOUNTAINS.getId() && e != Biomes.MOUNTAINS.getId()
					&& w != Biomes.MOUNTAINS.getId() && s != Biomes.MOUNTAINS.getId() && n != Biomes.FOREST.getId()
					&& e != Biomes.FOREST.getId() && w != Biomes.FOREST.getId()
					&& s != Biomes.FOREST.getId() ? center : Biomes.DESERT.getId();
		}
	}

	public static class Cool extends CrossLayer {
		public Cool(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int sample(int n, int e, int s, int w, int center) {
			return center != Biomes.FOREST.getId() || n != Biomes.PLAINS.getId() && e != Biomes.PLAINS.getId()
					&& w != Biomes.PLAINS.getId() && s != Biomes.PLAINS.getId() && n != Biomes.DESERT.getId()
					&& e != Biomes.DESERT.getId() && w != Biomes.DESERT.getId()
					&& s != Biomes.DESERT.getId() ? center : Biomes.MOUNTAINS.getId();
		}
	}

	public static class Special extends ComputeLayer {
		public Special(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int compute(int value, int x, int z) {
			if (Biome.isShallowOcean(value, this)) return value;
			this.setSeed(x, z);

			if (this.nextInt(13) == 0) {
				value |= (1 + this.nextInt(15)) << 8;
			}

			return value;
		}
	}

}
