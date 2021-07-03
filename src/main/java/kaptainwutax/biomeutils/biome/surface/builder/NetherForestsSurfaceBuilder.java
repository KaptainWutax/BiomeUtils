package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.rand.ChunkRand;

public class NetherForestsSurfaceBuilder extends SurfaceBuilder {
	public NetherForestsSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {

		double seaHeight = source.getStaticNoiseSource().getNetherForestsNoise().sample((double)x * 0.1D, seaLevel, (double)z * 0.1D);
		boolean shouldExpose = seaHeight > 0.15D + rand.nextDouble() * 0.35D;
		double topHeight = source.getStaticNoiseSource().getNetherForestsNoise().sample((double)x * 0.1D, 109.0D, (double)z * 0.1D);
		boolean shouldUnderwater = topHeight > 0.25D + rand.nextDouble() * 0.9D;
		int elevation = (int)(noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int state = -1;
		Block underBlock = this.getSurfaceConfig().getUnderBlock();

		for(int y = Math.max(127, maxY); y >= minY; --y) {
			Block topBlock = this.getSurfaceConfig().getTopBlock();
			Block block = column[y];
			if(Block.IS_AIR.test(source.getVersion(), block)) {
				state = -1;
			} else if(block == defaultBlock) {
				if(state == -1) {
					boolean flag2 = false;
					if(elevation <= 0) {
						flag2 = true;
						underBlock = this.getSurfaceConfig().getUnderBlock();
					}

					if(shouldExpose) {
						topBlock = this.getSurfaceConfig().getUnderBlock();
					} else if(shouldUnderwater) {
						topBlock = this.getSurfaceConfig().getUnderwaterBlock();
					}

					if(y < seaLevel && flag2) {
						topBlock = defaultFluid;
					}

					state = elevation;
					if(y >= seaLevel - 1) {
						block = topBlock;
					} else {
						block = underBlock;
					}
				} else if(state > 0) {
					--state;
					block = underBlock;
				}
			}
			column[y] = block;
		}
		return column;
	}
}
