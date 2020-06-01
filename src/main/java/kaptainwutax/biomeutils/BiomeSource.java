package kaptainwutax.biomeutils;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.land.*;
import kaptainwutax.biomeutils.layer.scale.CurveLayer;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;
import kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer;
import kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer;
import kaptainwutax.biomeutils.layer.shore.SmoothenShorelineLayer;
import kaptainwutax.biomeutils.layer.temperature.ClimateLayer;
import kaptainwutax.biomeutils.layer.water.DeepOceanLayer;
import kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer;
import kaptainwutax.biomeutils.layer.water.OceanTemperatureLayer;
import kaptainwutax.biomeutils.layer.water.RiverLayer;

import java.util.function.BiFunction;

public class BiomeSource {

	public BiomeLayer base;
	public BiomeLayer ocean;
	public BiomeLayer noise;
	public BiomeLayer hills;
	public BiomeLayer full;
	public VoronoiLayer voronoi;

	public final long worldSeed;
	public final int biomeSize;
	public final int riverSize;

	public BiomeSource(long worldSeed, int biomeSize, int riverSize) {
		this.worldSeed = worldSeed;
		this.biomeSize = biomeSize;
		this.riverSize = riverSize;
	}

	public BiomeSource init() {
		BiFunction<Long, BiomeLayer, BiomeLayer> NORMAL_SCALE = (s, p) -> new ScaleLayer(this.worldSeed, s, ScaleLayer.Type.NORMAL, p);

		this.base = new ContinentLayer(this.worldSeed, 1L);
		this.base = new ScaleLayer(this.worldSeed, 2000L, ScaleLayer.Type.FUZZY, this.base);
		this.base = new CurveLayer(this.worldSeed, 1L, this.base);
		this.base = new ScaleLayer(this.worldSeed, 2001L, ScaleLayer.Type.NORMAL, this.base);
		this.base = new CurveLayer(this.worldSeed, 2L, this.base);
		this.base = new CurveLayer(this.worldSeed, 50L, this.base);
		this.base = new CurveLayer(this.worldSeed, 70L, this.base);
		this.base = new IslandLayer(this.worldSeed, 2L, this.base);
		this.base = new ClimateLayer.Cold(this.worldSeed, 2L, this.base);
		this.base = new CurveLayer(this.worldSeed, 3L, this.base);
		this.base = new ClimateLayer.Temperate(this.worldSeed, 2L, this.base);
		this.base = new ClimateLayer.Cool(this.worldSeed, 2L, this.base);
		this.base = new ClimateLayer.Special(this.worldSeed, 3L, this.base);
		this.base = new ScaleLayer(this.worldSeed, 2002L, ScaleLayer.Type.NORMAL, this.base);
		this.base = new ScaleLayer(this.worldSeed, 2003L, ScaleLayer.Type.NORMAL, this.base);
		this.base = new CurveLayer(this.worldSeed, 4L, this.base);
		this.base = new MushroomLayer(this.worldSeed, 5L, this.base);
		this.base = new DeepOceanLayer(this.worldSeed, 4L, this.base);
		this.base = stack(1000L, NORMAL_SCALE, this.base, 0);

		this.noise = stack(1000L, NORMAL_SCALE, this.base, 0);
		this.noise = new SimpleLandNoiseLayer(this.worldSeed, 100L, this.base);

		this.full = new BaseBiomesLayer(this.worldSeed, 200L, this.base);
		this.full = new BambooJungleLayer(this.worldSeed, 1001L, this.full);
		this.full = stack(1000L, NORMAL_SCALE, this.full, 2);
		this.full = new EaseEdgeLayer(this.worldSeed, 1000L, this.full);

		this.hills = stack(1000L, NORMAL_SCALE, this.noise, 2);
		this.full = new HillsLayer(this.worldSeed, 1000L, this.full, this.hills);

		this.noise = stack(1000L, NORMAL_SCALE, this.noise, 2);
		this.noise = stack(1000L, NORMAL_SCALE, this.noise, this.riverSize);
		this.noise = new NoiseToRiverLayer(this.worldSeed, 1L, this.noise);
		this.noise = new SmoothenShorelineLayer(this.worldSeed, 1000L, this.noise);

		this.full = new SunflowerPlains(this.worldSeed, 1001L, this.full);

		for(int i = 0; i < this.biomeSize; i++) {
			this.full = new ScaleLayer(this.worldSeed, 1000L + i, ScaleLayer.Type.NORMAL, this.full);
			if(i == 0)this.full = new CurveLayer(this.worldSeed, 3L, this.full);

			if(i == 1 || this.biomeSize == 1) {
				this.full = new EdgeBiomesLayer(this.worldSeed, 1000L, this.full);
			}
		}

		this.full = new SmoothenShorelineLayer(this.worldSeed, 1000L, this.full);
		this.full = new RiverLayer(this.worldSeed, 100L, this.full, this.noise);

		this.ocean = new OceanTemperatureLayer(this.worldSeed, 2L);
		this.ocean = stack(2001L, NORMAL_SCALE, this.ocean, 6);

		this.full = new OceanTemperatureLayer.Apply(this.worldSeed, 100L, this.full, this.ocean);
		this.voronoi = new VoronoiLayer(this.worldSeed, true, this.full);
		return this;
	}

	public static BiomeLayer stack(long salt, BiFunction<Long, BiomeLayer, BiomeLayer> layer, BiomeLayer parent, int count) {
		for(int i = 0; i < count; i++) {
			parent = layer.apply(salt + i, parent);
		}

		return parent;
	}

	public static boolean isShallowOcean(int id) {
		return id == Biome.WARM_OCEAN.getId() || id == Biome.LUKEWARM_OCEAN.getId() || id == Biome.OCEAN.getId()
				|| id == Biome.COLD_OCEAN.getId() || id == Biome.FROZEN_OCEAN.getId();
	}

	public static boolean isOcean(int id) {
		return id == Biome.WARM_OCEAN.getId() || id == Biome.LUKEWARM_OCEAN.getId() || id == Biome.OCEAN.getId()
				|| id == Biome.COLD_OCEAN.getId() || id == Biome.FROZEN_OCEAN.getId() || id == Biome.DEEP_WARM_OCEAN.getId()
				|| id == Biome.DEEP_LUKEWARM_OCEAN.getId() || id == Biome.DEEP_OCEAN.getId()
				|| id == Biome.DEEP_COLD_OCEAN.getId() || id == Biome.DEEP_FROZEN_OCEAN.getId();
	}

	public static boolean areSimilar(int id, Biome b2) {
		if(b2 == null)return false;
		if(id == b2.getId())return true;

		Biome b = Biome.REGISTRY.get(id);
		if(b == null)return false;

		if(id != Biome.WOODED_BADLANDS_PLATEAU.getId() && id != Biome.BADLANDS_PLATEAU.getId()) {
			if(b.getCategory() != Biome.Category.NONE && b2.getCategory() != Biome.Category.NONE
					&& b.getCategory() == b2.getCategory()) {
				return true;
			}

			return b == b2;
		}

		return b2 == Biome.WOODED_BADLANDS_PLATEAU || b2 == Biome.BADLANDS_PLATEAU;
	}

}
