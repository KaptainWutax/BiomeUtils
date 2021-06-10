package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class EdgeBiomesLayer extends CrossLayer {

	public EdgeBiomesLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		Biome biome = Biomes.REGISTRY.get(center);

		if(center == Biomes.MUSHROOM_FIELDS.getId()) {
			if(Biome.applyAll((v) -> !Biome.isShallowOcean(v, this.getVersion()), n, e, s, w)) {
				return center;
			}

			return Biomes.MUSHROOM_FIELD_SHORE.getId();
		}

		if(this.getVersion().isOlderOrEqualTo(MCVersion.v1_0)) return center;
		if(this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_4)) return sampleOld(n, e, s, w, center);

		if(biome != null && biome.getCategory() == Biome.Category.JUNGLE) {
			if(!(Biome.applyAll(EdgeBiomesLayer::isWooded, n, e, s, w))) {
				return Biomes.JUNGLE_EDGE.getId();
			}

			if(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w)) {
				return center;
			}

			return Biomes.BEACH.getId();
		}

		if(center != Biomes.MOUNTAINS.getId() && center != Biomes.WOODED_MOUNTAINS.getId() && center != Biomes.MOUNTAIN_EDGE.getId()) {
			if(biome != null && biome.getPrecipitation() == Biome.Precipitation.SNOW) {
				if(!Biome.isOcean(center) && !(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w))) {
					return Biomes.SNOWY_BEACH.getId();
				}
			} else if(center != Biomes.BADLANDS.getId() && center != Biomes.WOODED_BADLANDS_PLATEAU.getId()) {
				if(!Biome.isOcean(center) && center != Biomes.RIVER.getId() && center != Biomes.SWAMP.getId() && !(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w))) {
					return Biomes.BEACH.getId();
				}
			} else if(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w) && !(Biome.applyAll(EdgeBiomesLayer::isBadlands, n, e, s, w))) {
				return Biomes.DESERT.getId();
			}
		} else if(!Biome.isOcean(center) && !(Biome.applyAll((v) -> !Biome.isOcean(v), n, e, s, w))) {
			return Biomes.STONE_SHORE.getId();
		}

		return center;
	}

	private static boolean isWooded(int id) {
		Biome b = Biomes.REGISTRY.get(id);
		if(b != null && b.getCategory() == Biome.Category.JUNGLE) return true;
		return id == Biomes.JUNGLE_EDGE.getId() || id == Biomes.JUNGLE.getId() || id == Biomes.JUNGLE_HILLS.getId() || id == Biomes.FOREST.getId() || id == Biomes.TAIGA.getId() || Biome.isOcean(id);
	}

	private static boolean isBadlands(int id) {
		return id == Biomes.BADLANDS.getId() || id == Biomes.WOODED_BADLANDS_PLATEAU.getId() || id == Biomes.BADLANDS_PLATEAU.getId() || id == Biomes.ERODED_BADLANDS.getId() || id == Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU.getId() || id == Biomes.MODIFIED_BADLANDS_PLATEAU.getId();
	}

	private int sampleOld(int n, int e, int s, int w, int center) {
		if(center != Biomes.OCEAN.getId() && center != Biomes.RIVER.getId() && center != Biomes.SWAMP.getId() && center != Biomes.MOUNTAINS.getId()) {
			if(Biome.applyAll((v) -> !Biome.isShallowOcean(v, this.getVersion()), n, e, s, w)) {
				return center;
			}

			return Biomes.BEACH.getId();
		}

		if(center == Biomes.MOUNTAINS.getId()) {
			if(Biome.applyAll((v) -> v == Biomes.MOUNTAINS.getId(), n, e, s, w)) {
				return center;
			}

			return Biomes.MOUNTAIN_EDGE.getId();
		}

		return center;
	}

}
