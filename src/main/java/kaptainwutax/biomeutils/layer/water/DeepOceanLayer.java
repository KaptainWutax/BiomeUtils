package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class DeepOceanLayer extends CrossLayer {

	public DeepOceanLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		if (!Biome.isShallowOcean(center, this)) {
			return center;
		}

		int i = 0;
		if (Biome.isShallowOcean(n, this)) i++;
		if (Biome.isShallowOcean(e, this)) i++;
		if (Biome.isShallowOcean(w, this)) i++;
		if (Biome.isShallowOcean(s, this)) i++;

		if (i > 3) {
			if (center == Biomes.WARM_OCEAN.getId()) return Biomes.DEEP_WARM_OCEAN.getId();
			if (center == Biomes.LUKEWARM_OCEAN.getId()) return Biomes.DEEP_LUKEWARM_OCEAN.getId();
			if (center == Biomes.OCEAN.getId()) return Biomes.DEEP_OCEAN.getId();
			if (center == Biomes.COLD_OCEAN.getId()) return Biomes.DEEP_COLD_OCEAN.getId();
			if (center == Biomes.FROZEN_OCEAN.getId()) return Biomes.DEEP_FROZEN_OCEAN.getId();
			return Biomes.DEEP_OCEAN.getId();
		}

		return center;
	}
}
