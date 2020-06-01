package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.seed.WorldSeed;
import kaptainwutax.seedutils.prng.SeedMixer;

public class VoronoiLayer extends BiomeLayer {

	private final long hashedSeed;

	public VoronoiLayer(long worldSeed, boolean doHashing, BiomeLayer parent) {
		super(worldSeed, 0, parent);
		this.hashedSeed = doHashing ? WorldSeed.toHash(worldSeed) : worldSeed;
	}

	public Biome sampleBiome(int x, int z) {
		return Biome.REGISTRY.get(this.sample(x, z));
	}

	@Override
	public int sample(int x, int z) {
		int i = x - 2;
		int j = -2;
		int k = z - 2;
		int l = i >> 2;
		int m = j >> 2;
		int n = k >> 2;
		double d = (double)(i & 3) / 4.0D;
		double e = (double)(j & 3) / 4.0D;
		double f = (double)(k & 3) / 4.0D;
		double[] ds = new double[8];

		int t;
		int aa;
		int ab;
		for(t = 0; t < 8; ++t) {
			boolean bl = (t & 4) == 0;
			boolean bl2 = (t & 2) == 0;
			boolean bl3 = (t & 1) == 0;
			aa = bl ? l : l + 1;
			ab = bl2 ? m : m + 1;
			int r = bl3 ? n : n + 1;
			double g = bl ? d : d - 1.0D;
			double h = bl2 ? e : e - 1.0D;
			double s = bl3 ? f : f - 1.0D;
			ds[t] = calcSquaredDistance(this.hashedSeed, aa, ab, r, g, h, s);
		}

		t = 0;
		double u = ds[0];

		int v;
		for(v = 1; v < 8; ++v) {
			if (u > ds[v]) {
				t = v;
				u = ds[v];
			}
		}

		v = (t & 4) == 0 ? l : l + 1;
		ab = (t & 1) == 0 ? n : n + 1;
		return this.parent.sample(v, ab);
	}

	private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
		long l = SeedMixer.mixSeed(seed, (long)x);
		l = SeedMixer.mixSeed(l, (long)y);
		l = SeedMixer.mixSeed(l, (long)z);
		l = SeedMixer.mixSeed(l, (long)x);
		l = SeedMixer.mixSeed(l, (long)y);
		l = SeedMixer.mixSeed(l, (long)z);
		double d = distribute(l);
		l = SeedMixer.mixSeed(l, seed);
		double e = distribute(l);
		l = SeedMixer.mixSeed(l, seed);
		double f = distribute(l);
		return square(zFraction + f) + square(yFraction + e) + square(xFraction + d);
	}

	private static double distribute(long seed) {
		double d = (double)((int)Math.floorMod(seed >> 24, 1024L)) / 1024.0D;
		return (d - 0.5D) * 0.9D;
	}

	private static double square(double d) {
		return d * d;
	}

}
