package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.util.data.Pair;
import kaptainwutax.mcutils.util.data.Quad;
import kaptainwutax.noiseutils.simplex.OctaveSimplexNoiseSampler;
import kaptainwutax.seedutils.rand.JRand;

import java.util.Arrays;
import java.util.Collections;

public class BadlandsSurfaceBuilder extends SurfaceBuilder {
	public BadlandsSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	public Block[] applyToColumn(BiomeSource source, ChunkRand rand, Block[] column, Biome biome, int x, int z, int maxY, int minY, double noise, int seaLevel, Block defaultBlock, Block defaultFluid) {
		Quad<Block[], OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> badlandsSurface = source.getStaticNoiseSource().getBadlandsSurface();
		Block trackedTopBlock = Blocks.WHITE_TERRACOTTA;
		SurfaceConfig config = this.getSurfaceConfig();
		Block underBlock = config.getUnderBlock();
		Block topBlock = config.getTopBlock();
		Block trackedUnderBlock = underBlock;
		int elevation = (int)(noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		boolean shouldBeSimple = Math.cos(noise / 3.0D * Math.PI) > 0.0D;
		int trackedY = -1;
		boolean isOrangeTerracotta = false;
		int topLayers = 0;

		for(int y = maxY; y >= minY; --y) {
			Block block=this.getBaseBlock(y,column,source,defaultBlock);

			if (topLayers < 15 || shouldBypass()) {
				if (Block.IS_AIR.test(source.getVersion(),block)) {
					trackedY = -1;
				} else if (block==defaultBlock) {
					if (trackedY == -1) {
						isOrangeTerracotta = false;
						if (elevation <= 0) {
							trackedTopBlock = Blocks.AIR;
							trackedUnderBlock = defaultBlock;
						} else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
							trackedTopBlock = Blocks.WHITE_TERRACOTTA;
							trackedUnderBlock = underBlock;
						}

						if (y < seaLevel && (trackedTopBlock == null || Block.IS_AIR.test(source.getVersion(),trackedTopBlock))) {
							trackedTopBlock = defaultFluid;
						}

						trackedY = elevation + Math.max(0, y - seaLevel);
						if (y >= seaLevel - 1) {
							if (this.highContion(y,elevation)){
								if (shouldBeSimple) {
									block = Blocks.COARSE_DIRT;
								} else {
									block =  Blocks.GRASS_BLOCK;
								}
							} else if(y <= seaLevel + 3 + elevation) {
								block=topBlock;
								isOrangeTerracotta = true;
							} else {
								if (y >= 64 && y <= 127) {
									if (shouldBeSimple) {
										block = Blocks.TERRACOTTA;
									} else {
										block = badlandsSurface.getFirst()[this.getBandY(x, y, z,badlandsSurface.getFourth())];;
									}
								} else {
									block = Blocks.ORANGE_TERRACOTTA;
								}
							}
						} else {
							block=trackedUnderBlock;
							if (this.orangeTerracottaCondition(block)) {
								block=Blocks.ORANGE_TERRACOTTA;
							}
						}
					} else if (trackedY > 0) {
						--trackedY;
						if (isOrangeTerracotta) {
							block=Blocks.ORANGE_TERRACOTTA;
						} else {
							block=badlandsSurface.getFirst()[this.getBandY(x, y, z,badlandsSurface.getFourth())];
						}
					}
					++topLayers;
				}
			}
			column[y]=block;
		}
		return column;
	}

	protected Block getBaseBlock(int y,Block[] column, BiomeSource source, Block defaultBlock){
		return column[y];
	}

	protected boolean highContion(int y,int elevation){
		return false;
	}

	protected boolean shouldBypass(){
		return false;
	}

	protected boolean orangeTerracottaCondition(Block block){
		return block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA
			|| block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA
			|| block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA
			|| block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA
			|| block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA
			|| block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA
			|| block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA
			|| block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA;
	}

	protected int getBandY(int x, int y, int z, OctaveSimplexNoiseSampler clayBandsOffsetNoise) {
		int offset = (int)Math.round(clayBandsOffsetNoise.sample((double)x / 512.0D, (double)z / 512.0D, false) * 2.0D);
		return (y + offset + 64) % 64;
	}

	public static Pair<Block[], OctaveSimplexNoiseSampler> generateBands(long worldSeed) {
		JRand rand = new JRand(worldSeed);
		Block[] clayBands = new Block[64];
		Arrays.fill(clayBands, Blocks.TERRACOTTA);
		OctaveSimplexNoiseSampler clayBandsOffsetNoise = new OctaveSimplexNoiseSampler(rand, Collections.singletonList(0));
		for(int y = 0; y < 64; ++y) {
			y += rand.nextInt(5) + 1;
			if(y < 64) {
				clayBands[y] = Blocks.ORANGE_TERRACOTTA;
			}
		}
		int attempts;

		attempts = rand.nextInt(4) + 2;

		for(int attempt = 0; attempt < attempts; ++attempt) {
			int bound = rand.nextInt(3) + 1;
			int offset = rand.nextInt(64);

			for(int y = 0; offset + y < 64 && y < bound; ++y) {
				clayBands[offset + y] = Blocks.YELLOW_TERRACOTTA;
			}
		}

		attempts = rand.nextInt(4) + 2;

		for(int attempt = 0; attempt < attempts; ++attempt) {
			int bound = rand.nextInt(3) + 2;
			int offset = rand.nextInt(64);

			for(int y = 0; offset + y < 64 && y < bound; ++y) {
				clayBands[offset + y] = Blocks.BROWN_TERRACOTTA;
			}
		}

		attempts = rand.nextInt(4) + 2;

		for(int attempt = 0; attempt < attempts; ++attempt) {
			int bound = rand.nextInt(3) + 1;
			int offset = rand.nextInt(64);

			for(int y = 0; offset + y < 64 && y < bound; ++y) {
				clayBands[offset + y] = Blocks.RED_TERRACOTTA;
			}
		}

		attempts = rand.nextInt(3) + 3;
		int offset = 0;

		for(int attempt = 0; attempt < attempts; ++attempt) {
			int bound = 1;
			offset += rand.nextInt(16) + 4;

			for(int y = 0; offset + y < 64 && y < bound; ++y) {
				clayBands[offset + y] = Blocks.WHITE_TERRACOTTA;
				if(offset + y > 1 && rand.nextBoolean()) {
					clayBands[offset + y - 1] = Blocks.LIGHT_GRAY_TERRACOTTA;
				}

				if(offset + y < 63 && rand.nextBoolean()) {
					clayBands[offset + y + 1] = Blocks.LIGHT_GRAY_TERRACOTTA;
				}
			}
		}
		return new Pair<>(clayBands, clayBandsOffsetNoise);
	}
}
