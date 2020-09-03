package kaptainwutax.biomeutils.device;

import kaptainwutax.seedutils.mc.MCVersion;

public class Main {

    public static void main(String[] args) {
        BiomeDevice device = new BiomeDevice(MCVersion.v1_16_2);

        for(int x = 0; x < 4; x++) {
            for(int z = 0; z < 3; z++) {
                device.add(Restrictions.COLD_FOREST, x, z);
            }
        }

        device.findSeeds(System.out::println);
    }

}
