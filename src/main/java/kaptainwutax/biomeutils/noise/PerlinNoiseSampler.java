package kaptainwutax.biomeutils.noise;

import kaptainwutax.seedutils.lcg.rand.JRand;

public class PerlinNoiseSampler {

	private final byte[] permutations;
	public final double originX;
	public final double originY;
	public final double originZ;

	public PerlinNoiseSampler(JRand rand) {
		this.originX = rand.nextDouble() * 256.0D;
		this.originY = rand.nextDouble() * 256.0D;
		this.originZ = rand.nextDouble() * 256.0D;
		this.permutations = new byte[256];

		int j;
		for(j = 0; j < 256; ++j) {
			this.permutations[j] = (byte)j;
		}

		for(j = 0; j < 256; ++j) {
			int k = rand.nextInt(256 - j);
			byte b = this.permutations[j];
			this.permutations[j] = this.permutations[j + k];
			this.permutations[j + k] = b;
		}

	}

	public double sample(double x, double y, double z, double d, double e) {
		double f = x + this.originX;
		double g = y + this.originY;
		double h = z + this.originZ;
		int i = floor(f);
		int j = floor(g);
		int k = floor(h);
		double l = f - (double)i;
		double m = g - (double)j;
		double n = h - (double)k;
		double o = perlinFade(l);
		double p = perlinFade(m);
		double q = perlinFade(n);
		double t;
		if (d != 0.0D) {
			double r = Math.min(e, m);
			t = (double)floor(r / d) * d;
		} else {
			t = 0.0D;
		}

		return this.sample(i, j, k, l, m - t, n, o, p, q);
	}

	private static double grad(int hash, double x, double y, double z) {
		return SimplexNoiseSampler.dot(SimplexNoiseSampler.GRADIENTS[hash & 15], x, y, z);
	}

	private int getGradient(int hash) {
		return this.permutations[hash & 255] & 255;
	}

	public double sample(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX, double fadeLocalY, double fadeLocalZ) {
		int i = this.getGradient(sectionX) + sectionY;
		int j = this.getGradient(i) + sectionZ;
		int k = this.getGradient(i + 1) + sectionZ;
		int l = this.getGradient(sectionX + 1) + sectionY;
		int m = this.getGradient(l) + sectionZ;
		int n = this.getGradient(l + 1) + sectionZ;
		double d = grad(this.getGradient(j), localX, localY, localZ);
		double e = grad(this.getGradient(m), localX - 1.0D, localY, localZ);
		double f = grad(this.getGradient(k), localX, localY - 1.0D, localZ);
		double g = grad(this.getGradient(n), localX - 1.0D, localY - 1.0D, localZ);
		double h = grad(this.getGradient(j + 1), localX, localY, localZ - 1.0D);
		double o = grad(this.getGradient(m + 1), localX - 1.0D, localY, localZ - 1.0D);
		double p = grad(this.getGradient(k + 1), localX, localY - 1.0D, localZ - 1.0D);
		double q = grad(this.getGradient(n + 1), localX - 1.0D, localY - 1.0D, localZ - 1.0D);
		return lerp3(fadeLocalX, fadeLocalY, fadeLocalZ, d, e, f, g, h, o, p, q);
	}

	public static int floor(double d) {
		int i = (int)d;
		return d < (double)i ? i - 1 : i;
	}

	public static double lerp3(double deltaX, double deltaY, double deltaZ, double val000, double val100, double val010, double val110, double val001, double val101, double val011, double val111) {
		return lerp(deltaZ, lerp2(deltaX, deltaY, val000, val100, val010, val110), lerp2(deltaX, deltaY, val001, val101, val011, val111));
	}

	public static double lerp2(double deltaX, double deltaY, double val00, double val10, double val01, double val11) {
		return lerp(deltaY, lerp(deltaX, val00, val10), lerp(deltaX, val01, val11));
	}

	public static double lerp(double delta, double start, double end) {
		return start + delta * (end - start);
	}

	public static double perlinFade(double d) {
		return d * d * d * (d * (d * 6.0D - 15.0D) + 10.0D);
	}

}
