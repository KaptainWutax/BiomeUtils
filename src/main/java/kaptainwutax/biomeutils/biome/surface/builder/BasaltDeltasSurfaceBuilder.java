package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.util.data.Triplet;
import kaptainwutax.noiseutils.perlin.OctavePerlinNoiseSampler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasaltDeltasSurfaceBuilder extends ValleySurfaceBuilder {
	public static final List<Block> FLOOR_BLOCK_STATES = Arrays.asList(Blocks.BASALT, Blocks.BLACKSTONE);
	public static final List<Block> CEILING_BLOCK_STATES = Collections.singletonList(Blocks.BASALT);

	public BasaltDeltasSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Triplet<List<OctavePerlinNoiseSampler>,List<OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler> getNoises(BiomeSource source) {
		return source.getStaticNoiseSource().getBasaltDeltasNoise();
	}

	@Override
	protected List<Block> getFloorBlockStates() {
		return FLOOR_BLOCK_STATES;
	}

	@Override
	protected List<Block> getCeilingBlockStates() {
		return CEILING_BLOCK_STATES;
	}

	@Override
	protected Block getPatchBlock() {
		return Blocks.GRAVEL;
	}
}
