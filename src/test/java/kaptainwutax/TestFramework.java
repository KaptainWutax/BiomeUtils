package kaptainwutax;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFramework {

    public static void randomBiomeGen(int size, IntBiomeLayer layer, int[][] biomesMap) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = biomesMap[i * size + j][0];
                int z = biomesMap[i * size + j][1];
                int id = layer.sample(x, 0, z);
                assertEquals(biomesMap[i * size + j][2], id, x + " " + z + " Wrong got id " + id + " but was " + biomesMap[i * size + j][2]);
            }
        }
    }

    public static void singleBlockBiomeGen(IntBiomeLayer layer, int x, int z, int id) {
        assertEquals(id,layer.sample(x,0,z));
    }

    public static void viewDebug(IntBiomeLayer layer, int cx, int cz, int size){
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                int biome = layer.sample(cx+x-size/2,0, cz+z-size/2);
                System.out.printf("%d,",biome);
            }
            System.out.println();
        }
        System.out.println();
    }
}
