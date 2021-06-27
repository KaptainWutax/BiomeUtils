package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasaltDeltasSurfaceBuilder extends SurfaceBuilder {
	public static final List<Block> FLOOR_BLOCK_STATES = Arrays.asList(Blocks.BASALT, Blocks.BLACKSTONE);
	public static final List<Block> CEILING_BLOCK_STATES = Collections.singletonList(Blocks.BASALT);

	public BasaltDeltasSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}
}
