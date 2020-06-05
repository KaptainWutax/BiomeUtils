package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.seed.WorldSeed;
import kaptainwutax.seedutils.prng.SeedMixer;

public class VoronoiLayer extends BiomeLayer {

	public final long seed;

	public VoronoiLayer(long worldSeed, boolean doHashing, BiomeLayer parent) {
		super(worldSeed, doHashing?0L:10L, parent);
		this.seed = doHashing ? WorldSeed.toHash(worldSeed) : worldSeed;
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

		for(int cell = 0; cell < 8; ++cell) {
			boolean bl = (cell & 4) == 0;
			boolean bl2 = (cell & 2) == 0;
			boolean bl3 = (cell & 1) == 0;
			int aa = bl ? l : l + 1;
			int ab = bl2 ? m : m + 1;
			int ac = bl3 ? n : n + 1;
			double g = bl ? d : d - 1.0D;
			double h = bl2 ? e : e - 1.0D;
			double s = bl3 ? f : f - 1.0D;
			ds[cell] = calcSquaredDistance(this.seed, aa, ab, ac, g, h, s);
		}

		int index = 0;
		double min = ds[0];

		for(int cell = 1; cell < 8; ++cell) {
			if (min > ds[cell]) {
				index = cell;
				min = ds[cell];
			}
		}

		int xFinal = (index & 4) == 0 ? l : l + 1;
		int zFinal = (index & 1) == 0 ? n : n + 1;
		return this.parent.get(xFinal, zFinal);
	}

	private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
		long mixedSeed = SeedMixer.mixSeed(seed, x);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, y);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, x);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, y);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
		double d = distribute(mixedSeed);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, seed);
		double e = distribute(mixedSeed);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, seed);
		double f = distribute(mixedSeed);
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
