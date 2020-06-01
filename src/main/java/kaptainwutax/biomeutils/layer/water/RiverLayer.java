package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.MergingLayer;

public class RiverLayer extends MergingLayer {

	public RiverLayer(long worldSeed, long salt, BiomeLayer... parents) {
		super(worldSeed, salt, parents);
	}

	public RiverLayer(long worldSeed, long salt) {
		this(worldSeed, salt, (BiomeLayer[])null);
	}

	@Override
	public int sample(int x, int z) {
		int i = this.parents[0].sample(x, z);
		int j = this.parents[1].sample(x, z);

		if(BiomeSource.isOcean(i))return i;
		
		if(j == Biome.RIVER.getId()) {
			if(i == Biome.SNOWY_TUNDRA.getId()) {
				return Biome.FROZEN_RIVER.getId();
			} else {
				return i != Biome.MUSHROOM_FIELDS.getId() && i != Biome.MUSHROOM_FIELD_SHORE.getId()
						? j & 255 : Biome.MUSHROOM_FIELD_SHORE.getId();
			}
		}
		
		return i;
	}

}
