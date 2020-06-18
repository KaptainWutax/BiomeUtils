package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class SmoothScaleLayer extends CrossLayer {

	public SmoothScaleLayer(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		boolean xMatches = e == w;
		boolean zMatches = n == s;

		if(xMatches && zMatches) {
			return this.choose(w, n);
		} else if(!xMatches && !zMatches) {
			return center;
		} else if(xMatches) {
			return w;
		} else {
			return n;
		}

		/*
		if(xMatches == zMatches) {
			return xMatches ? this.choose(w, n): center;
		} else {
			return xMatches ? w : n;
		}*/
	}

}
