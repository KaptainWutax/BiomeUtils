package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.ComputeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class NoiseLayer extends ComputeLayer {

	public NoiseLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int compute(int value, int x, int z) {
		this.setSeed(x, z);
		return Biome.isShallowOcean(value, this) ? value : this.nextInt(is1_6down.call() ? 2 : 299999) + 2;
	}

}
