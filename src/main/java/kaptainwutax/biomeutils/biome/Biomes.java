package kaptainwutax.biomeutils.biome;

import kaptainwutax.biomeutils.biome.surface.ConfiguredSurfaceBuilders;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Biomes {

	public final static Map<Integer, Biome> REGISTRY = new HashMap<>();
	public static final Biome OCEAN = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 0, "ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.000F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome PLAINS = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 1, "plains",
		Biome.Category.PLAINS, Biome.Precipitation.RAIN, 0.8F, 0.050F, 0.125F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DESERT = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 2, "desert",
		Biome.Category.DESERT, Biome.Precipitation.NONE, 2.0F, 0.050F, 0.125F, null, ConfiguredSurfaceBuilders.DESERT));
	public static final Biome MOUNTAINS = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 3, "mountains",
		Biome.Category.EXTREME_HILLS, Biome.Precipitation.RAIN, 0.2F, 0.500F, 1.000F, null, ConfiguredSurfaceBuilders.MOUNTAIN));
	public static final Biome FOREST = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 4, "forest",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.7F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome TAIGA = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 5, "taiga",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.25F, 0.200F, 0.200F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SWAMP = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 6, "swamp",
		Biome.Category.SWAMP, Biome.Precipitation.RAIN, 0.8F, 0.100F, -0.200F, null, ConfiguredSurfaceBuilders.SWAMP));
	public static final Biome RIVER = register(new Biome(MCVersion.vb1_8_1, Dimension.OVERWORLD, 7, "river",
		Biome.Category.RIVER, Biome.Precipitation.RAIN, 0.5F, 0.000F, -0.500F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome NETHER_WASTES = register(new Biome(MCVersion.vb1_8_1, Dimension.NETHER, 8, "nether_wastes",
		Biome.Category.NETHER, Biome.Precipitation.NONE, 2.0F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.NETHER));
	public static final Biome THE_END = register(new Biome(MCVersion.vb1_8_1, Dimension.END, 9, "the_end",
		Biome.Category.THE_END, Biome.Precipitation.NONE, 0.5F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.END));
	public static final Biome FROZEN_OCEAN = register(new Biome(MCVersion.v1_0, Dimension.OVERWORLD, 10, "frozen_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.SNOW, 0.0F, 0.100F, -1.000F, null, ConfiguredSurfaceBuilders.FROZEN_OCEAN));
	public static final Biome FROZEN_RIVER = register(new Biome(MCVersion.v1_0, Dimension.OVERWORLD, 11, "frozen_river",
		Biome.Category.RIVER, Biome.Precipitation.SNOW, 0.0F, 0.000F, -0.500F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SNOWY_TUNDRA = register(new Biome(MCVersion.v1_0, Dimension.OVERWORLD, 12, "snowy_tundra",
		Biome.Category.ICY, Biome.Precipitation.SNOW, 0.0F, 0.050F, 0.125F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SNOWY_MOUNTAINS = register(new Biome(MCVersion.v1_0, Dimension.OVERWORLD, 13, "snowy_mountains",
		Biome.Category.ICY, Biome.Precipitation.SNOW, 0.0F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome MUSHROOM_FIELDS = register(new Biome(MCVersion.v1_0, Dimension.OVERWORLD, 14, "mushroom_fields",
		Biome.Category.MUSHROOM, Biome.Precipitation.RAIN, 0.9F, 0.300F, 0.200F, null, ConfiguredSurfaceBuilders.MYCELIUM));
	public static final Biome MUSHROOM_FIELD_SHORE = register(new Biome(MCVersion.v1_0, Dimension.OVERWORLD, 15, "mushroom_field_shore",
		Biome.Category.MUSHROOM, Biome.Precipitation.RAIN, 0.9F, 0.025F, 0.000F, null, ConfiguredSurfaceBuilders.MYCELIUM));
	public static final Biome BEACH = register(new Biome(MCVersion.v1_1, Dimension.OVERWORLD, 16, "beach",
		Biome.Category.BEACH, Biome.Precipitation.RAIN, 0.8F, 0.025F, 0.000F, null, ConfiguredSurfaceBuilders.DESERT));
	public static final Biome DESERT_HILLS = register(new Biome(MCVersion.v1_1, Dimension.OVERWORLD, 17, "desert_hills",
		Biome.Category.DESERT, Biome.Precipitation.NONE, 2.0F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.DESERT));
	public static final Biome WOODED_HILLS = register(new Biome(MCVersion.v1_1, Dimension.OVERWORLD, 18, "wooded_hills",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.7F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome TAIGA_HILLS = register(new Biome(MCVersion.v1_1, Dimension.OVERWORLD, 19, "taiga_hills",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.25F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome MOUNTAIN_EDGE = register(new Biome(MCVersion.v1_1, Dimension.OVERWORLD, 20, "mountain_edge",
		Biome.Category.EXTREME_HILLS, Biome.Precipitation.RAIN, 0.2F, 0.300F, 0.800F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome JUNGLE = register(new Biome(MCVersion.v1_2_1, Dimension.OVERWORLD, 21, "jungle",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome JUNGLE_HILLS = register(new Biome(MCVersion.v1_2_1, Dimension.OVERWORLD, 22, "jungle_hills",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome JUNGLE_EDGE = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 23, "jungle_edge",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DEEP_OCEAN = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 24, "deep_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.800F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome STONE_SHORE = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 25, "stone_shore",
		Biome.Category.NONE, Biome.Precipitation.RAIN, 0.2F, 0.800F, 0.100F, null, ConfiguredSurfaceBuilders.STONE));
	public static final Biome SNOWY_BEACH = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 26, "snowy_beach",
		Biome.Category.BEACH, Biome.Precipitation.SNOW, 0.05F, 0.025F, 0.000F, null, ConfiguredSurfaceBuilders.DESERT));
	public static final Biome BIRCH_FOREST = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 27, "birch_forest",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.6F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome BIRCH_FOREST_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 28, "birch_forest_hills",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.6F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DARK_FOREST = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 29, "dark_forest",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.7F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SNOWY_TAIGA = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 30, "snowy_taiga",
		Biome.Category.TAIGA, Biome.Precipitation.SNOW, -0.5F, 0.200F, 0.200F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SNOWY_TAIGA_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 31, "snowy_taiga_hills",
		Biome.Category.TAIGA, Biome.Precipitation.SNOW, -0.5F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome GIANT_TREE_TAIGA = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 32, "giant_tree_taiga",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.3F, 0.200F, 0.200F, null, ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA));
	public static final Biome GIANT_TREE_TAIGA_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 33, "giant_tree_taiga_hills",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.3F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA));
	public static final Biome WOODED_MOUNTAINS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 34, "wooded_mountains",
		Biome.Category.EXTREME_HILLS, Biome.Precipitation.RAIN, 0.2F, 0.500F, 1.000F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SAVANNA = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 35, "savanna",
		Biome.Category.SAVANNA, Biome.Precipitation.NONE, 1.2F, 0.050F, 0.125F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SAVANNA_PLATEAU = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 36, "savanna_plateau",
		Biome.Category.SAVANNA, Biome.Precipitation.NONE, 1.0F, 0.025F, 1.500F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome BADLANDS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 37, "badlands",
		Biome.Category.MESA, Biome.Precipitation.NONE, 2.0F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.BADLANDS));
	public static final Biome WOODED_BADLANDS_PLATEAU = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 38, "wooded_badlands_plateau",
		Biome.Category.MESA, Biome.Precipitation.NONE, 2.0F, 0.025F, 1.500F, null, ConfiguredSurfaceBuilders.WOODED_BADLANDS));
	public static final Biome BADLANDS_PLATEAU = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 39, "badlands_plateau",
		Biome.Category.MESA, Biome.Precipitation.NONE, 2.0F, 0.025F, 1.500F, null, ConfiguredSurfaceBuilders.BADLANDS));
	public static final Biome SMALL_END_ISLANDS = register(new Biome(MCVersion.v1_13, Dimension.END, 40, "small_end_islands",
		Biome.Category.THE_END, Biome.Precipitation.NONE, 0.5F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.END));
	public static final Biome END_MIDLANDS = register(new Biome(MCVersion.v1_13, Dimension.END, 41, "end_midlands",
		Biome.Category.THE_END, Biome.Precipitation.NONE, 0.5F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.END));
	public static final Biome END_HIGHLANDS = register(new Biome(MCVersion.v1_13, Dimension.END, 42, "end_highlands",
		Biome.Category.THE_END, Biome.Precipitation.NONE, 0.5F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.END));
	public static final Biome END_BARRENS = register(new Biome(MCVersion.v1_13, Dimension.END, 43, "end_barrens",
		Biome.Category.THE_END, Biome.Precipitation.NONE, 0.5F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.END));
	public static final Biome WARM_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 44, "warm_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.000F, null, ConfiguredSurfaceBuilders.FULL_SAND));
	public static final Biome LUKEWARM_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 45, "lukewarm_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.000F, null, ConfiguredSurfaceBuilders.OCEAN_SAND));
	public static final Biome COLD_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 46, "cold_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.000F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DEEP_WARM_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 47, "deep_warm_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.800F, null, ConfiguredSurfaceBuilders.FULL_SAND));
	public static final Biome DEEP_LUKEWARM_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 48, "deep_lukewarm_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.800F, null, ConfiguredSurfaceBuilders.OCEAN_SAND));
	public static final Biome DEEP_COLD_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 49, "deep_cold_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.800F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DEEP_FROZEN_OCEAN = register(new Biome(MCVersion.v1_13, Dimension.OVERWORLD, 50, "deep_frozen_ocean",
		Biome.Category.OCEAN, Biome.Precipitation.RAIN, 0.5F, 0.100F, -1.800F, null, ConfiguredSurfaceBuilders.FROZEN_OCEAN));
	public static final Biome THE_VOID = register(new Biome(MCVersion.v1_8, null, 127, "the_void",
		Biome.Category.NONE, Biome.Precipitation.NONE, 0.5F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.NOPE));
	public static final Biome SUNFLOWER_PLAINS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 129, "sunflower_plains",
		Biome.Category.PLAINS, Biome.Precipitation.RAIN, 0.8F, 0.050F, 0.125F, Biomes.PLAINS, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DESERT_LAKES = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 130, "desert_lakes",
		Biome.Category.DESERT, Biome.Precipitation.NONE, 2.0F, 0.250F, 0.225F, Biomes.DESERT, ConfiguredSurfaceBuilders.DESERT));
	public static final Biome GRAVELLY_MOUNTAINS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 131, "gravelly_mountains",
		Biome.Category.EXTREME_HILLS, Biome.Precipitation.RAIN, 0.2F, 0.500F, 1.000F, Biomes.MOUNTAINS, ConfiguredSurfaceBuilders.GRAVELLY_MOUNTAIN));
	public static final Biome FLOWER_FOREST = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 132, "flower_forest",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.7F, 0.400F, 0.100F, Biomes.FOREST, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome TAIGA_MOUNTAINS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 133, "taiga_mountains",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.25F, 0.400F, 0.300F, Biomes.TAIGA, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SWAMP_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 134, "swamp_hills",
		Biome.Category.SWAMP, Biome.Precipitation.RAIN, 0.8F, 0.300F, -0.100F, Biomes.SWAMP, ConfiguredSurfaceBuilders.SWAMP));
	public static final Biome ICE_SPIKES = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 140, "ice_spikes",
		Biome.Category.ICY, Biome.Precipitation.SNOW, 0.0F, 0.450F, 0.425F, Biomes.SNOWY_TUNDRA, ConfiguredSurfaceBuilders.ICE_SPIKES));
	public static final Biome MODIFIED_JUNGLE = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 149, "modified_jungle",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.400F, 0.200F, Biomes.JUNGLE, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome MODIFIED_JUNGLE_EDGE = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 151, "modified_jungle_edge",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.400F, 0.200F, Biomes.JUNGLE_EDGE, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome TALL_BIRCH_FOREST = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 155, "tall_birch_forest",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.6F, 0.400F, 0.200F, Biomes.BIRCH_FOREST, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome TALL_BIRCH_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 156, "tall_birch_hills",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.6F, 0.500F, 0.550F, Biomes.BIRCH_FOREST_HILLS, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome DARK_FOREST_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 157, "dark_forest_hills",
		Biome.Category.FOREST, Biome.Precipitation.RAIN, 0.7F, 0.400F, 0.200F, Biomes.DARK_FOREST, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SNOWY_TAIGA_MOUNTAINS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 158, "snowy_taiga_mountains",
		Biome.Category.TAIGA, Biome.Precipitation.SNOW, -0.5F, 0.400F, 0.300F, Biomes.SNOWY_TAIGA, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome GIANT_SPRUCE_TAIGA = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 160, "giant_spruce_taiga",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.25F, 0.200F, 0.200F, Biomes.GIANT_TREE_TAIGA, ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA));
	public static final Biome GIANT_SPRUCE_TAIGA_HILLS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 161, "giant_spruce_taiga_hills",
		Biome.Category.TAIGA, Biome.Precipitation.RAIN, 0.25F, 0.200F, 0.200F, Biomes.GIANT_TREE_TAIGA_HILLS, ConfiguredSurfaceBuilders.GIANT_TREE_TAIGA));
	public static final Biome MODIFIED_GRAVELLY_MOUNTAINS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 162, "modified_gravelly_mountains",
		Biome.Category.EXTREME_HILLS, Biome.Precipitation.RAIN, 0.2F, 0.500F, 1.000F, Biomes.WOODED_MOUNTAINS, ConfiguredSurfaceBuilders.GRAVELLY_MOUNTAIN));
	public static final Biome SHATTERED_SAVANNA = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 163, "shattered_savanna",
		Biome.Category.SAVANNA, Biome.Precipitation.NONE, 1.1F, 1.225F, 0.3625F, Biomes.SAVANNA, ConfiguredSurfaceBuilders.SHATTERED_SAVANNA));
	public static final Biome SHATTERED_SAVANNA_PLATEAU = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 164, "shattered_savanna_plateau",
		Biome.Category.SAVANNA, Biome.Precipitation.NONE, 1.0F, 1.212F, 1.050F, Biomes.SAVANNA_PLATEAU, ConfiguredSurfaceBuilders.SHATTERED_SAVANNA));
	public static final Biome ERODED_BADLANDS = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 165, "eroded_badlands",
		Biome.Category.MESA, Biome.Precipitation.NONE, 2.0F, 0.200F, 0.100F, Biomes.BADLANDS, ConfiguredSurfaceBuilders.ERODED_BADLANDS));
	public static final Biome MODIFIED_WOODED_BADLANDS_PLATEAU = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 166, "modified_wooded_badlands_plateau",
		Biome.Category.MESA, Biome.Precipitation.NONE, 2.0F, 0.300F, 0.450F, Biomes.WOODED_BADLANDS_PLATEAU, ConfiguredSurfaceBuilders.WOODED_BADLANDS));
	public static final Biome MODIFIED_BADLANDS_PLATEAU = register(new Biome(MCVersion.v1_7_2, Dimension.OVERWORLD, 167, "modified_badlands_plateau",
		Biome.Category.MESA, Biome.Precipitation.NONE, 2.0F, 0.300F, 0.450F, Biomes.BADLANDS_PLATEAU, ConfiguredSurfaceBuilders.BADLANDS));
	public static final Biome BAMBOO_JUNGLE = register(new Biome(MCVersion.v1_14, Dimension.OVERWORLD, 168, "bamboo_jungle",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.200F, 0.100F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome BAMBOO_JUNGLE_HILLS = register(new Biome(MCVersion.v1_14, Dimension.OVERWORLD, 169, "bamboo_jungle_hills",
		Biome.Category.JUNGLE, Biome.Precipitation.RAIN, 0.95F, 0.300F, 0.450F, null, ConfiguredSurfaceBuilders.GRASS));
	public static final Biome SOUL_SAND_VALLEY = register(new Biome(MCVersion.v1_16, Dimension.NETHER, 170, "soul_sand_valley",
		Biome.Category.NETHER, Biome.Precipitation.NONE, 2.0F, 0.2F, 0.1F, null, ConfiguredSurfaceBuilders.SOUL_SAND_VALLEY));
	public static final Biome CRIMSON_FOREST = register(new Biome(MCVersion.v1_16, Dimension.NETHER, 171, "crimson_forest",
		Biome.Category.NETHER, Biome.Precipitation.NONE, 2.0F, 0.2F, 0.1F, null, ConfiguredSurfaceBuilders.CRIMSON_FOREST));
	public static final Biome WARPED_FOREST = register(new Biome(MCVersion.v1_16, Dimension.NETHER, 172, "warped_forest",
		Biome.Category.NETHER, Biome.Precipitation.NONE, 2.0F, 0.2F, 0.1F, null, ConfiguredSurfaceBuilders.WARPED_FOREST));
	public static final Biome BASALT_DELTAS = register(new Biome(MCVersion.v1_16, Dimension.NETHER, 173, "basalt_deltas",
		Biome.Category.NETHER, Biome.Precipitation.NONE, 2.0F, 0.2F, 0.1F, null, ConfiguredSurfaceBuilders.BASALT_DELTAS));

	public static Biome register(Biome biome) {
		REGISTRY.put(biome.getId(), biome);
		return biome;
	}

}
