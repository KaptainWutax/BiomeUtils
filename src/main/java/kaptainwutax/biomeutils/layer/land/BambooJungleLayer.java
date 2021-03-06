package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class BambooJungleLayer extends IntBiomeLayer {

	public BambooJungleLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		int value = this.getParent(IntBiomeLayer.class).get(x, y, z);
		return value == Biomes.JUNGLE.getId() && this.nextInt(10) == 0 ? Biomes.BAMBOO_JUNGLE.getId() : value;
	}

}
