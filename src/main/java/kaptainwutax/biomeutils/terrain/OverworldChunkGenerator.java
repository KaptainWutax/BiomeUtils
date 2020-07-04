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
		for (int i = -2; i <= 2; ++i) {
			for (int j = -2; j <= 2; ++j) {
				float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
				BIOME_WEIGHT_TABLE[i + 2 + (j + 2) * 5] = f;
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
		// double d = 8.5D;
		double e = ((double) y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;
		if (e < 0.0D) {
			e *= 4.0D;
		}

		return e;
	}

	@Override
	protected double[] computeNoiseRange(int x, int z) {
		double[] ds = new double[2];
		float f = 0.0F;
		float g = 0.0F;
		float h = 0.0F;
		float j = this.biomeSource.getBiomeForNoiseGen(x, 0, z).getDepth();

		for (int k = -2; k <= 2; ++k) {
			for (int l = -2; l <= 2; ++l) {
				Biome biome = this.biomeSource.getBiomeForNoiseGen(x + k, 0, z + l);
				float m = biome.getDepth();
				float n = biome.getScale();
				if (this.amplified && m > 0.0F) {
					m = 1.0F + m * 2.0F;
					n = 1.0F + n * 4.0F;
				}

				float o = BIOME_WEIGHT_TABLE[k + 2 + (l + 2) * 5] / (m + 2.0F);
				if (biome.getDepth() > j) {
					o /= 2.0F;
				}

				f += n * o;
				g += m * o;
				h += o;
			}
		}

		f /= h;
		g /= h;
		f = f * 0.9F + 0.1F;
		g = (g * 4.0F - 1.0F) / 8.0F;
		ds[0] = (double) g + this.sampleNoise(x, z);
		ds[1] = (double) f;
		return ds;
	}

	private double sampleNoise(int x, int y) {
		double d = this.noiseSampler.sample((double) (x * 200), 10.0D, (double) (y * 200), 1.0D, 0.0D, true) / 8000.0D;
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
		this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D,
				4.277574920654297D, 3, -10);
	}
}