package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;

public abstract class XCrossLayer extends BiomeLayer {

	public XCrossLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public XCrossLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);

		return this.sample(
				this.parent.get(x - 1, z + 1),
				this.parent.get(x + 1, z + 1),
				this.parent.get(x + 1, z - 1),
				this.parent.get(x - 1, z - 1),
				this.parent.get(x, z)
			);
	}

	public abstract int sample(int sw, int se, int ne, int nw, int center);

}
