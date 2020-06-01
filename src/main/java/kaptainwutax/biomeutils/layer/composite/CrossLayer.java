package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;

public abstract class CrossLayer extends BiomeLayer {

	public CrossLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public CrossLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);

		return this.sample(
				this.parent.get(x, z - 1),
				this.parent.get(x + 1, z),
				this.parent.get(x, z + 1),
				this.parent.get(x - 1, z),
				this.parent.get(x, z)
		);
	}

	public abstract int sample(int n, int e, int s, int w, int center);

}
