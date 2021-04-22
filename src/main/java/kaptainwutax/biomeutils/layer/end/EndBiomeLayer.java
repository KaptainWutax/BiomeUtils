package kaptainwutax.biomeutils.layer.end;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.FloatBiomeLayer;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class EndBiomeLayer extends IntBiomeLayer {

	public EndBiomeLayer(MCVersion version, long worldSeed, FloatBiomeLayer parent) {
		super(version, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		x >>= 2;
		z >>= 2;

		if ((long) x * (long) x + (long) z * (long) z <= 4096L) {
			return Biome.THE_END.getId();
		}

		float height = this.getParent(FloatBiomeLayer.class).get(x * 2 + 1, 0, z * 2 + 1);

		if (height > 40.0F) {
			return Biome.END_HIGHLANDS.getId();
		} else if (height >= 0.0F) {
			return Biome.END_MIDLANDS.getId();
		} else if (height >= -20.0F) {
			return Biome.END_BARRENS.getId();
		}

		return Biome.SMALL_END_ISLANDS.getId();
	}

}
