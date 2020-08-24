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
		double normalizedNoise = this.perlin.sample((double)x / 8.0D, (double)z / 8.0D, 0.0D, 0.0D, 0.0D);
		
		if(normalizedNoise > 0.4D) {
			return Biome.WARM_OCEAN.getId();
		} else if(normalizedNoise > 0.2D) {
			return Biome.LUKEWARM_OCEAN.getId();
		} else if(normalizedNoise < -0.4D) {
			return Biome.FROZEN_OCEAN.getId();
		} else if(normalizedNoise < -0.2D) {
			return Biome.COLD_OCEAN.getId();
		}

		return Biome.OCEAN.getId();
	}

	public static class Apply extends BiomeLayer {
		public Apply(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
			super(version, worldSeed, salt, parents);
		}

		@Override
		public int sample(int x, int y, int z) {
			int fullStackCenter = this.getParent(0).get(x, y, z);
			if(!Biome.isOcean(fullStackCenter))return fullStackCenter;

			int oceanStackCenter = this.getParent(1).get(x, y, z);

			for(int rx = -8; rx <= 8; rx += 4) {
				for(int rz = -8; rz <= 8; rz += 4) {
					int shiftedXZ = this.getParent(0).get(x + rx, y, z + rz);
					if(Biome.isOcean(shiftedXZ))continue;

					if(oceanStackCenter == Biome.WARM_OCEAN.getId()) {
						return Biome.LUKEWARM_OCEAN.getId();
					} else if(oceanStackCenter == Biome.FROZEN_OCEAN.getId()) {
						return Biome.COLD_OCEAN.getId();
					}
				}
			}

			if(fullStackCenter != Biome.DEEP_OCEAN.getId())return oceanStackCenter;

			if(oceanStackCenter == Biome.LUKEWARM_OCEAN.getId()) {
				return Biome.DEEP_LUKEWARM_OCEAN.getId();
			} else if(oceanStackCenter == Biome.OCEAN.getId()) {
				return Biome.DEEP_OCEAN.getId();
			} else if(oceanStackCenter == Biome.COLD_OCEAN.getId()) {
				return Biome.DEEP_COLD_OCEAN.getId();
			} else if(oceanStackCenter == Biome.FROZEN_OCEAN.getId()) {
				return Biome.DEEP_FROZEN_OCEAN.getId();
			}

			return oceanStackCenter;
		}
	}

}
