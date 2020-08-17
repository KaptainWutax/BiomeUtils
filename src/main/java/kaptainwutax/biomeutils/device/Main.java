package kaptainwutax.biomeutils.device;

import kaptainwutax.seedutils.mc.MCVersion;

public class Main {

    public static void main(String[] args) {
        BiomeDevice device = new BiomeDevice(MCVersion.v1_16_2)
                .add(Restrictions.MUSHROOM, 0, 0)
                .add(Restrictions.MUSHROOM, 1, 0)
                .add(Restrictions.MUSHROOM, 0, 1)
                .add(Restrictions.MUSHROOM, 1, 1)
                .add(Restrictions.MUSHROOM, 2, 0)
                .add(Restrictions.MUSHROOM, 2, 1);


        device.findSeeds(System.out::println);
    }

}
