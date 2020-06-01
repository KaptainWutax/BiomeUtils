package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class SmoothenShorelineLayer extends CrossLayer {

	public SmoothenShorelineLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public SmoothenShorelineLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		boolean xMatches = e == w;
		boolean zMatches = n == s;

		if(xMatches == zMatches) {
			return xMatches ? this.choose(w, n) : center;
		} else {
			return xMatches ? w : n;
		}
	}

}
