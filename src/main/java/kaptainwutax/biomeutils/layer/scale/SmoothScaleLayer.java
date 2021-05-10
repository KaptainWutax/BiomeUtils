package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class SmoothScaleLayer extends CrossLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	public SmoothScaleLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		boolean xMatches = e == w;
		boolean zMatches = n == s;

		if (xMatches && zMatches) {
			return this.choose(w, n);
		} else if (!xMatches && !zMatches) {
			return center;
		} else if (xMatches) {
			return w;
		} else {
			return n;
		}
	}
}
