package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.XCrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class LandLayer extends XCrossLayer {

    public LandLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
        super(version, worldSeed, salt, parent);
    }

    @Override
    public int sample(int sw, int se, int ne, int nw, int center) {
        if (!Biome.isShallowOcean(center, this) || Biome.applyAll(v -> Biome.isShallowOcean(v, this), sw, se, ne, nw)) {
            if (Biome.isShallowOcean(center, this)
                    || Biome.applyAll(v -> !Biome.isShallowOcean(v, this), sw, se, ne, nw)
                    || this.nextInt(5) != 0) {
                return center;
            }

            if (is1_6down.call()) {
                return center == Biome.SNOWY_TUNDRA.getId() ? Biome.FROZEN_OCEAN.getId() : Biome.OCEAN.getId();
            }

            if (Biome.isShallowOcean(nw, this)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), nw);
            }

            if (Biome.isShallowOcean(sw, this)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), sw);
            }

            if (Biome.isShallowOcean(ne, this)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), ne);
            }

            if (Biome.isShallowOcean(se, this)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), se);
            }

            return center;
        }

        int i = 1;
        int j = 1;

        if (!Biome.isShallowOcean(nw, this) && this.nextInt(i++) == 0) {
            j = nw;
        }

        if (!Biome.isShallowOcean(ne, this) && this.nextInt(i++) == 0) {
            j = ne;
        }

        if (!Biome.isShallowOcean(sw, this) && this.nextInt(i++) == 0) {
            j = sw;
        }

        if (!Biome.isShallowOcean(se, this) && this.nextInt(i) == 0) {
            j = se;
        }

        if (this.nextInt(3) == 0) {
            return j;
        }
        if (is1_6down.call()) {
            return j == Biome.SNOWY_TUNDRA.getId() ? Biome.FROZEN_OCEAN.getId() : Biome.OCEAN.getId();
        }
        return j == Biome.FOREST.getId() ? Biome.FOREST.getId() : center;
    }

}
