package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class IslandLayer extends CrossLayer {

	public IslandLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public IslandLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		return Biome.isShallowOcean(center) && Biome.isShallowOcean(n)
				&& Biome.isShallowOcean(e) && Biome.isShallowOcean(w)
				&& Biome.isShallowOcean(s) && this.nextInt(2) == 0 ? Biome.PLAINS.getId() : center;
	}

}
