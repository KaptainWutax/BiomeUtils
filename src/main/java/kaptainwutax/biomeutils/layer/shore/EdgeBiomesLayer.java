package kaptainwutax.biomeutils.layer.shore;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;

public class EdgeBiomesLayer extends CrossLayer {

	public EdgeBiomesLayer(long worldSeed, long salt, BiomeLayer parent) {
		super(worldSeed, salt, parent);
	}

	public EdgeBiomesLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	@Override
	public int sample(int n, int e, int s, int w, int center) {
		Biome biome = Biome.REGISTRY.get(center);

		if(center == Biome.MUSHROOM_FIELDS.getId()) {
			if(Biome.isShallowOcean(n) || Biome.isShallowOcean(e) || Biome.isShallowOcean(s) || Biome.isShallowOcean(w)) {
				return Biome.MUSHROOM_FIELD_SHORE.getId();
			}
		} else if(biome != null && biome.getCategory() == Biome.Category.JUNGLE) {
			if(!isWooded(n) || !isWooded(e) || !isWooded(s) || !isWooded(w)) {
				return Biome.JUNGLE_EDGE.getId();
			}

			if(Biome.isOcean(n) || Biome.isOcean(e) || Biome.isOcean(s) || Biome.isOcean(w)) {
				return Biome.BEACH.getId();
			}
		} else if (center != Biome.MOUNTAINS.getId() && center != Biome.WOODED_MOUNTAINS.getId() && center != Biome.MOUNTAIN_EDGE.getId()) {
			if(biome != null && biome.getPrecipitation() == Biome.Precipitation.SNOW) {
				if (!Biome.isOcean(center) && (Biome.isOcean(n) || Biome.isOcean(e) || Biome.isOcean(s) || Biome.isOcean(w))) {
					return Biome.SNOWY_BEACH.getId();
				}
			} else if(center != Biome.BADLANDS.getId() && center != Biome.WOODED_BADLANDS_PLATEAU.getId()) {
				if(!Biome.isOcean(center) && center != Biome.RIVER.getId() && center != Biome.SWAMP.getId() && (Biome.isOcean(n) || Biome.isOcean(e) || Biome.isOcean(s) || Biome.isOcean(w))) {
					return Biome.BEACH.getId();
				}
			} else if(!Biome.isOcean(n) && !Biome.isOcean(e) && !Biome.isOcean(s) && !Biome.isOcean(w) && (!this.isBadlands(n) || !this.isBadlands(e) || !this.isBadlands(s) || !this.isBadlands(w))) {
				return Biome.DESERT.getId();
			}
		} else if(!Biome.isOcean(center) && (Biome.isOcean(n) || Biome.isOcean(e) || Biome.isOcean(s) || Biome.isOcean(w))) {
			return Biome.STONE_SHORE.getId();
		}

		return center;
	}

	private static boolean isWooded(int id) {
		Biome b = Biome.REGISTRY.get(id);
		if(b != null && b.getCategory() == Biome.Category.JUNGLE)return true;
		return id == Biome.JUNGLE_EDGE.getId() || id == Biome.JUNGLE.getId() || id == Biome.JUNGLE_HILLS.getId()
				|| id == Biome.FOREST.getId() || id == Biome.TAIGA.getId() || Biome.isOcean(id);
	}

	private boolean isBadlands(int id) {
		return id == Biome.BADLANDS.getId() || id == Biome.WOODED_BADLANDS_PLATEAU.getId()
				|| id == Biome.BADLANDS_PLATEAU.getId() || id == Biome.ERODED_BADLANDS.getId()
				|| id == Biome.MODIFIED_WOODED_BADLANDS_PLATEAU.getId() || id == Biome.MODIFIED_BADLANDS_PLATEAU.getId();
	}

}
