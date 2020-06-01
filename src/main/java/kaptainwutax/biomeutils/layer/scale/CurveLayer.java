package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.XCrossLayer;

public class CurveLayer extends XCrossLayer {

	public CurveLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public CurveLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int sw, int se, int ne, int nw, int center) {
		if(!BiomeSource.isShallowOcean(center) || BiomeSource.isShallowOcean(nw) && BiomeSource.isShallowOcean(ne) && BiomeSource.isShallowOcean(sw) && BiomeSource.isShallowOcean(se)) {
			if(!BiomeSource.isShallowOcean(center) && (BiomeSource.isShallowOcean(nw) || BiomeSource.isShallowOcean(sw) || BiomeSource.isShallowOcean(ne) || BiomeSource.isShallowOcean(se)) && this.nextInt(5) == 0) {
				if(BiomeSource.isShallowOcean(nw)) {
					return center == 4 ? 4 : nw;
				}

				if(BiomeSource.isShallowOcean(sw)) {
					return center == 4 ? 4 : sw;
				}

				if(BiomeSource.isShallowOcean(ne)) {
					return center == 4 ? 4 : ne;
				}

				if(BiomeSource.isShallowOcean(se)) {
					return center == 4 ? 4 : se;
				}
			}

			return center;
		}

		int i = 1;
		int j = 1;

		if(!BiomeSource.isShallowOcean(nw) && this.nextInt(i++) == 0) {
			j = nw;
		}

		if(!BiomeSource.isShallowOcean(ne) && this.nextInt(i++) == 0) {
			j = ne;
		}

		if(!BiomeSource.isShallowOcean(sw) && this.nextInt(i++) == 0) {
			j = sw;
		}

		if(!BiomeSource.isShallowOcean(se) && this.nextInt(i++) == 0) {
			j = se;
		}

		if(this.nextInt(3) == 0) {
			return j;
		} else {
			return j == 4 ? 4 : center;
		}
	}

}
