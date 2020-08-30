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
        int offset;
        x -= 2;
        z -= 2;
        int pX = x >> 2;
        int pZ = z >> 2;
        int X = pX << 2;
        int Z = pZ << 2;
        double[] off_0_0 = calcOffset(layerSeed, X, Z, 0, 0);
        double[] off_1_0 = calcOffset(layerSeed, X, Z, 4, 0);
        double[] off_0_1 = calcOffset(layerSeed, X, Z, 0, 4);
        double[] off_1_1 = calcOffset(layerSeed, X, Z, 4, 4);

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


        //  X -> (cell&1)
        // _________
        // | 0 | 1 |   Z cell>>1)
        // |---|---|   |
        // | 2 | 3 |  \_/
        // |___|___|

        return this.getParent().get(pX + (offset & 1), 0, pZ + (offset >> 1));
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
