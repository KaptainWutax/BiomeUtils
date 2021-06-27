package kaptainwutax.biomeutils.biome.surface;

import kaptainwutax.biomeutils.biome.surface.builder.BadlandsSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.BasaltDeltasSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.DefaultSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.ErodedBadlandsSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.FrozenOceanSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.GiantTreeTaigaSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.GravellyMountainSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.MountainSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.NetherForestsSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.NetherSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.NoopSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.ShatteredSavannaSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.SoulSandValleySurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.SurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.SwampSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.WoodedBadlandsSurfaceBuilder;

import java.util.HashMap;
import java.util.function.Function;

public class SurfaceBuilders {
	public static final HashMap<String, Function<SurfaceConfig, SurfaceBuilder>> SURFACE_BUILDERS = new HashMap<>();
	public static final Function<SurfaceConfig, SurfaceBuilder> DEFAULT = register("default", DefaultSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> MOUNTAIN = register("mountain", MountainSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> SHATTERED_SAVANNA = register("shattered_savanna", ShatteredSavannaSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> GRAVELLY_MOUNTAIN = register("gravelly_mountain", GravellyMountainSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> GIANT_TREE_TAIGA = register("giant_tree_taiga", GiantTreeTaigaSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> SWAMP = register("swamp", SwampSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> BADLANDS = register("badlands", BadlandsSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> WOODED_BADLANDS = register("wooded_badlands", WoodedBadlandsSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> ERODED_BADLANDS = register("eroded_badlands", ErodedBadlandsSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> FROZEN_OCEAN = register("frozen_ocean", FrozenOceanSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> NETHER = register("nether", NetherSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> NETHER_FOREST = register("nether_forest", NetherForestsSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> SOUL_SAND_VALLEY = register("soul_sand_valley", SoulSandValleySurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> BASALT_DELTAS = register("basalt_deltas", BasaltDeltasSurfaceBuilder::new);
	public static final Function<SurfaceConfig, SurfaceBuilder> NOPE = register("nope", NoopSurfaceBuilder::new);

	public static Function<SurfaceConfig, SurfaceBuilder> register(String name, Function<SurfaceConfig, SurfaceBuilder> surfaceBuilder) {
		SURFACE_BUILDERS.put(name, surfaceBuilder);
		return surfaceBuilder;
	}


}
