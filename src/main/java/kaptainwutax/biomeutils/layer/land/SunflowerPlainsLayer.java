package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class SunflowerPlainsLayer extends BiomeLayer {

	public SunflowerPlainsLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public SunflowerPlainsLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);
		int value = this.parent.get(x, z);
		return value == Biome.PLAINS.getId() && this.nextInt(57) == 0 ? Biome.SUNFLOWER_PLAINS.getId() : value;
	}

}
