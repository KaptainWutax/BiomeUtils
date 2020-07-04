package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class IslandLayer extends CrossLayer {

	public IslandLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		return Biome.applyAll(Biome::isShallowOcean, center, n, e, s, w)
				&& this.nextInt(2) == 0 ? Biome.PLAINS.getId() : center;
	}

}
