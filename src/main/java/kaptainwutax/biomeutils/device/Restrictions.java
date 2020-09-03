package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.land.*;
import kaptainwutax.biomeutils.layer.temperature.ClimateLayer;
import kaptainwutax.biomeutils.source.BiomeSource;

import static kaptainwutax.biomeutils.device.Restriction.getSalt;

public class Restrictions {

    //4096:1
    public static final Restriction.Factory<BoundRestriction> CONTINENT = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, ContinentLayer.class), 10, 0) {
                private final int ID = getLayerId(version, ContinentLayer.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.PLAINS.getId();
                }
            };

    //1024:1
    public static final Restriction.Factory<BoundRestriction> ISLAND = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, IslandLayer.class), 2, 0) {
                private final int ID = getLayerId(version, IslandLayer.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.PLAINS.getId();
                }
            };

    //1024:1
    public static final Restriction.Factory<BoundRestriction> COLD_FOREST = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, ClimateLayer.Cold.class), 6, 0) {
                private final int ID = getLayerId(version, ClimateLayer.Cold.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.FOREST.getId();
                }
            };

    //1024:1
    public static final Restriction.Factory<BoundRestriction> COLD_MOUNTAIN = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, ClimateLayer.Cold.class), 6, 1) {
                private final int ID = getLayerId(version, ClimateLayer.Cold.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.MOUNTAINS.getId();
                }
            };

    //1024:1
    public static final Restriction.Factory<BoundRestriction> COLD_PLAINS = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, ClimateLayer.Cold.class), 6, 2, 5) {
                private final int ID = getLayerId(version, ClimateLayer.Cold.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.PLAINS.getId();
                }
            };

    //1024:1
    public static final Restriction.Factory<BoundRestriction> SPECIAL = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, ClimateLayer.Special.class), 13, 0) {
                private final int ID = getLayerId(version, ClimateLayer.Special.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) >> 8 != 0;
                }
            };

    //256:1
    public static final Restriction.Factory<BoundRestriction> MUSHROOM = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, MushroomLayer.class), 100, 0) {
                private final int ID = getLayerId(version, MushroomLayer.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.MUSHROOM_FIELDS.getId();
                }
            };

    //64:1
    public static final Restriction.Factory<BoundRestriction> SUNFLOWER_PLAINS = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, SunflowerPlainsLayer.class), 57, 0) {
                private final int ID = getLayerId(version, SunflowerPlainsLayer.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.SUNFLOWER_PLAINS.getId();
                }
            };

    //256:1
    public static final Restriction.Factory<BoundRestriction> BAMBOO_JUNGLE = (version, x, z) ->
            new BoundRestriction(x, z, getSalt(version, BambooJungleLayer.class), 10, 0) {
                private final int ID = getLayerId(version, BambooJungleLayer.class);

                @Override
                public boolean testSource(BiomeSource source) {
                    return source.getLayer(this.ID).get(this.getX(), 0, this.getZ()) == Biome.BAMBOO_JUNGLE.getId();
                }
            };

}
