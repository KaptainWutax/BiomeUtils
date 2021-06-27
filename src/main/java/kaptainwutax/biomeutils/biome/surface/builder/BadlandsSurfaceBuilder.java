package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.util.data.Pair;
import kaptainwutax.noiseutils.simplex.OctaveSimplexNoiseSampler;
import kaptainwutax.seedutils.rand.JRand;

import java.util.Arrays;
import java.util.Collections;

public class BadlandsSurfaceBuilder extends SurfaceBuilder {
	public BadlandsSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
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
