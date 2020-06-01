package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class SunflowerPlains extends BiomeLayer {

	public SunflowerPlains(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public SunflowerPlains(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);
		int value = this.parent.get(x, z);
		return this.nextInt(57) == 0 && value == Biome.PLAINS.getId() ? Biome.SUNFLOWER_PLAINS.getId() : value;
	}

}
