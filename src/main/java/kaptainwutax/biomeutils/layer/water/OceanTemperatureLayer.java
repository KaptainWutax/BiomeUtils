package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.noise.PerlinNoiseSampler;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.MCVersion;

public class OceanTemperatureLayer extends BiomeLayer {

	private final PerlinNoiseSampler perlin;

	public OceanTemperatureLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
		this.perlin = new PerlinNoiseSampler(new JRand(worldSeed));
	}

	@Override
	public int sample(int x, int y, int z) {
		double normalized_noise = this.perlin.sample((double)x / 8.0D, (double)z / 8.0D, 0.0D, 0.0D, 0.0D);
		
		if (normalized_noise > 0.4D) {
			return Biome.WARM_OCEAN.getId();
		} else if (normalized_noise > 0.2D) {
			return Biome.LUKEWARM_OCEAN.getId();
		} else if (normalized_noise < -0.4D) {
			return Biome.FROZEN_OCEAN.getId();
		} else {
			return normalized_noise < -0.2D ? Biome.COLD_OCEAN.getId() : Biome.OCEAN.getId();
		}
	}

	public static class Apply extends BiomeLayer {
		public Apply(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
			super(version, worldSeed, salt, parents);
		}

		@Override
		public int sample(int x, int y, int z) {
			int full_stack_center = this.getParent(0).get(x, y, z);
			int ocean_stack_center = this.getParent(1).get(x, y, z);
			if (!Biome.isOcean(full_stack_center))return full_stack_center;

			for(int rx = -8; rx <= 8; rx += 4) {
				for(int rz = -8; rz <= 8; rz += 4) {
					int shifted_xz = this.getParent(0).get(x + rx, y, z + rz);

					if (!Biome.isOcean(shifted_xz)) {
						if (ocean_stack_center == Biome.WARM_OCEAN.getId()) {
							return Biome.LUKEWARM_OCEAN.getId();
						}

						if (ocean_stack_center == Biome.FROZEN_OCEAN.getId()) {
							return Biome.COLD_OCEAN.getId();
						}
					}
				}
			}

			if (full_stack_center == Biome.DEEP_OCEAN.getId()) {
				if (ocean_stack_center == Biome.LUKEWARM_OCEAN.getId()) {
					return Biome.DEEP_LUKEWARM_OCEAN.getId();
				}

				if (ocean_stack_center == Biome.OCEAN.getId()) {
					return Biome.DEEP_OCEAN.getId();
				}

				if (ocean_stack_center == Biome.COLD_OCEAN.getId()) {
					return Biome.DEEP_COLD_OCEAN.getId();
				}

				if (ocean_stack_center == Biome.FROZEN_OCEAN.getId()) {
					return Biome.DEEP_FROZEN_OCEAN.getId();
				}
			}

			return ocean_stack_center;
		}
	}

}
