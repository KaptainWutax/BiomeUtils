package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.seed.SeedMixer;
import kaptainwutax.seedutils.mc.seed.WorldSeed;

public class VoronoiLayer extends BiomeLayer {

    private final long seed;
    private final boolean is3D;

    public VoronoiLayer(MCVersion version, long worldSeed, boolean is3D, BiomeLayer parent) {
        super(version, worldSeed, version.isOlderThan(MCVersion.v1_15) ? 10L : 0L, parent);
        this.seed = version.isOlderThan(MCVersion.v1_15) ? worldSeed : WorldSeed.toHash(worldSeed);
        this.is3D = is3D;
    }

    public long getSeed() {
        return this.seed;
    }

    public boolean is3D() {
        return this.is3D;
    }

    @Override
    public int sample(int x, int y, int z) {
        return this.getVersion().isOlderThan(MCVersion.v1_15) ? this.sample14minus(x, z) : this.sample15plus(x, y, z);
    }

    private int sample14minus(int x, int z) {
        int offset;
        x -= 2;
        z -= 2;
        int pX = x >> 2;
        int pZ = z >> 2;
        int sX = pX << 2;
        int sZ = pZ << 2;
        double[] off_0_0 = calcOffset(this.layerSeed, sX, sZ, 0, 0);
        double[] off_1_0 = calcOffset(this.layerSeed, sX, sZ, 4, 0);
        double[] off_0_1 = calcOffset(this.layerSeed, sX, sZ, 0, 4);
        double[] off_1_1 = calcOffset(this.layerSeed, sX, sZ, 4, 4);

        int cell = (z & 3) * 4 + (x & 3);
        double corner0 = calcContribution(off_0_0, cell >> 2, cell & 3);
        double corner1 = calcContribution(off_1_0, cell >> 2, cell & 3);
        double corner2 = calcContribution(off_0_1, cell >> 2, cell & 3);
        double corner3 = calcContribution(off_1_1, cell >> 2, cell & 3);
        if (corner0 < corner1 && corner0 < corner2 && corner0 < corner3) {
            offset = 0;
        } else if (corner1 < corner0 && corner1 < corner2 && corner1 < corner3) {
            offset = 1;
        } else if (corner2 < corner0 && corner2 < corner1 && corner2 < corner3) {
            offset = 2;
        } else {
            offset = 3;
        }


        //  X -> (offset&1)
        // _________
        // | 0 | 1 |   Z (offset>>1)
        // |---|---|   |
        // | 2 | 3 |  \_/
        // |___|___|

        return this.getParent().get(pX + (offset & 1), 0, pZ + (offset >> 1));
    }

    private int sample15plus(int x, int y, int z) {
        int i = x - 2;
        int j = y - 2;
        int k = z - 2;
        int l = i >> 2;
        int m = j >> 2;
        int n = k >> 2;
        double d = (double)(i & 3) / 4.0D;
        double e = (double)(j & 3) / 4.0D;
        double f = (double)(k & 3) / 4.0D;
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

        for(int cell = 1; cell < 8; ++cell) {
            if(ds[cell] >= min)continue;
            index = cell;
            min = ds[cell];
        }

        int xFinal = (index & 4) == 0 ? l : l + 1;
        int yFinal = (index & 2) == 0 ? m : m + 1;
        int zFinal = (index & 1) == 0 ? n : n + 1;
        return this.getParent().get(xFinal, this.is3D ? yFinal : 0, zFinal);
    }

    private static double calcContribution(double[] d, int x, int z) {
        return ((double) x - d[1]) * ((double) x - d[1]) + ((double) z - d[0]) * ((double) z - d[0]);
    }

    private static double[] calcOffset(long layerSeed, int x, int z, int offX, int offZ) {
        long mixedSeed = SeedMixer.mixSeed(layerSeed, x + offX);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, z + offZ);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, x + offX);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, z + offZ);
        double d1 = (((double) ((int) Math.floorMod(mixedSeed >> 24, 1024L)) / 1024.0D) - 0.5D) * 3.6D + offX;
        mixedSeed = SeedMixer.mixSeed(mixedSeed, layerSeed);
        double d2 = (((double) ((int) Math.floorMod(mixedSeed >> 24, 1024L)) / 1024.0D) - 0.5D) * 3.6D + offZ;
        return new double[] {d1, d2};
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
