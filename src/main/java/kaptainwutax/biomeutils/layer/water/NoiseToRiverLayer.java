package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;


public class NoiseToRiverLayer extends CrossLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	public NoiseToRiverLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		if (is1_6down.call()) {
			return center != 0 && Biome.applyAll(v -> center == v, w, n, e, s) ? -1 : Biomes.RIVER.getId();
		}
		int validCenter = isValidForRiver(center);
		return Biome.applyAll(v -> validCenter == isValidForRiver(v), w, n, e, s) ? -1 : Biomes.RIVER.getId();
	}

	private static int isValidForRiver(int value) {
		return value >= 2 ? 2 + (value & 1) : value;
	}
}
