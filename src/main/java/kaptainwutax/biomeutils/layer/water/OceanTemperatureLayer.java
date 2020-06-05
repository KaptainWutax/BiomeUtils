package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.noise.PerlinNoiseSampler;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.prng.lcg.java.JRand;

public class OceanTemperatureLayer extends BiomeLayer {

	private final PerlinNoiseSampler perlin;

	public OceanTemperatureLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
		this.perlin = new PerlinNoiseSampler(new JRand(worldSeed));
	}

	@Override
	public int sample(int x, int z) {
		double d = this.perlin.sample((double)x / 8.0D, (double)z / 8.0D, 0.0D, 0.0D, 0.0D);
		
		if (d > 0.4D) {
			return Biome.WARM_OCEAN.getId();
		} else if (d > 0.2D) {
			return Biome.LUKEWARM_OCEAN.getId();
		} else if (d < -0.4D) {
			return Biome.FROZEN_OCEAN.getId();
		} else {
			return d < -0.2D ? Biome.COLD_OCEAN.getId() : Biome.OCEAN.getId();
		}
	}

	public static class Apply extends BiomeLayer {
		public Apply(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
			super(version, worldSeed, salt, parents);
		}

		@Override
		public int sample(int x, int z) {
			int i = this.getParent(0).get(x, z);
			int j = this.getParent(1).get(x, z);
			if (!Biome.isOcean(i))return i;

			for(int m = -8; m <= 8; m += 4) {
				for(int n = -8; n <= 8; n += 4) {
					int o = this.getParent(0).get(x + m, z + n);

					if (!Biome.isOcean(o)) {
						if (j == Biome.WARM_OCEAN.getId()) {
							return Biome.LUKEWARM_OCEAN.getId();
						}

						if (j == Biome.FROZEN_OCEAN.getId()) {
							return Biome.COLD_OCEAN.getId();
						}
					}
				}
			}

			if (i == Biome.DEEP_OCEAN.getId()) {
				if (j == Biome.LUKEWARM_OCEAN.getId()) {
					return Biome.DEEP_LUKEWARM_OCEAN.getId();
				}

				if (j == Biome.OCEAN.getId()) {
					return Biome.DEEP_OCEAN.getId();
				}

				if (j == Biome.COLD_OCEAN.getId()) {
					return Biome.DEEP_COLD_OCEAN.getId();
				}

				if (j == Biome.FROZEN_OCEAN.getId()) {
					return Biome.DEEP_FROZEN_OCEAN.getId();
				}
			}

			return j;
		}
	}

	@Override
	public int getScale() {
		return this.scale == -1 ? this.scale = 256 : this.scale;
	}

}
