package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.rand.ChunkRand;

public class DefaultSurfaceBuilder extends SurfaceBuilder {
	public DefaultSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		Block topBlock = this.getSurfaceConfig().getTopBlock();
		Block underBlock = this.getSurfaceConfig().getUnderBlock();
		int state = -1;
		int elevation = (int)(noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		Object[] extras=generateExtras(rand,seaLevel);

		for(int y = maxY; y >= minY; --y) {
			Block block=getBaseBlock(source,y,column,rand,seaLevel);
			if (Block.IS_AIR.test(source.getVersion(),block)) {
				state = -1;
			} else if (block==defaultBlock) {
				if (state == -1) {
					if (elevation <= 0) {
						topBlock = Blocks.AIR;
						underBlock = defaultBlock;
					} else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
						topBlock = this.getSurfaceConfig().getTopBlock();
						underBlock = this.getSurfaceConfig().getUnderBlock();
					}

					if (y < seaLevel && (topBlock == null || Block.IS_AIR.test(source.getVersion(),topBlock))) {
						if (biome.getTemperatureAt(x,y,z) < 0.15F) {
							topBlock = Blocks.ICE;
						} else {
							topBlock = defaultFluid;
						}
					}

					state = elevation;
					if (y >= seaLevel - 1) {
						block=topBlock;
					} else if (y < seaLevel - 7 - elevation) {
						topBlock = Blocks.AIR;
						underBlock = defaultBlock;
						block= this.getSurfaceConfig().getUnderwaterBlock();
					} else {
						block=underBlock;
					}
				} else if (state > 0) {
					--state;
					block=underBlock;
					if (state == 0 && underBlock==Blocks.SAND && elevation > 1) {
						state = rand.nextInt(4) + Math.max(0, y - 63);
						underBlock = underBlock == Blocks.RED_SAND ? Blocks.RED_SANDSTONE : Blocks.SANDSTONE;
					}
				}
			}else{
				block=applyExtraConditions(y,block,extras);
			}
			column[y]=block;
		}
		return column;
	}

	public Block applyExtraConditions(int y,Block block,Object[] extras){
		return block;
	}

	public Object[] generateExtras(ChunkRand rand,int seaLevel){
		return new Object[0];
	}

	public Block getBaseBlock(BiomeSource source, int y,Block[] column, ChunkRand rand, int seaLevel){
		return column[y];
	}
}
