package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class XCrossLayer extends IntBiomeLayer {

	public XCrossLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);

		return this.sample(
				this.getParent(IntBiomeLayer.class).get(x - 1, y, z + 1),
				this.getParent(IntBiomeLayer.class).get(x + 1, y, z + 1),
				this.getParent(IntBiomeLayer.class).get(x + 1, y, z - 1),
				this.getParent(IntBiomeLayer.class).get(x - 1, y, z - 1),
				this.getParent(IntBiomeLayer.class).get(x, y, z)
		);
	}

	public abstract int sample(int sw, int se, int ne, int nw, int center);

}
