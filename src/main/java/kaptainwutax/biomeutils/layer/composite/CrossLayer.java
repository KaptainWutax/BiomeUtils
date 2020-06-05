package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public abstract class CrossLayer extends BiomeLayer {

	public CrossLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);

		return this.sample(
				this.getParent().get(x, z - 1),
				this.getParent().get(x + 1, z),
				this.getParent().get(x, z + 1),
				this.getParent().get(x - 1, z),
				this.getParent().get(x, z)
			);
	}

	public abstract int sample(int n, int e, int s, int w, int center);

}
