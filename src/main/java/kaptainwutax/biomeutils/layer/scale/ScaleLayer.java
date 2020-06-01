package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.layer.BiomeLayer;

public class ScaleLayer extends BiomeLayer {

	private final Type type;

	public ScaleLayer(long worldSeed, long salt, Type type, BiomeLayer parent) {
		super(worldSeed, salt, parent);
		this.type = type;
	}

	public ScaleLayer(long worldSeed, long salt, Type type) {
		this(worldSeed, salt, type, null);
	}

	@Override
	public int sample(int x, int z) {
		int i = this.parent.get(x >> 1, z >> 1);
		this.setSeed(x & -2, z & -2);
		int xb = x & 1, zb = z & 1;

		if(xb == 0 && zb == 0)return i;

		int l = this.parent.get(x >> 1, (z + 1) >> 1);
		int m = this.choose(i, l);

		if(xb == 0)return m;

		int n = this.parent.get((x + 1) >> 1, (z) >> 1);
		int o = this.choose(i, n);

		if(zb == 0)return o;

		int p = parent.get((x + 1) >> 1, (z + 1) >> 1);
		return this.sample(i, n, l, p);
	}

	protected int sample(int i, int j, int k, int l) {
		if(this.type == Type.FUZZY) {
			return this.choose(i, j, k, l);
		}

		if(j == k && k == l) {
			return j;
		} else if(i == j && i == k) {
			return i;
		} else if(i == j && i == l) {
			return i;
		} else if(i == k && i == l) {
			return i;
		} else if(i == j && k != l) {
			return i;
		} else if(i == k && j != l) {
			return i;
		} else if(i == l && j != k) {
			return i;
		} else if(j == k && i != l) {
			return j;
		} else if(j == l && i != k) {
			return j;
		} else {
			return k == l && i != j ? k : this.choose(i, j, k, l);
		}
	}

	public enum Type {
		NORMAL, FUZZY
	}

}
