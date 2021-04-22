package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class NoiseLayer extends IntBiomeLayer {

	public NoiseLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int i = this.getParent(IntBiomeLayer.class).get(x, y, z);
		return Biome.isShallowOcean(i, this) ? i : this.nextInt(is1_6down.call() ? 2 : 299999) + 2;
	}

}
