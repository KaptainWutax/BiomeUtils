package kaptainwutax.biomeutils.layer.temperature;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class ClimateLayer {

	public static class Cold extends BiomeLayer {
		public Cold(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int sample(int x, int y, int z) {
			int value = this.getParent().get(x, y, z);

			if(Biome.isShallowOcean(value)) {
				return value;
			}

			this.setSeed(x, z);
			int i = this.nextInt(6);
			if(i == 0)return Biome.FOREST.getId();
			return i == 1 ? Biome.MOUNTAINS.getId() : Biome.PLAINS.getId();
		}
	}

	public static class Temperate extends CrossLayer {
		public Temperate(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int sample(int n, int e, int s, int w, int center) {
			return center != Biome.PLAINS.getId() || n != Biome.MOUNTAINS.getId() && e != Biome.MOUNTAINS.getId()
					&& w != Biome.MOUNTAINS.getId() && s != Biome.MOUNTAINS.getId() && n != Biome.FOREST.getId()
					&& e != Biome.FOREST.getId() && w != Biome.FOREST.getId()
					&& s != Biome.FOREST.getId() ? center : Biome.DESERT.getId();
		}
	}

	public static class Cool extends CrossLayer {
		public Cool(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int sample(int n, int e, int s, int w, int center) {
			return center != Biome.FOREST.getId() || n != Biome.PLAINS.getId() && e != Biome.PLAINS.getId()
					&& w != Biome.PLAINS.getId() && s != Biome.PLAINS.getId() && n != Biome.DESERT.getId()
					&& e != Biome.DESERT.getId() && w != Biome.DESERT.getId()
					&& s != Biome.DESERT.getId() ? center : Biome.MOUNTAINS.getId();
		}
	}

	public static class Special extends BiomeLayer {
		public Special(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
			super(version, worldSeed, salt, parent);
		}

		@Override
		public int sample(int x, int y, int z) {
			int i = this.getParent().get(x, y, z);

			if(Biome.isShallowOcean(i))return i;
			this.setSeed(x, z);

			if(this.nextInt(13) == 0) {
				i |= (1 + this.nextInt(15)) << 8;
			}

			return i;
		}
	}

}
