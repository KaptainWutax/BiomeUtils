package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class DeepOceanLayer extends CrossLayer {

	public DeepOceanLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public DeepOceanLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		if(!BiomeSource.isShallowOcean(center)) {
			return center;
		}

		int i = 0;
		if(BiomeSource.isShallowOcean(n))i++;
		if(BiomeSource.isShallowOcean(e))i++;
		if(BiomeSource.isShallowOcean(w))i++;
		if(BiomeSource.isShallowOcean(s))i++;

		if(i > 3) {
			if(center == Biome.WARM_OCEAN.getId())return Biome.DEEP_WARM_OCEAN.getId();
			if(center == Biome.LUKEWARM_OCEAN.getId())return Biome.DEEP_LUKEWARM_OCEAN.getId();
			if(center == Biome.OCEAN.getId())return Biome.DEEP_OCEAN.getId();
			if(center == Biome.COLD_OCEAN.getId())return Biome.DEEP_COLD_OCEAN.getId();
			if(center == Biome.FROZEN_OCEAN.getId())return Biome.DEEP_FROZEN_OCEAN.getId();
			return Biome.DEEP_OCEAN.getId();
		}

		return center;
	}

}
