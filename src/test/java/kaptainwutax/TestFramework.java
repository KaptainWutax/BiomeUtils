package kaptainwutax;

import kaptainwutax.biomeutils.layer.BiomeLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFramework {

    public static void randomBiomeGen(int size, BiomeLayer layer, int[][] biomesMap) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = biomesMap[i * size + j][0];
                int z = biomesMap[i * size + j][1];
                int id = layer.sample(x, 0, z);
                assertEquals(biomesMap[i * size + j][2], id, x + " " + z + " Wrong got id " + id + " but was " + biomesMap[i * size + j][2]);
            }
        }
    }
}
