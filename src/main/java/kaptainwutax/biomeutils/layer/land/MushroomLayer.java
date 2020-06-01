package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.XCrossLayer;

public class MushroomLayer extends XCrossLayer {

	public MushroomLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public MushroomLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int sw, int se, int ne, int nw, int center) {
		return BiomeSource.isShallowOcean(center) && BiomeSource.isShallowOcean(sw)
				&& BiomeSource.isShallowOcean(se) && BiomeSource.isShallowOcean(ne)
				&& BiomeSource.isShallowOcean(nw) && this.nextInt(100) == 0 ? Biome.MUSHROOM_FIELDS.getId() : center;
	}

}
