package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class EaseEdgeLayer extends CrossLayer {

    public EaseEdgeLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
        super(version, worldSeed, salt, parent);
    }

    @Override
    public int sample(int n, int e, int s, int w, int center) {
        int[] is = new int[1];
        if (!this.replaceEdgeIfNeeded(is, n, e, s, w, center, Biome.MOUNTAINS, Biome.MOUNTAIN_EDGE) &&
                this.replaceEdge(is, n, e, s, w, center, Biome.WOODED_BADLANDS_PLATEAU, Biome.BADLANDS) &&
                this.replaceEdge(is, n, e, s, w, center, Biome.BADLANDS_PLATEAU, Biome.BADLANDS) &&
                this.replaceEdge(is, n, e, s, w, center, Biome.GIANT_TREE_TAIGA, Biome.TAIGA)) {

            if (center == Biome.DESERT.getId() && anyMatch(Biome.SNOWY_TUNDRA, n, e, w, s)) {
                return Biome.WOODED_MOUNTAINS.getId();
            } else {
                if (center == Biome.SWAMP.getId()) {
                    if (anyMatch(Biome.DESERT, n, e, w, s) || anyMatch(Biome.SNOWY_TUNDRA, n, e, w, s) || anyMatch(Biome.SNOWY_TAIGA, n, e, w, s)) {
                        return Biome.PLAINS.getId();
                    }

                    if (anyMatch(Biome.JUNGLE, n, e, w, s) || anyMatch(Biome.BAMBOO_JUNGLE, n, e, w, s)) {
                        return Biome.JUNGLE_EDGE.getId();
                    }
                }

                return center;
            }
        }

        return is[0];
    }

    public static boolean anyMatch(Biome biome, int... values) {
        for (int value : values) {
            if (value == biome.getId()) return true;
        }
        return false;
    }

    private boolean replaceEdgeIfNeeded(int[] is, int n, int e, int s, int w, int center, Biome biome1, Biome biome2) {
        if (is1_16_2up.call()){
            if (!Biome.areSimilar(center,Biome.MOUNTAINS,this)){
                return false;
            }
            is[0]=center;
            return true;
        }
        if (!Biome.areSimilar(center, biome1,this)) {
            return false;
        } else {
            if (this.canBeNeighbors(n, biome1) && this.canBeNeighbors(e, biome1) && this.canBeNeighbors(w, biome1) && this.canBeNeighbors(s, biome1)) {
                is[0] = center;
            } else {
                is[0] = biome2.getId();
            }
            return true;
        }
    }

    private boolean replaceEdge(int[] is, int i, int j, int k, int l, int m, Biome n, Biome o) {
        if (m != n.getId()) return true;

        if (Biome.areSimilar(i, n,this) && Biome.areSimilar(j, n,this) && Biome.areSimilar(l, n,this) && Biome.areSimilar(k, n,this)) {
            is[0] = m;
        } else {
            is[0] = o.getId();
        }
        return false;
    }

    private boolean canBeNeighbors(int id, Biome b2) {
        if (Biome.areSimilar(id, b2,this)) return true;

        Biome biome = Biome.REGISTRY.get(id);

        if (biome != null && b2 != null) {
            Biome.Temperature t = biome.getTemperatureGroup();
            Biome.Temperature t2 = b2.getTemperatureGroup();
            return t == t2 || t == Biome.Temperature.MEDIUM || t2 == Biome.Temperature.MEDIUM;
        }

        return false;
    }

}

