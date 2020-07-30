package kaptainwutax.biomeutils.terrain;

import kaptainwutax.biomeutils.MathHelper;
import kaptainwutax.biomeutils.noise.NoiseSampler;
import kaptainwutax.biomeutils.noise.OctavePerlinNoiseSampler;
import kaptainwutax.biomeutils.noise.OctaveSimplexNoiseSampler;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.ChunkRand;

import java.util.HashMap;
import java.util.Map;

public abstract class SurfaceChunkGenerator extends ChunkGenerator {

	private final int verticalNoiseResolution;
	private final int horizontalNoiseResolution;
	private final int noiseSizeX;
	private final int noiseSizeY;
	private final int noiseSizeZ;
	private final OctavePerlinNoiseSampler field_16574;
	private final OctavePerlinNoiseSampler field_16581;
	private final OctavePerlinNoiseSampler field_16575;
	private final NoiseSampler surfaceDepthNoise;
	protected final ChunkRand random;

	private final Map<Long, double[]> noiseColumnCache = new HashMap<>();

	public SurfaceChunkGenerator(BiomeSource biomeSource, int horizontalNoiseResolution, int verticalNoiseResolution,
			int worldHeight, boolean useSimplexNoise) {
		super(biomeSource);
		this.verticalNoiseResolution = verticalNoiseResolution;
		this.horizontalNoiseResolution = horizontalNoiseResolution;
		this.noiseSizeX = 16 / this.horizontalNoiseResolution;
		this.noiseSizeY = worldHeight / this.verticalNoiseResolution;
		this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
		this.random = new ChunkRand(biomeSource.getWorldSeed());
		this.field_16574 = new OctavePerlinNoiseSampler(this.random, 16);
		this.field_16581 = new OctavePerlinNoiseSampler(this.random, 16);
		this.field_16575 = new OctavePerlinNoiseSampler(this.random, 8);
		if (useSimplexNoise) {
			this.surfaceDepthNoise = new OctaveSimplexNoiseSampler(this.random, 4);
		} else {
			this.surfaceDepthNoise = new OctavePerlinNoiseSampler(this.random, 4);
		}
	}

	public int getNoiseSizeY() {
		return this.noiseSizeY + 1;
	}

	private double sampleNoise(int x, int y, int z, double d, double e, double f, double g) {
		double h = 0.0D;
		double i = 0.0D;
		double j = 0.0D;
		double k = 1.0D;

		for (int l = 0; l < 16; ++l) {
			double m = OctavePerlinNoiseSampler.maintainPrecision((double) x * d * k);
			double n = OctavePerlinNoiseSampler.maintainPrecision((double) y * e * k);
			double o = OctavePerlinNoiseSampler.maintainPrecision((double) z * d * k);
			double p = e * k;
			h += this.field_16574.getOctave(l).sample(m, n, o, p, (double) y * p) / k;
			i += this.field_16581.getOctave(l).sample(m, n, o, p, (double) y * p) / k;
			if (l < 8) {
				j += this.field_16575.getOctave(l).sample(
						OctavePerlinNoiseSampler.maintainPrecision((double) x * f * k),
						OctavePerlinNoiseSampler.maintainPrecision((double) y * g * k),
						OctavePerlinNoiseSampler.maintainPrecision((double) z * f * k), g * k, (double) y * g * k) / k;
			}
			k /= 2.0D;
		}

		return MathHelper.clampedLerp(h / 512.0D, i / 512.0D, (j / 10.0D + 1.0D) / 2.0D);
	}

	protected void sampleNoiseColumn(double[] buffer, int x, int z, double d, double e, double f, double g, int i, int j) {
		double[] ds = this.computeNoiseRange(x, z);
		double h = ds[0];
		double k = ds[1];
		double scale = this.getNoiseSizeY() - 4;
		double m = 0.0D;

		for (int ry = 0; ry < this.getNoiseSizeY(); ++ry) {
			double noise = this.sampleNoise(x, ry, z, d, e, f, g);
			noise -= this.computeNoiseFalloff(h, k, ry);
			if ((double) ry > scale) {
				noise = MathHelper.clampedLerp(noise, j, ((double) ry - scale) / (double) i);
			} else if ((double) ry < m) {
				noise = MathHelper.clampedLerp(noise, -30.0D, (m - (double) ry) / (m - 1.0D));
			}
			buffer[ry] = noise;
		}
	}

	protected double[] sampleNoiseColumn(int x, int z) {
		long key = ((((long) x) & 0xFFFFFFFFL) << 32) | (((long) z) & 0xFFFFFFFFL);
		if (noiseColumnCache.containsKey(key)) {
			return noiseColumnCache.get(key);
		} else {
			double[] ds = new double[this.noiseSizeY + 1];
			this.sampleNoiseColumn(ds, x, z);
			noiseColumnCache.put(key, ds);
			return ds;
		}
	}

	@Override
	public int getHeightOnGround(int x, int z) {
		int i = Math.floorDiv(x, this.horizontalNoiseResolution);
		int j = Math.floorDiv(z, this.horizontalNoiseResolution);
		int k = Math.floorMod(x, this.horizontalNoiseResolution);
		int l = Math.floorMod(z, this.horizontalNoiseResolution);
		double d = (double) k / (double) this.horizontalNoiseResolution;
		double e = (double) l / (double) this.horizontalNoiseResolution;
		double[][] ds = new double[][] { this.sampleNoiseColumn(i, j), this.sampleNoiseColumn(i, j + 1),
				this.sampleNoiseColumn(i + 1, j), this.sampleNoiseColumn(i + 1, j + 1) };

		for (int n = this.noiseSizeY - 1; n >= 0; --n) {
			double f = ds[0][n];
			double g = ds[1][n];
			double h = ds[2][n];
			double o = ds[3][n];
			double p = ds[0][n + 1];
			double q = ds[1][n + 1];
			double r = ds[2][n + 1];
			double s = ds[3][n + 1];

			for (int t = this.verticalNoiseResolution - 1; t >= 0; --t) {
				double u = (double) t / (double) this.verticalNoiseResolution;
				double v = MathHelper.lerp3(u, d, e, f, p, h, r, g, q, o, s);
				int w = n * this.verticalNoiseResolution + t;
				if (v > 0.0D) {
					return w + 1;
				}
			}
		}

		return 0;
	}

	protected abstract void sampleNoiseColumn(double[] buffer, int x, int z);

	protected abstract double[] computeNoiseRange(int x, int z);

	protected abstract double computeNoiseFalloff(double depth, double scale, int y);

}