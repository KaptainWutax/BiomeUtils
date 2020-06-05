package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class EdgeBiomesLayer extends CrossLayer {

    public EdgeBiomesLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
        super(version, worldSeed, salt, parent);
    }

    @Override
    public int sample(int n, int e, int s, int w, int center) {
        Biome biome = Biome.REGISTRY.get(center);

        if (center == Biome.MUSHROOM_FIELDS.getId()) {
            if (Biome.applyAll((v) -> !Biome.isShallowOcean(v), n, e, s, w)) {
                return center;
            }
            return Biome.MUSHROOM_FIELD_SHORE.getId();
        } else if (biome != null && biome.getCategory() == Biome.Category.JUNGLE) {
            if (!(Biome.applyAll(EdgeBiomesLayer::isWooded, n, e, s, w))) {
                return Biome.JUNGLE_EDGE.getId();
            }
            if (Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w)) {
                return center;
            }
            return Biome.BEACH.getId();
        } else if (center != Biome.MOUNTAINS.getId() && center != Biome.WOODED_MOUNTAINS.getId() && center != Biome.MOUNTAIN_EDGE.getId()) {
            if (biome != null && biome.getPrecipitation() == Biome.Precipitation.SNOW) {
                if (!Biome.isOcean(center) && !(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w))) {
                    return Biome.SNOWY_BEACH.getId();
                }
            } else if (center != Biome.BADLANDS.getId() && center != Biome.WOODED_BADLANDS_PLATEAU.getId()) {
                if (!Biome.isOcean(center) && center != Biome.RIVER.getId() && center != Biome.SWAMP.getId() && !(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w))) {
                    return Biome.BEACH.getId();
                }
            } else if (Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w) && !(Biome.applyAll(EdgeBiomesLayer::isBadlands, n, e, s, w))) {
                return Biome.DESERT.getId();
            }
        } else if (!Biome.isOcean(center) && !(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w))) {
            return Biome.STONE_SHORE.getId();
        }

        return center;
    }

    private static boolean isWooded(int id) {
        Biome b = Biome.REGISTRY.get(id);
        if (b != null && b.getCategory() == Biome.Category.JUNGLE) return true;
        return id == Biome.JUNGLE_EDGE.getId() || id == Biome.JUNGLE.getId() || id == Biome.JUNGLE_HILLS.getId() || id == Biome.FOREST.getId() || id == Biome.TAIGA.getId() || Biome.isOcean(id);
    }

    private static boolean isBadlands(int id) {
        return id == Biome.BADLANDS.getId() || id == Biome.WOODED_BADLANDS_PLATEAU.getId() || id == Biome.BADLANDS_PLATEAU.getId() || id == Biome.ERODED_BADLANDS.getId() || id == Biome.MODIFIED_WOODED_BADLANDS_PLATEAU.getId() || id == Biome.MODIFIED_BADLANDS_PLATEAU.getId();
    }

}
