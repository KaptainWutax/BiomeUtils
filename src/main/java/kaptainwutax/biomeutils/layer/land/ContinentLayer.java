package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class ContinentLayer extends IntBiomeLayer {

	public ContinentLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
	}

	@Override
	public int sample(int x, int y, int z) {
		this.setSeed(x, z);
		return x == 0 && z == 0 || this.nextInt(10) == 0 ? Biomes.PLAINS.getId() : Biomes.OCEAN.getId();
	}

}
