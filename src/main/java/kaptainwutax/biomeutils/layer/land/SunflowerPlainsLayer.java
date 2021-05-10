package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.ComputeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class SunflowerPlainsLayer extends ComputeLayer {

	public SunflowerPlainsLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int compute(int value, int x, int z) {
		this.setSeed(x, z);
		return value == Biomes.PLAINS.getId() && this.nextInt(57) == 0 ? Biomes.SUNFLOWER_PLAINS.getId() : value;
	}
}
