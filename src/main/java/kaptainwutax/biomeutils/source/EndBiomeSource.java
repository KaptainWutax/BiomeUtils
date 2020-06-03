package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.noise.SimplexNoiseSampler;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.prng.lcg.LCG;
import kaptainwutax.seedutils.prng.lcg.java.JRand;

public class EndBiomeSource extends BiomeSource {

	public static final LCG SIMPLEX_SKIP = LCG.JAVA.combine(17292);
	public final SimplexNoiseSampler simplex;

	public EndBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed);
		JRand rand = new JRand(worldSeed);
		rand.advance(SIMPLEX_SKIP);
		this.simplex = new SimplexNoiseSampler(rand);
	}

	@Override
	public Biome getBiome(int blockX, int blockY, int blockZ) {
		int i = blockX >> 2;
		int j = blockZ >> 2;

		if ((long)i * (long)i + (long)j * (long)j <= 4096L) {
			return Biome.THE_END;
		}

		float f = getNoiseAt(this.simplex, i * 2 + 1, j * 2 + 1);
		if (f > 40.0F) {
			return Biome.END_HIGHLANDS;
		} else if (f >= 0.0F) {
			return Biome.END_MIDLANDS;
		} else {
			return f < -20.0F ? Biome.SMALL_END_ISLANDS : Biome.END_BARRENS;
		}
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return this.getBiome(x, y, z);
	}

	public static float getNoiseAt(SimplexNoiseSampler simplexNoiseSampler, int i, int j) {
		int k = i / 2;
		int l = j / 2;
		int m = i % 2;
		int n = j % 2;
		float f = 100.0F - (float)Math.sqrt((float)(i * i + j * j)) * 8.0F;
		f = clamp(f, -100.0F, 80.0F);

		for(int o = -12; o <= 12; ++o) {
			for(int p = -12; p <= 12; ++p) {
				long q = (long)(k + o);
				long r = (long)(l + p);
				if (q * q + r * r > 4096L && simplexNoiseSampler.sample((double)q, (double)r) < -0.8999999761581421D) {
					float g = (Math.abs((float)q) * 3439.0F + Math.abs((float)r) * 147.0F) % 13.0F + 9.0F;
					float h = (float)(m - o * 2);
					float s = (float)(n - p * 2);
					float t = 100.0F - (float)Math.sqrt(h * h + s * s) * g;
					t = clamp(t, -100.0F, 80.0F);
					f = Math.max(f, t);
				}
			}
		}

		return f;
	}

	public static float clamp(float value, float min, float max) {
		if(value < min)return min;
		return Math.min(value, max);
	}

}
