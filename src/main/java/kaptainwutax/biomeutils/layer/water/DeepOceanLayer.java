package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class DeepOceanLayer extends CrossLayer {

	public DeepOceanLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		if(!Biome.isShallowOcean(center,this)) {
			return center;
		}

		int i = 0;
		if(Biome.isShallowOcean(n,this))i++;
		if(Biome.isShallowOcean(e,this))i++;
		if(Biome.isShallowOcean(w,this))i++;
		if(Biome.isShallowOcean(s,this))i++;

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
