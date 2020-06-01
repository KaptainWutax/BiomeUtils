package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class ContinentLayer extends BiomeLayer {

	public ContinentLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public ContinentLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);
		if(x == 0 && z == 0)return 1;
		return this.nextInt(10) == 0 ? Biome.PLAINS.getId() : Biome.OCEAN.getId();
	}

}
