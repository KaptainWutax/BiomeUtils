package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.BiomeSource;
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
		return BiomeSource.isShallowOcean(center) && BiomeSource.isShallowOcean(n)
				&& BiomeSource.isShallowOcean(e) && BiomeSource.isShallowOcean(w)
				&& BiomeSource.isShallowOcean(s) && this.nextInt(2) == 0 ? 1 : center;
	}

}
