package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.noiseutils.perlin.PerlinNoiseSampler;
import kaptainwutax.seedutils.rand.JRand;

public class OceanTemperatureLayer extends IntBiomeLayer {

	private final PerlinNoiseSampler perlin;

	public OceanTemperatureLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
		this.perlin = new PerlinNoiseSampler(new JRand(worldSeed));
	}

	@Override
	public int sample(int x, int y, int z) {
		double normalizedNoise = this.perlin.sample((double)x / 8.0D, (double)z / 8.0D, 0.0D, 0.0D, 0.0D);

		if(normalizedNoise > 0.4D) {
			return Biomes.WARM_OCEAN.getId();
		} else if(normalizedNoise > 0.2D) {
			return Biomes.LUKEWARM_OCEAN.getId();
		} else if(normalizedNoise < -0.4D) {
			return Biomes.FROZEN_OCEAN.getId();
		} else if(normalizedNoise < -0.2D) {
			return Biomes.COLD_OCEAN.getId();
		}

		return Biomes.OCEAN.getId();
	}

	public static class Apply extends IntBiomeLayer {
		public Apply(MCVersion version, long worldSeed, long salt, IntBiomeLayer... parents) {
			super(version, worldSeed, salt, parents);
		}

		@Override
		public int sample(int x, int y, int z) {
			int fullStackCenter = this.getParent(0, IntBiomeLayer.class).get(x, y, z);
			if(!Biome.isOcean(fullStackCenter)) return fullStackCenter;

			int oceanStackCenter = this.getParent(1, IntBiomeLayer.class).get(x, y, z);

			for(int rx = -8; rx <= 8; rx += 4) {
				for(int rz = -8; rz <= 8; rz += 4) {
					int shiftedXZ = this.getParent(0, IntBiomeLayer.class).get(x + rx, y, z + rz);
					if(Biome.isOcean(shiftedXZ)) continue;

					if(oceanStackCenter == Biomes.WARM_OCEAN.getId()) {
						return Biomes.LUKEWARM_OCEAN.getId();
					} else if(oceanStackCenter == Biomes.FROZEN_OCEAN.getId()) {
						return Biomes.COLD_OCEAN.getId();
					}
				}
			}

			if(fullStackCenter != Biomes.DEEP_OCEAN.getId()) return oceanStackCenter;

			if(oceanStackCenter == Biomes.LUKEWARM_OCEAN.getId()) {
				return Biomes.DEEP_LUKEWARM_OCEAN.getId();
			} else if(oceanStackCenter == Biomes.OCEAN.getId()) {
				return Biomes.DEEP_OCEAN.getId();
			} else if(oceanStackCenter == Biomes.COLD_OCEAN.getId()) {
				return Biomes.DEEP_COLD_OCEAN.getId();
			} else if(oceanStackCenter == Biomes.FROZEN_OCEAN.getId()) {
				return Biomes.DEEP_FROZEN_OCEAN.getId();
			}

			return oceanStackCenter;
		}
	}

}
