package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.rand.ChunkRand;

public class ShatteredSavannaSurfaceBuilder extends DefaultSurfaceBuilder {
	public ShatteredSavannaSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		if(noise > 1.75D) {
			setSurfaceConfig(SurfaceConfig.CONFIG_STONE);
		} else if(noise > -0.5D) {
			setSurfaceConfig(SurfaceConfig.CONFIG_COARSE_DIRT);
		} else {
			setSurfaceConfig(SurfaceConfig.CONFIG_GRASS);
		}

		return super.applyToColumn(source, rand, column, biome, x, z, maxY, minY, noise, seaLevel, defaultBlock, defaultFluid);
	}
}
