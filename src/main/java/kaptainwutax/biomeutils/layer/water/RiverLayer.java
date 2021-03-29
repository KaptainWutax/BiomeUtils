package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class RiverLayer extends BiomeLayer {

    public RiverLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
        super(version, worldSeed, salt, parents);
    }

    @Override
    public int sample(int x, int y, int z) {
        int landStackCenter = this.getParent(0).get(x, y, z);
        int riverStackCenter = this.getParent(1).get(x, y, z);

        if (is1_6down.call()) {
            // Warning this is the only case like so because isOcean have Frozen ocean which is bypassed everywhere except here
            // thus we make this weird case, sorry about the confusion.
            if (landStackCenter == Biome.OCEAN.getId()) return landStackCenter;
        } else if (Biome.isOcean(landStackCenter)) return landStackCenter;

        if (riverStackCenter == Biome.RIVER.getId()) {
            if (landStackCenter == Biome.SNOWY_TUNDRA.getId()) {
                return Biome.FROZEN_RIVER.getId();
            } else {
                return landStackCenter != Biome.MUSHROOM_FIELDS.getId() && landStackCenter != Biome.MUSHROOM_FIELD_SHORE.getId() ? riverStackCenter & 255 : Biome.MUSHROOM_FIELD_SHORE.getId();
            }
        }

        return landStackCenter;
    }

}
