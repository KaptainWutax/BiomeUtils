package kaptainwutax.biomeutils.biome.surface;

import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;

@SuppressWarnings("unused")
public class SurfaceConfig {
	public static final SurfaceConfig CONFIG_PODZOL = new SurfaceConfig(Blocks.PODZOL, Blocks.DIRT, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_GRAVEL = new SurfaceConfig(Blocks.GRAVEL, Blocks.GRAVEL, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_GRASS = new SurfaceConfig(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_STONE = new SurfaceConfig(Blocks.STONE, Blocks.STONE, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_COARSE_DIRT = new SurfaceConfig(Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_DESERT = new SurfaceConfig(Blocks.SAND, Blocks.SAND, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_OCEAN_SAND = new SurfaceConfig(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.SAND);
	public static final SurfaceConfig CONFIG_FULL_SAND = new SurfaceConfig(Blocks.SAND, Blocks.SAND, Blocks.SAND);
	public static final SurfaceConfig CONFIG_BADLANDS = new SurfaceConfig(Blocks.RED_SAND, Blocks.WHITE_TERRACOTTA, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_MYCELIUM = new SurfaceConfig(Blocks.MYCELIUM, Blocks.DIRT, Blocks.GRAVEL);
	public static final SurfaceConfig CONFIG_HELL = new SurfaceConfig(Blocks.NETHERRACK, Blocks.NETHERRACK, Blocks.NETHERRACK);
	public static final SurfaceConfig CONFIG_SOUL_SAND_VALLEY = new SurfaceConfig(Blocks.SOUL_SAND, Blocks.SOUL_SAND, Blocks.SOUL_SAND);
	public static final SurfaceConfig CONFIG_THE_END = new SurfaceConfig(Blocks.END_STONE, Blocks.END_STONE, Blocks.END_STONE);
	public static final SurfaceConfig CONFIG_CRIMSON_FOREST = new SurfaceConfig(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, Blocks.NETHER_WART_BLOCK);
	public static final SurfaceConfig CONFIG_WARPED_FOREST = new SurfaceConfig(Blocks.WARPED_NYLIUM, Blocks.NETHERRACK, Blocks.WARPED_WART_BLOCK);
	public static final SurfaceConfig CONFIG_BASALT_DELTAS = new SurfaceConfig(Blocks.BLACKSTONE, Blocks.BASALT, Blocks.MAGMA_BLOCK);
	public static final SurfaceConfig CONFIG_ICE_SPIKES = new SurfaceConfig(Blocks.SNOW_BLOCK, Blocks.DIRT, Blocks.GRAVEL);

	private final Block topBlock;
	private final Block underBlock;
	private final Block underwaterBlock;

	public SurfaceConfig(Block topBlock, Block underBlock, Block underwaterBlock) {
		this.topBlock = topBlock;
		this.underBlock = underBlock;
		this.underwaterBlock = underwaterBlock;
	}

	public Block getTopBlock() {
		return topBlock;
	}

	public Block getUnderBlock() {
		return underBlock;
	}

	public Block getUnderwaterBlock() {
		return underwaterBlock;
	}

	@Override public String toString() {
		return "SurfaceConfig{" +
			"topBlock=" + topBlock +
			", underBlock=" + underBlock +
			", underwaterBlock=" + underwaterBlock +
			'}';
	}
}
