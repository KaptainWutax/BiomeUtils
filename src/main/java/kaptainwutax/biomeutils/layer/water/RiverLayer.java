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
		int land_stack_center = this.getParent(0).get(x, y, z);
		int noise_stack_center = this.getParent(1).get(x, y, z);

		if(Biome.isOcean(land_stack_center))return land_stack_center;
		
		if(noise_stack_center == Biome.RIVER.getId()) {
			if(land_stack_center == Biome.SNOWY_TUNDRA.getId()) {
				return Biome.FROZEN_RIVER.getId();
			} else {
				return land_stack_center != Biome.MUSHROOM_FIELDS.getId() && land_stack_center != Biome.MUSHROOM_FIELD_SHORE.getId() ? noise_stack_center & 255 : Biome.MUSHROOM_FIELD_SHORE.getId();
			}
		}
		
		return land_stack_center;
	}

}
