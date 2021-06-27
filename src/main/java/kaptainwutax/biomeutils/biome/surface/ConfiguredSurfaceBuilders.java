package kaptainwutax.biomeutils.biome.surface;

import kaptainwutax.biomeutils.biome.surface.builder.SurfaceBuilder;

import java.util.HashMap;

/**
 * Registry of commonly used surface builders to avoid recreating them for each biomes
 */
public class ConfiguredSurfaceBuilders {
	public static final HashMap<String, SurfaceBuilder> CONFIGURED_SURFACE_BUILDERS = new HashMap<>();
	public static final SurfaceBuilder BADLANDS = register("badlands", SurfaceBuilders.BADLANDS.apply(SurfaceConfig.CONFIG_BADLANDS));
	public static final SurfaceBuilder BASALT_DELTAS = register("basalt_deltas", SurfaceBuilders.BASALT_DELTAS.apply(SurfaceConfig.CONFIG_BASALT_DELTAS));
	public static final SurfaceBuilder CRIMSON_FOREST = register("crimson_forest", SurfaceBuilders.NETHER_FOREST.apply(SurfaceConfig.CONFIG_CRIMSON_FOREST));
	public static final SurfaceBuilder DESERT = register("desert", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_DESERT));
	public static final SurfaceBuilder END = register("end", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_THE_END));
	public static final SurfaceBuilder ERODED_BADLANDS = register("eroded_badlands", SurfaceBuilders.ERODED_BADLANDS.apply(SurfaceConfig.CONFIG_BADLANDS));
	public static final SurfaceBuilder FROZEN_OCEAN = register("frozen_ocean", SurfaceBuilders.FROZEN_OCEAN.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder FULL_SAND = register("full_sand", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_FULL_SAND));
	public static final SurfaceBuilder GIANT_TREE_TAIGA = register("giant_tree_taiga", SurfaceBuilders.GIANT_TREE_TAIGA.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder GRASS = register("grass", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder GRAVELLY_MOUNTAIN = register("gravelly_mountain", SurfaceBuilders.GRAVELLY_MOUNTAIN.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder ICE_SPIKES = register("ice_spikes", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_ICE_SPIKES));
	public static final SurfaceBuilder MOUNTAIN = register("mountain", SurfaceBuilders.MOUNTAIN.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder MYCELIUM = register("mycelium", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_MYCELIUM));
	public static final SurfaceBuilder NETHER = register("nether", SurfaceBuilders.NETHER.apply(SurfaceConfig.CONFIG_HELL));
	public static final SurfaceBuilder NOPE = register("nope", SurfaceBuilders.NOPE.apply(SurfaceConfig.CONFIG_STONE));
	public static final SurfaceBuilder OCEAN_SAND = register("ocean_sand", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_OCEAN_SAND));
	public static final SurfaceBuilder SHATTERED_SAVANNA = register("shattered_savanna", SurfaceBuilders.SHATTERED_SAVANNA.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder SOUL_SAND_VALLEY = register("soul_sand_valley", SurfaceBuilders.SOUL_SAND_VALLEY.apply(SurfaceConfig.CONFIG_SOUL_SAND_VALLEY));
	public static final SurfaceBuilder STONE = register("stone", SurfaceBuilders.DEFAULT.apply(SurfaceConfig.CONFIG_STONE));
	public static final SurfaceBuilder SWAMP = register("swamp", SurfaceBuilders.SWAMP.apply(SurfaceConfig.CONFIG_GRASS));
	public static final SurfaceBuilder WARPED_FOREST = register("warped_forest", SurfaceBuilders.NETHER_FOREST.apply(SurfaceConfig.CONFIG_WARPED_FOREST));
	public static final SurfaceBuilder WOODED_BADLANDS = register("wooded_badlands", SurfaceBuilders.WOODED_BADLANDS.apply(SurfaceConfig.CONFIG_BADLANDS));

	public static SurfaceBuilder register(String name, SurfaceBuilder surfaceBuilder) {
		CONFIGURED_SURFACE_BUILDERS.put(name, surfaceBuilder);
		return surfaceBuilder;
	}
}
