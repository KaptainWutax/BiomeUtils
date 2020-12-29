package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.temperature.ClimateLayer;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.Random;

import static kaptainwutax.biomeutils.device.Restriction.getSalt;

public class Main {

    //1024:1
    public static final Restriction.Factory<Restriction> CLIMATE_PLAINS = (version, x, z) -> {
        return new Restriction("CLIMATE_PLAINS", x, z) {
            private final int ID = getLayerId(version, ClimateLayer.Temperate.class);

            @Override
            public boolean testSeed(long seed, long bits) {
                return true;
            }

            @Override
            public boolean testSource(BiomeSource source) {
                return (source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) & ~0xF00) == Biome.PLAINS.getId();
            }
        };
    };

    public static final Restriction.Factory<Restriction> CLIMATE_MOUNTAIN = (version, x, z) -> {
        return new Restriction("CLIMATE_MOUNTAIN", x, z) {
            private final int ID = getLayerId(version, ClimateLayer.Temperate.class);

            @Override
            public boolean testSeed(long seed, long bits) {
                return true;
            }

            @Override
            public boolean testSource(BiomeSource source) {
                return (source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) & ~0xF00) == Biome.MOUNTAINS.getId();
            }
        };
    };


    //1024:1
    public static final Restriction.Factory<Restriction> CLIMATE_DESERT = (version, x, z) -> {
        return new Restriction("CLIMATE_DESERT", x, z) {
            private final int ID = getLayerId(version, ClimateLayer.Temperate.class);

            @Override
            public boolean testSeed(long seed, long bits) {
                return true;
            }

            @Override
            public boolean testSource(BiomeSource source) {
                return (source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) & ~0xF00) == Biome.DESERT.getId();
            }
        };
    };

    //1024:1
    public static final Restriction.Factory<Restriction> NO_PLAINS = (version, x, z) -> {
        return new Restriction("NO_PLAINS", x, z) {
            private final int ID = getLayerId(version, ClimateLayer.Cold.class);

            @Override
            public boolean testSeed(long seed, long bits) {
                return true;
            }

            @Override
            public boolean testSource(BiomeSource source) {
                return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) != Biome.PLAINS.getId();
            }
        };
    };

    public static final int[][] MAP = new int[][] {
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 2, 2},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 2, 0, 2},
            {1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 2, 2}
    };

    public static final int[][] MAP_2 = new int[][] {
            {2, 1, 1, 2, 1, 1, 2},
            {1, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 1},
            {2, 1, 2, 2, 2, 1, 2},
            {2, 2, 1, 2, 1, 2, 2},
            {2, 2, 2, 1, 2, 2, 2}
    };

    public static void main3(String[] args) {
        BiomeDevice device = new BiomeDevice(MCVersion.v1_16_2);

        for(int i = 0; i < MAP_2.length; i++) {
            for(int j = 0; j < MAP_2[i].length; j++) {
                int v = MAP_2[i][j];
                if(v == 2)continue;

                if(v == 1) {
                    device.add(Restrictions.CONTINENT, j - 3, i - 1);
                } else {
                   // device.add(NO_PLAINS, j, i);
                }
            }
        }

        device.findSeeds(System.out::println);
    }

    public static void main2(String[] args) {
        BiomeDevice device = new BiomeDevice(MCVersion.v1_16_2);

        /*
        device.add(Restrictions.COLD_PLAINS, 0, 0);
        device.add(Restrictions.COLD_PLAINS, -1, -1);
        device.add(Restrictions.COLD_PLAINS, 1, -1);
        device.add(Restrictions.COLD_PLAINS, -1, 1);
        device.add(Restrictions.COLD_PLAINS, 1, 1);

        device.add(Restrictions.SPECIAL, 0, 0);
        device.add(Restrictions.SPECIAL, -1, -1);
        device.add(Restrictions.SPECIAL, 1, -1);
        device.add(Restrictions.SPECIAL, -1, 1);
        device.add(Restrictions.SPECIAL, 1, 1);

        device.add(CLIMATE_DESERT, 0, 0);
        device.add(CLIMATE_DESERT, -1, -1);
        device.add(CLIMATE_DESERT, 1, -1);
        device.add(CLIMATE_DESERT, -1, 1);
        device.add(CLIMATE_DESERT, 1, 1);*/

        for(int x = 0; x < 3; x++) {
            for(int z = 0; z < 2; z++) {
                device.add(Restrictions.MUSHROOM, x, z);
            }
        }

        device.findSeeds(System.out::println);
    }

    public static void main(String[] args) {
        BiomeDevice device = new BiomeDevice(MCVersion.v1_16_2);
        device.add(Restrictions.MUSHROOM, 0, 0);

        device.add(Restrictions.BAMBOO_JUNGLE, 1, 0);
        device.add(Restrictions.BAMBOO_JUNGLE, 0, -1);
        device.findSeeds(System.out::println);
    }

}