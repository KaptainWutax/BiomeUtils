package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class BambooJungleLayer extends BiomeLayer {

    public BambooJungleLayer(long worldSeed, long salt, BiomeLayer parent) {
        super(worldSeed, salt, parent);
    }

    public BambooJungleLayer(long worldSeed, long salt) {
        this(worldSeed, salt, null);
    }

    @Override
    public int sample(int x, int z) {
        this.setSeed(x, z);
        int value = this.parent.get(x, z);
        return value == Biome.JUNGLE.getId() && this.nextInt(10) == 0 ? Biome.BAMBOO_JUNGLE.getId() : value;
    }

}
