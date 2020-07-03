package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class SunflowerPlainsLayer extends BiomeLayer {

	public SunflowerPlainsLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int value = this.getParent().get(x, y, z);
		return value == Biome.PLAINS.getId() && this.nextInt(57) == 0 ? Biome.SUNFLOWER_PLAINS.getId() : value;
	}

}
