package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;

public class SimpleLandNoiseLayer extends BiomeLayer {

	public SimpleLandNoiseLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public SimpleLandNoiseLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);
		int i = this.parent.get(x, z);
		return BiomeSource.isShallowOcean(i) ? i : this.nextInt(299999) + 2;
	}

}
