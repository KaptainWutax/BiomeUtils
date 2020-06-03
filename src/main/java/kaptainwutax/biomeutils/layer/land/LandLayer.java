package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.XCrossLayer;

public class CurveLayer extends XCrossLayer {

    public CurveLayer(long worldSeed, long salt, BiomeLayer parent) {
        super(worldSeed, salt, parent);
    }

    public CurveLayer(long worldSeed, long salt) {
        this(worldSeed, salt, null);
    }

    @Override
    public int sample(int sw, int se, int ne, int nw, int center) {
        if (!Biome.isShallowOcean(center) || Biome.applyAll(Biome::isShallowOcean, sw, se, ne, nw )) {
            if (Biome.isShallowOcean(center) || (Biome.applyAll(e -> !Biome.isShallowOcean(e), sw, se, ne, nw)) || this.nextInt(5) != 0) {
                return center;
            }
            if (Biome.isShallowOcean(nw)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), nw);
            }

            if (Biome.isShallowOcean(sw)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), sw);
            }

            if (Biome.isShallowOcean(ne)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), ne);
            }

            if (Biome.isShallowOcean(se)) {
                return Biome.equalsOrDefault(center, Biome.FOREST.getId(), se);
            }

            return center;
        }

        int i = 1;
        int j = 1;

        if (!Biome.isShallowOcean(nw) && this.nextInt(i++) == 0) {
            j = nw;
        }

        if (!Biome.isShallowOcean(ne) && this.nextInt(i++) == 0) {
            j = ne;
        }

        if (!Biome.isShallowOcean(sw) && this.nextInt(i++) == 0) {
            j = sw;
        }

        if (!Biome.isShallowOcean(se) && this.nextInt(i) == 0) {
            j = se;
        }

        if (this.nextInt(3) == 0) {
            return j;
        } else {
            return j == Biome.FOREST.getId() ? Biome.FOREST.getId() : center;
        }
    }

}
