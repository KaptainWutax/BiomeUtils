package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class RiverLayer extends BiomeLayer {

	public RiverLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
	}

	@Override
	public int sample(int x, int y, int z) {
		int i = this.getParent(0).get(x, y, z);
		int j = this.getParent(1).get(x, y, z);

		if(Biome.isOcean(i))return i;
		
		if(j == Biome.RIVER.getId()) {
			if(i == Biome.SNOWY_TUNDRA.getId()) {
				return Biome.FROZEN_RIVER.getId();
			} else {
				return i != Biome.MUSHROOM_FIELDS.getId() && i != Biome.MUSHROOM_FIELD_SHORE.getId() ? j & 255 : Biome.MUSHROOM_FIELD_SHORE.getId();
			}
		}
		
		return i;
	}

}
