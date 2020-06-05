package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public abstract class XCrossLayer extends BiomeLayer {

	public XCrossLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);

		return this.sample(
				this.getParent().get(x - 1, z + 1),
				this.getParent().get(x + 1, z + 1),
				this.getParent().get(x + 1, z - 1),
				this.getParent().get(x - 1, z - 1),
				this.getParent().get(x, z)
			);
	}

	public abstract int sample(int sw, int se, int ne, int nw, int center);

}
