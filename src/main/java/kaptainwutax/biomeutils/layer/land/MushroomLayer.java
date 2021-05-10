package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.XCrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class MushroomLayer extends XCrossLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	public MushroomLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int sw, int se, int ne, int nw, int center) {
		return Biome.applyAll(v -> Biome.isShallowOcean(v, this), center, sw, se, ne, nw) &&
				this.nextInt(100) == 0 ? Biomes.MUSHROOM_FIELDS.getId() : center;
	}
}
