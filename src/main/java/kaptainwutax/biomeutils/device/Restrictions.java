package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.land.MushroomLayer;
import kaptainwutax.biomeutils.layer.land.SunflowerPlainsLayer;
import kaptainwutax.biomeutils.source.BiomeSource;

import static kaptainwutax.biomeutils.device.Restriction.getSalt;

public class Restrictions {

    public static final Restriction.Factory<BoundRestriction> MUSHROOM = (version, x, z) -> {
        return new BoundRestriction(x, z, getSalt(version, MushroomLayer.class), 100, 0) {
            private final int ID = getLayerId(version, MushroomLayer.class);

            @Override
            public boolean testSource(BiomeSource source) {
                return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.MUSHROOM_FIELDS.getId();
            }
        };
    };

    public static final Restriction.Factory<BoundRestriction> SUNFLOWER_PLAINS = (version, x, z) -> {
        return new BoundRestriction(x, z, getSalt(version, SunflowerPlainsLayer.class), 57, 0) {
            private final int ID = getLayerId(version, SunflowerPlainsLayer.class);

            @Override
            public boolean testSource(BiomeSource source) {
                return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.SUNFLOWER_PLAINS.getId();
            }
        };
    };

}
