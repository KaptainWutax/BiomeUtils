package kaptainwutax.biomeutils.layer.end;

import kaptainwutax.biomeutils.biome.Biomes;
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
			return Biomes.THE_END.getId();
		}

		float height = this.getParent(FloatBiomeLayer.class).get(x * 2 + 1, 0, z * 2 + 1);

		if (height > 40.0F) {
			return Biomes.END_HIGHLANDS.getId();
		} else if (height >= 0.0F) {
			return Biomes.END_MIDLANDS.getId();
		} else if (height >= -20.0F) {
			return Biomes.END_BARRENS.getId();
		}

		return Biomes.SMALL_END_ISLANDS.getId();
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		return new int[xSize*ySize*zSize];
	}

}
