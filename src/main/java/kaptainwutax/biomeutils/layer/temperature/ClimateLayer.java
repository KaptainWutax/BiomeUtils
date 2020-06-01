package kaptainwutax.biomeutils.layer.temperature;

import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class ClimateLayer {

	public static class Cold extends BiomeLayer {
		public Cold(long worldSeed, long salt, BiomeLayer parent) {
			super(worldSeed, salt, parent);
		}

		public Cold(long worldSeed, long salt) {
			this(worldSeed, salt, null);
		}

		@Override
		public int sample(int x, int z) {
			int i = parent.get(x, z);

			if(BiomeSource.isShallowOcean(i)) {
				return i;
			}

			this.setSeed(x, z);
			int v = this.nextInt(6);
			if(v == 0)return 4;
			return v == 1 ? 3 : 1;
		}
	}

	public static class Special extends BiomeLayer {
		public Special(long worldSeed, long salt, BiomeLayer parent) {
			super(worldSeed, salt, parent);
		}

		public Special(long worldSeed, long salt) {
			this(worldSeed, salt, null);
		}

		@Override
		public int sample(int x, int z) {
			int i = this.parent.get(x, z);

			if(BiomeSource.isShallowOcean(i))return i;
			this.setSeed(x, z);

			if(this.nextInt(13) == 0) {
				i |= 1 + this.nextInt(15) << 8 & 3840;
			}

			return i;
		}
	}

	public static class Cool extends CrossLayer {
		public Cool(long worldSeed, long salt, BiomeLayer parent) {
			super(worldSeed, salt, parent);
		}

		public Cool(long worldSeed, long salt) {
			this(worldSeed, salt, null);
		}

		@Override
		public int sample(int n, int e, int s, int w, int center) {
			return center != 4 || n != 1 && e != 1 && w != 1 && s != 1 && n != 2 && e != 2 && w != 2 && s != 2 ? center : 3;
		}
	}

	public static class Temperate extends CrossLayer {
		public Temperate(long worldSeed, long salt, BiomeLayer parent) {
			super(worldSeed, salt, parent);
		}

		public Temperate(long worldSeed, long salt) {
			this(worldSeed, salt, null);
		}

		@Override
		public int sample(int n, int e, int s, int w, int center) {
			return center != 1 || n != 3 && e != 3 && w != 3 && s != 3 && n != 4 && e != 4 && w != 4 && s != 4 ? center : 2;
		}
	}

}
