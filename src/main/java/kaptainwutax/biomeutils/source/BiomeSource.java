package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.BPos;

import java.util.*;
import java.util.function.Predicate;

public abstract class BiomeSource {

    private final MCVersion version;
    private final long worldSeed;

    public BiomeSource(MCVersion version, long worldSeed) {
        this.version = version;
        this.worldSeed = worldSeed;
    }

    public static Factory factory(Dimension dimension) {
        if (dimension == Dimension.OVERWORLD) return OverworldBiomeSource::new;
        else if (dimension == Dimension.NETHER) return NetherBiomeSource::new;
        else if (dimension == Dimension.END) return EndBiomeSource::new;
        return null;
    }

    public static BiomeSource of(Dimension dimension, MCVersion version, long worldSeed) {
        Factory factory = factory(dimension);
        return factory == null ? null : factory.create(version, worldSeed);
    }

    public MCVersion getVersion() {
        return this.version;
    }

    public long getWorldSeed() {
        return this.worldSeed;
    }

    public abstract LayerStack<BiomeLayer> getLayers();

    public BiomeLayer getLayer(int index) {
        return this.getLayers().get(index);
    }

    public int getLayerCount() {
        return this.getLayers().size();
    }

    public abstract Dimension getDimension();

    public abstract Biome getBiome(int x, int y, int z);

    public abstract Biome getBiomeForNoiseGen(int x, int y, int z);

    public boolean iterateUniqueBiomes(int x, int y, int z, int radius, Predicate<Biome> shouldContinue) {
        int i = x - radius >> 2;
        int j = y - radius >> 2;
        int k = z - radius >> 2;
        int l = x + radius >> 2;
        int m = y + radius >> 2;
        int n = z + radius >> 2;
        int o = l - i + 1;
        int p = m - j + 1;
        int q = n - k + 1;

        Set<Integer> set = new HashSet<>();

        for (int r = 0; r < q; ++r) {
            for (int s = 0; s < o; ++s) {
                for (int t = 0; t < p; ++t) {
                    int u = i + s;
                    int v = j + t;
                    int w = k + r;

                    Biome b = this.getBiomeForNoiseGen(u, v, w);

                    if (!set.contains(b.getId())) {
                        if (!shouldContinue.test(b)) return false;
                    }

                    set.add(b.getId());
                }
            }
        }

        return true;
    }

    public boolean iterateUniqueBiomes(int x, int z, int radius, Predicate<Biome> shouldContinue) {
        int i = x - radius >> 2;
        int k = z - radius >> 2;
        int l = x + radius >> 2;
        int n = z + radius >> 2;
        int o = l - i + 1;
        int q = n - k + 1;

        Set<Integer> set = new HashSet<>();

        for (int r = 0; r < q; ++r) {
            for (int s = 0; s < o; ++s) {
                int u = i + s;
                int w = k + r;

                Biome b = this.getBiomeForNoiseGen(u, 0, w);

                if (!set.contains(b.getId())) {
                    if (!shouldContinue.test(b)) return false;
                }

                set.add(b.getId());
            }
        }

        return true;
    }

    public BPos locateNearestBiome(int centerX, int centerY, int centerZ, int radius, Collection<Biome> biomes, JRand rand) {
        return this.locateBiome(centerX, centerY, centerZ, radius, 1, biomes, rand, true);
    }

    public BPos locateBiome(int centerX, int centerY, int centerZ, int radius, Collection<Biome> biomes, JRand rand) {
        return this.locateBiome(centerX, centerY, centerZ, radius, 1, biomes, rand, false);
    }

    public BPos locateBiome(int centerX, int centerY, int centerZ, int radius, int increment,
                            Collection<Biome> biomes, JRand rand, boolean checkByLayer) {
        //Since we're looking at the layer before the voronoi zoom...
        centerX >>= 2;
        centerZ >>= 2;
        centerY >>= 2;
        radius >>= 2;

        BPos pos = null;
        int s = checkByLayer ? 0 : radius;
        int bound = 0;

        for (int depth = s; depth <= radius; depth += increment) {
            for (int oz = -depth; oz <= depth; oz += increment) {
                boolean isZEdge = Math.abs(oz) == depth;

                for (int ox = -depth; ox <= depth; ox += increment) {
                    if (checkByLayer) {
                        boolean isXEdge = Math.abs(ox) == depth;
                        if (!isXEdge && !isZEdge) continue;
                    }

                    int x = centerX + ox;
                    int z = centerZ + oz;

                    if (biomes.contains(this.getBiomeForNoiseGen(x, centerY, z))) {
                        if (pos == null || rand.nextInt(bound + 1) == 0) {
                            pos = new BPos(x << 2, centerY, z << 2);
                            if (checkByLayer) {
                                return pos;
                            }
                        }

                        ++bound;
                    }
                }
            }
        }

        return pos;
    }

    public BPos locateBiome12(int posX, int posZ, int radius, Collection<Biome> biomes, JRand rand) {
        int lowerX = posX - radius >> 2;
        int lowerZ = posZ - radius >> 2;
        int upperX = posX + radius >> 2;
        int upperZ = posZ + radius >> 2;
        int counter = 0;
        BPos pos = null;

        for (int z = lowerZ; z <= upperZ; z++) {
            for (int x = lowerX; x <= upperX; x++) {
                Biome biome = this.getBiomeForNoiseGen(x, 0, z);
                if (biomes.contains(biome) && (pos == null || rand.nextInt(counter + 1) == 0)) {
                    pos = new BPos(x << 2, 0, z << 2);
                    ++counter;
                }
            }
        }
        return pos;
    }

    @FunctionalInterface
    public interface Factory {
        BiomeSource create(MCVersion version, long worldSeed);
    }

}
