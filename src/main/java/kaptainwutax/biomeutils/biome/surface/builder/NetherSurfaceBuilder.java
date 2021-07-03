package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.rand.ChunkRand;

public class NetherSurfaceBuilder extends SurfaceBuilder {
	public NetherSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		boolean shouldExpose = source.getStaticNoiseSource().getNetherForestsNoise().sample((double)x * 0.03125D, (double)z * 0.03125D, 0.0D) * 75.0D + rand.nextDouble() > 0.0D;
		boolean shouldUnderwater = source.getStaticNoiseSource().getNetherForestsNoise().sample((double)x * 0.03125D, 109.0D, (double)z * 0.03125D) * 75.0D + rand.nextDouble() > 0.0D;
		int elevation = (int)(noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int state = -1;
		Block topBlock = this.getSurfaceConfig().getTopBlock();
		Block underBlock = this.getSurfaceConfig().getUnderBlock();

		for(int y = Math.max(127, maxY); y >= minY; --y) {
			Block block = column[y];
			if(Block.IS_AIR.test(source.getVersion(), block)) {
				state = -1;
			} else if(block == defaultBlock) {
				if(state == -1) {
					boolean isBelow = false;
					if(elevation <= 0) {
						isBelow = true;
						underBlock = this.getSurfaceConfig().getUnderBlock();
					} else if(y >= seaLevel - 4 && y <= seaLevel + 1) {
						topBlock = this.getSurfaceConfig().getTopBlock();
						underBlock = this.getSurfaceConfig().getUnderBlock();
						if(shouldUnderwater) {
							topBlock = Blocks.GRAVEL;
							underBlock = this.getSurfaceConfig().getUnderwaterBlock();
						}

						if(shouldExpose) {
							topBlock = Blocks.SOUL_SAND;
							underBlock = Blocks.SOUL_SAND;
						}
					}

					if(y < seaLevel && isBelow) {
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
