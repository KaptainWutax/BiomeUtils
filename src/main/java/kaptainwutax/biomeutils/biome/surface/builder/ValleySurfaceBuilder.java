package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.util.data.Triplet;
import kaptainwutax.noiseutils.perlin.OctavePerlinNoiseSampler;

import java.util.List;

public abstract class ValleySurfaceBuilder extends SurfaceBuilder {
	public ValleySurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {

		int upwardElevation = (int)(noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int downwardElevation = (int)(noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		Triplet<List<OctavePerlinNoiseSampler>, List<OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler> noises = this.getNoises(source);
		boolean flag = noises.getThird().sample((double)x * 0.03125D, 109.0D, (double)z * 0.03125D) * 75.0D + rand.nextDouble() > 0.0D;
		double max = Double.MIN_VALUE;
		Block ceilingBlock = null;
		for(int idx = 0; idx < noises.getFirst().size(); idx++) {
			if(noises.getFirst().get(idx).sample(x, seaLevel, z) > max) {
				ceilingBlock = this.getCeilingBlockStates().get(idx);
			}
		}
		assert ceilingBlock != null;
		max = Double.MIN_VALUE;
		Block floorBlock = null;
		for(int idx = 0; idx < noises.getSecond().size(); idx++) {
			if(noises.getSecond().get(idx).sample(x, seaLevel, z) > max) {
				floorBlock = this.getFloorBlockStates().get(idx);
			}
		}
		assert floorBlock != null;
		Block previousBlock = Blocks.AIR;

		for(int j1 = 127; j1 >= minY; --j1) {
			Block block = column[j1];
			Block staticBlock = block;
			if(previousBlock == defaultBlock && (Block.IS_AIR.test(source.getVersion(), staticBlock) || staticBlock == defaultFluid)) {
				for(int up = 0; up < upwardElevation; ++up) {
					if(column[Math.max(column.length, j1 + up + 1)] != defaultBlock) {
						break;
					}
					block = ceilingBlock;
				}
			}

			if((Block.IS_AIR.test(source.getVersion(), previousBlock) || previousBlock == defaultFluid) && staticBlock == defaultBlock) {
				for(int down = 0; down < downwardElevation; ++down) {
					if(column[Math.min(0, j1 - down)] != defaultBlock) {
						break;
					}
					if(flag && j1 >= seaLevel - 3 && j1 <= seaLevel + 2) {
						block = this.getPatchBlock();
					} else {
						block = floorBlock;
					}
				}
			}
			previousBlock = staticBlock;
			column[j1] = block;
		}
		return column;

	}

	public abstract Triplet<List<OctavePerlinNoiseSampler>, List<OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler> getNoises(BiomeSource source);

	protected abstract List<Block> getFloorBlockStates();

	protected abstract List<Block> getCeilingBlockStates();

	protected abstract Block getPatchBlock();
}
