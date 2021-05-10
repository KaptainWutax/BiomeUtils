package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.XCrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class LandLayer extends XCrossLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	public LandLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int sw, int se, int ne, int nw, int center) {
		if (is_beta_1_8_1down.call()) {
			// we don't want to clutter, technically it could be merged with the current stack
			return sample_beta(sw, se, ne, nw, center);
		}
		if (!Biome.isShallowOcean(center, this) || Biome.applyAll(v -> Biome.isShallowOcean(v, this), sw, se, ne, nw)) {
			if (Biome.isShallowOcean(center, this)
					|| Biome.applyAll(v -> !Biome.isShallowOcean(v, this), sw, se, ne, nw)
					|| this.nextInt(5) != 0) {
				return center;
			}

			if (is1_6down.call()) {
				return center == Biomes.SNOWY_TUNDRA.getId() ? Biomes.FROZEN_OCEAN.getId() : Biomes.OCEAN.getId();
			}

			if (Biome.isShallowOcean(nw, this)) {
				return Biome.equalsOrDefault(center, Biomes.FOREST.getId(), nw);
			}

			if (Biome.isShallowOcean(sw, this)) {
				return Biome.equalsOrDefault(center, Biomes.FOREST.getId(), sw);
			}

			if (Biome.isShallowOcean(ne, this)) {
				return Biome.equalsOrDefault(center, Biomes.FOREST.getId(), ne);
			}

			if (Biome.isShallowOcean(se, this)) {
				return Biome.equalsOrDefault(center, Biomes.FOREST.getId(), se);
			}

			return center;
		}

		int i = 1;
		int j = 1;

		if (!Biome.isShallowOcean(nw, this) && this.nextInt(i++) == 0) {
			j = nw;
		}

		if (!Biome.isShallowOcean(ne, this) && this.nextInt(i++) == 0) {
			j = ne;
		}

		if (!Biome.isShallowOcean(sw, this) && this.nextInt(i++) == 0) {
			j = sw;
		}

		if (!Biome.isShallowOcean(se, this) && this.nextInt(i) == 0) {
			j = se;
		}

		if (this.nextInt(3) == 0) {
			return j;
		}
		if (is1_6down.call()) {
			return j == Biomes.SNOWY_TUNDRA.getId() ? Biomes.FROZEN_OCEAN.getId() : Biomes.OCEAN.getId();
		}
		return j == Biomes.FOREST.getId() ? Biomes.FOREST.getId() : center;
	}

	public int sample_beta(int sw, int se, int ne, int nw, int center) {
		if (!Biome.isShallowOcean(center, this) || Biome.applyAll(v -> Biome.isShallowOcean(v, this), sw, se, ne, nw)) {
			if (center != Biomes.PLAINS.getId() || Biome.applyAll(v -> v == Biomes.PLAINS.getId(), sw, se, ne, nw)) {
				return center;
			} else {
				// 1-nextInt(5)/4 => nextInt(5)==4 gives 0 the rest is 1
				return nextInt(5) == 4 ? Biomes.OCEAN.getId() : Biomes.PLAINS.getId();
			}
		}
		// 0+nextInt(3)/2 => nextInt(3)==2 gives 1 the rest is 0
		return nextInt(3) == 2 ? Biomes.PLAINS.getId() : Biomes.OCEAN.getId();
	}
}
