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
    public int sample(int x, int z) {
        int i = this.getParent().get(x >> 1, z >> 1);
        this.setSeed(x & -2, z & -2);
        int xb = x & 1, zb = z & 1;

        if (xb == 0 && zb == 0)return i;

        int l = this.getParent().get(x >> 1, (z + 1) >> 1);
        int m = this.choose(i, l);

        if (xb == 0)return m;

        int n = this.getParent().get((x + 1) >> 1, z >> 1);
        int o = this.choose(i, n);

        if (zb == 0)return o;

        int p = getParent().get((x + 1) >> 1, (z + 1) >> 1);
        return this.sample(i, n, l, p);
    }

    protected int sample(int i, int j, int k, int l) {
        int ret = this.choose(i, j, k, l);
        if (this.type == Type.FUZZY) {
            return ret;
        }
        if (j == k && j == l) return j;
        if (i == j && (i == l || k != l)) return i;
        if (i == k && (i == l || j != l)) return i;
        if (i == l && j != k) return i;
        if (j == k && i != l) return j;
        if (j == l && i != k) return j;
        if (k == l && i != j) return k;
        return ret;
    }

    @Override
    public int getScale() {
        return this.scale == -1 ? this.scale = this.getParent().getScale() >> 1 : this.scale;
    }

    public enum Type {
        NORMAL, FUZZY
    }

}
