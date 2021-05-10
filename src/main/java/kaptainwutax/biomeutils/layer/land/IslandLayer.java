package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class IslandLayer extends CrossLayer {

	public IslandLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		return Biome.applyAll(v -> Biome.isShallowOcean(v, this), center, n, e, s, w)
				&& this.nextInt(2) == 0 ? Biomes.PLAINS.getId() : center;
	}
}
