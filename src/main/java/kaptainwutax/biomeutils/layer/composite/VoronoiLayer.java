package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.seed.SeedMixer;
import kaptainwutax.seedutils.mc.seed.WorldSeed;

public class VoronoiLayer extends BiomeLayer {

    public final long seed;
    private final boolean is3D;

    public VoronoiLayer(MCVersion version, long worldSeed, boolean is3D, BiomeLayer parent) {
        super(version, worldSeed, version.isOlderThan(MCVersion.v1_15) ? 10L : 0L, parent);
        this.seed = version.isOlderThan(MCVersion.v1_15) ? worldSeed : WorldSeed.toHash(worldSeed);
        this.is3D = is3D;
    }

    public int sample12(int x, int z) {
        x -= 2;
        z -= 2;
        int pX = x >> 2;
        int pZ = z >> 2;
        long mixedSeed = SeedMixer.mixSeed(layerSeed, x);

        return 0;
    }

    @Override
    public int sample(int x, int y, int z) {
        double[] res = new double[16];
        x -= 2;
        z -= 2;
        int pX = x >> 2;
        int pZ = z >> 2;
        int X = pX << 2;
        int Z = pZ << 2;
        double[] off_0_0 = calcOffset(layerSeed, X, Z);
        double[] off_1_0 = calcOffset(layerSeed, X + 4, Z);
        double[] off_0_1 = calcOffset(layerSeed, X, Z + 4);
        double[] off_1_1 = calcOffset(layerSeed, X + 4, Z + 4);
        for (int cell = 0; cell < 16; cell++) {
            double corner0 = calcContribution(off_0_0, cell, cell & 3);
            double corner1 = calcContribution(off_1_0, cell, cell & 3);
            double corner2 = calcContribution(off_0_1, cell, cell & 3);
            double corner3 = calcContribution(off_1_1, cell, cell & 3);
            if (corner0 < corner1 && corner0 < corner2 && corner0 < corner3) {
                res[cell] = 0;
            } else if (corner1 < corner0 && corner1 < corner2 && corner1 < corner3) {
                res[cell] = 1;
            } else if (corner2 < corner0 && corner2 < corner1 && corner2 < corner3) {
                res[cell] = 2;
            } else {
                res[cell] = 3;
            }

        }
        // res[3] is the correct one
        for (int v = 0; v < 4; v++) {
            for (int w = 0; w < 4; w++) {
                System.out.println(this.getParent().get(v+x,0,w+y));
            }
        }
        return this.getParent().get(x,0,y);
    }

    private static double calcContribution(double[] d, int major, int minor) {
        return ((double) major - d[1]) * ((double) major - d[1]) + ((double) minor - d[0]) * ((double) minor - d[0]);
    }

    private static double[] calcOffset(long seed, int x, int z) {
        long mixedSeed = SeedMixer.mixSeed(seed, x);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, x);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
        double d1 = (((double) ((int) Math.floorMod(mixedSeed >> 24, 1024L)) / 1024.0D) - 0.5D) * 3.6D;
        double d2 = (((double) ((int) Math.floorMod(mixedSeed >> 24, 1024L)) / 1024.0D) - 0.5D) * 3.6D;
        return new double[] {d1, d2};
    }

    private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
        long mixedSeed = SeedMixer.mixSeed(seed, x);
        //mixedSeed = SeedMixer.mixSeed(mixedSeed, y);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
        mixedSeed = SeedMixer.mixSeed(mixedSeed, x);
        //mixedSeed = SeedMixer.mixSeed(mixedSeed, y);
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
