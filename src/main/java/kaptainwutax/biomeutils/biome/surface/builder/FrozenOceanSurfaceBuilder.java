package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.util.data.Pair;
import kaptainwutax.noiseutils.simplex.OctaveSimplexNoiseSampler;

public class FrozenOceanSurfaceBuilder extends DefaultSurfaceBuilder {
	public FrozenOceanSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	private double d0;
	private double d1;

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		d0 = 0.0D;
		d1 = 0.0D;
		float f = biome.getTemperatureAt(x, 63, z);
		Pair<OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> noises = source.getStaticNoiseSource().getFrozenOceanSurface();
		double icebergHeight = Math.min(Math.abs(noise), noises.getFirst().sample((double)x * 0.1D, (double)z * 0.1D, false) * 15.0D);
		if(icebergHeight > 1.8D) {
			double icebergCeiling = Math.abs(noises.getSecond().sample((double)x * 0.09765625D, (double)z * 0.09765625D, false));
			d0 = icebergHeight * icebergHeight * 1.2D;
			double d5 = Math.ceil(icebergCeiling * 40.0D) + 14.0D;
			if(d0 > d5) {
				d0 = d5;
			}

			if(f > 0.1F) {
				d0 -= 2.0D;
			}

			if(d0 > 2.0D) {
				d1 = (double)seaLevel - d0 - 7.0D;
				d0 = d0 + (double)seaLevel;
			} else {
				d0 = 0.0D;
			}
		}
		maxY = Math.max(maxY, (int)d0 + 1);
		return super.applyToColumn(source,rand,column,biome,x,z,maxY,minY,noise,seaLevel,defaultBlock,defaultFluid);
	}

	@Override
	public Block applyExtraConditions(int y,Block block, Object[] extras) {
		if(block == Blocks.PACKED_ICE && (int)extras[0] <= (int)extras[1] && y > (int)extras[2]) {
			block = Blocks.SNOW_BLOCK;
			extras[0] = (int)extras[0] + 1;
		}
		return block;
	}

	@Override
	public Object[] generateExtras(ChunkRand rand, int seaLevel) {
		int snowLayerCount = 0;
		int snowLayerMax = 2 + rand.nextInt(4);
		int minHeightSnow = seaLevel + 18 + rand.nextInt(10);
		return new Object[] {snowLayerCount, snowLayerMax, minHeightSnow};
	}

	@Override
	public Block getBaseBlock(BiomeSource source, int y, Block[] column, ChunkRand rand, int seaLevel) {
		Block block = column[y];
		if(Block.IS_AIR.test(source.getVersion(), block) && y < (int)d0 && rand.nextDouble() > 0.01D) {
			block = Blocks.PACKED_ICE;
		} else if(block == Blocks.WATER && y > (int)d1 && y < seaLevel && d1 != 0.0D && rand.nextDouble() > 0.15D) {
			block = Blocks.PACKED_ICE;
		}
		return block;
	}
}
