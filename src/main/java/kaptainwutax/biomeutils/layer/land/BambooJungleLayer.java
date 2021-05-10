package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.ComputeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class BambooJungleLayer extends ComputeLayer {

	public BambooJungleLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	public int compute(int value, int x, int z) {
		this.setSeed(x, z);
		return value == Biomes.JUNGLE.getId() && this.nextInt(10) == 0 ? Biomes.BAMBOO_JUNGLE.getId() : value;
	}
}
