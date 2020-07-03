package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.seed.SeedMixer;
import kaptainwutax.seedutils.mc.seed.WorldSeed;

public class VoronoiLayer extends BiomeLayer {

    public final long seed;

    public VoronoiLayer(MCVersion version, long worldSeed, BiomeLayer parent) {
        super(version, worldSeed, version.isOlderThan(MCVersion.v1_15) ? 10L : 0L, parent);
        this.seed = version.isOlderThan(MCVersion.v1_15) ? worldSeed : WorldSeed.toHash(worldSeed);
    }

    @Override
    public int sample(int x, int y, int z) {
        int i = x - 2;
        int j = y - 2;
        int k = z - 2;
        int l = i >> 2;
        int m = j >> 2;
        int n = k >> 2;
        double d = (double) (i & 3) / 4.0D;
        double e = (double) (j & 3) / 4.0D;
        double f = (double) (k & 3) / 4.0D;
        double[] ds = new double[8];

        for (int cell = 0; cell < 8; ++cell) {
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

        for (int cell = 1; cell < 8; ++cell) {
            if (min > ds[cell]) {
                index = cell;
                min = ds[cell];
            }
        }

        int xFinal = (index & 4) == 0 ? l : l + 1;
        int yFinal = (index & 2) == 0 ? m : m + 1;
        int zFinal = (index & 1) == 0 ? n : n + 1;
        return this.getParent().get(xFinal, yFinal, zFinal);
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
        double d = (double) ((int) Math.floorMod(seed >> 24, 1024L)) / 1024.0D;
        return (d - 0.5D) * 0.9D;
    }

    private static double square(double d) {
        return d * d;
    }

}
