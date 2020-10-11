package kaptainwutax.biomeutils.terrain;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.MathHelper;
import kaptainwutax.biomeutils.noise.OctavePerlinNoiseSampler;
import kaptainwutax.biomeutils.source.BiomeSource;

public class OverworldChunkGenerator extends SurfaceChunkGenerator {

	private static final float[] BIOME_WEIGHT_TABLE;
	private final OctavePerlinNoiseSampler noiseSampler;
	private final boolean amplified;

	static {
		BIOME_WEIGHT_TABLE = new float[25];
		for (int rx = -2; rx <= 2; ++rx) {
			for (int rz = -2; rz <= 2; ++rz) {
				float f = 10.0F / MathHelper.sqrt((float) (rx * rx + rz * rz) + 0.2F);
				BIOME_WEIGHT_TABLE[rx + 2 + (rz + 2) * 5] = f;
			}
		}
	}

	public OverworldChunkGenerator(BiomeSource biomeSource) {
		super(biomeSource, 4, 8, 256, true);
		this.random.advance(2620);
		this.noiseSampler = new OctavePerlinNoiseSampler(this.random, 16);
		this.amplified = false;
	}

	@Override
	protected double computeNoiseFalloff(double depth, double scale, int y) {
		double fallOff = ((double) y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;

		if(fallOff < 0.0D) {
			fallOff *= 4.0D;
		}

		return fallOff;
	}

	@Override
	protected double[] computeNoiseRange(int x, int z) {
		double[] ds = new double[2];
		float f = 0.0F;
		float g = 0.0F;
		float h = 0.0F;
		float j = this.biomeSource.getBiomeForNoiseGen(x, 0, z).getDepth();

		for (int rx = -2; rx <= 2; ++rx) {
			for (int rz = -2; rz <= 2; ++rz) {
				Biome biome = this.biomeSource.getBiomeForNoiseGen(x + rx, 0, z + rz);
				float depth = biome.getDepth();
				float scale = biome.getScale();
				if (this.amplified && depth > 0.0F) {
					depth = 1.0F + depth * 2.0F;
					scale = 1.0F + scale * 4.0F;
				}

				float weight = BIOME_WEIGHT_TABLE[rx + 2 + (rz + 2) * 5] / (depth + 2.0F);
				if (biome.getDepth() > j) {
					weight /= 2.0F;
				}

				f += scale * weight;
				g += depth * weight;
				h += weight;
			}
		}

		f /= h;
		g /= h;
		f = f * 0.9F + 0.1F;
		g = (g * 4.0F - 1.0F) / 8.0F;
		ds[0] = (double)g + this.sampleNoise(x, z);
		ds[1] = f;
		return ds;
	}

	private double sampleNoise(int x, int y) {
		double d = this.noiseSampler.sample(x * 200, 10.0D, y * 200, 1.0D, 0.0D, true) / 8000.0D;
		if (d < 0.0D) {
			d = -d * 0.3D;
		}

		d = d * 3.0D - 2.0D;
		if (d < 0.0D) {
			d /= 28.0D;
		} else {
			if (d > 1.0D) {
				d = 1.0D;
			}
			d /= 40.0D;
		}

		return d;
	}

	@Override
	protected void sampleNoiseColumn(double[] buffer, int x, int z) {
		this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
	}

}