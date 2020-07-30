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
		int landStackCenter = this.getParent(0).get(x, y, z);
		int noiseStackCenter = this.getParent(1).get(x, y, z);

		if(Biome.isOcean(landStackCenter))return landStackCenter;
		
		if(noiseStackCenter == Biome.RIVER.getId()) {
			if(landStackCenter == Biome.SNOWY_TUNDRA.getId()) {
				return Biome.FROZEN_RIVER.getId();
			} else {
				return landStackCenter != Biome.MUSHROOM_FIELDS.getId() && landStackCenter != Biome.MUSHROOM_FIELD_SHORE.getId() ? noiseStackCenter & 255 : Biome.MUSHROOM_FIELD_SHORE.getId();
			}
		}
		
		return landStackCenter;
	}

}
