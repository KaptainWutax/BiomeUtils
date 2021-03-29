package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class ScaleLayer extends BiomeLayer {

    private final Type type;

    public ScaleLayer(MCVersion version, long worldSeed, long salt, Type type, BiomeLayer parent) {
        super(version, worldSeed, salt, parent);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public int sample(int x, int y, int z) {
        int center = this.getParent().get(x >> 1, y, z >> 1);
        this.setSeed(x & -2, z & -2);
        int xb = x & 1, zb = z & 1;

        if (xb == 0 && zb == 0) return center;

        int s = this.getParent().get(x >> 1, y, (z + 1) >> 1);
        int zPlus = this.choose(center, s);

        if (xb == 0) return zPlus;

        int e = this.getParent().get((x + 1) >> 1, y, z >> 1);
        int xPlus = this.choose(center, e);

        if (zb == 0) return xPlus;

        int se = getParent().get((x + 1) >> 1, y, (z + 1) >> 1);
        return this.sample(center, e, s, se);
    }

    public int sample(int center, int e, int s, int se) {
        int ret = this.choose(center, e, s, se);

        if (this.type == Type.FUZZY) {
            return ret;
        }

        if (e == s && e == se) return e;
        if (center == e && s != se) return center;
        if (center == s && e != se) return center;
        if (center == se && e != s) return center;
        if (e == s && center != se) return e;
        if (e == se && center != s) return e;
        if (s == se && center != e) return s;
        return ret;
    }

    // ((center == s && center == se) ||
    // (center == s && center == e) ||
    // (center == e && center == se) ||
    // (center == s && e != se) ||
    // (center == e && s != se) ||
    // (center == se && s != e))
    // with the precondition s!=se if e==s or e==se


    public enum Type {
        NORMAL, FUZZY
    }

}
