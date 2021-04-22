package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class SmoothScaleLayer extends CrossLayer {

	public SmoothScaleLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
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
	}

}
