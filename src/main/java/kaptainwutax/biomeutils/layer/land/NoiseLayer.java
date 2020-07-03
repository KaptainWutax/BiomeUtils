package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class NoiseLayer extends BiomeLayer {

	public NoiseLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int i = this.getParent().get(x, y, z);
		return Biome.isShallowOcean(i) ? i : this.nextInt(299999) + 2;
	}

}
