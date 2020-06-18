package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.noise.SimplexNoiseSampler;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.MCVersion;

public class EndBiomeSource extends BiomeSource {

	public static final LCG SIMPLEX_SKIP = LCG.JAVA.combine(17292);
	public final SimplexNoiseSampler simplex;

	public EndBiomeSource.Layer full;
	public VoronoiLayer voronoi;

	public EndBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed);
		JRand rand = new JRand(worldSeed);
		rand.advance(SIMPLEX_SKIP);
		this.simplex = new SimplexNoiseSampler(rand);
		this.build();
	}

	@Override
	protected void build() {
		this.full = new EndBiomeSource.Layer(this.getVersion());
		this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), this.full);
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biome.REGISTRY.get(this.voronoi.get(x >> 2, z >> 2));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biome.REGISTRY.get(this.full.get(x >> 4, z >> 4));
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
				long q = k + o;
				long r = l + p;
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

	public class Layer extends BiomeLayer {
		public Layer(MCVersion version) {
			super(version, -1, -1, (BiomeLayer)null);
		}

		@Override
		public int sample(int x, int z) {
			System.out.println(x + ", " + z);
			System.out.println((long)x * (long)x + (long)z * (long)z);
			if ((long)x * (long)x + (long)z * (long)z <= 4096L) {
				return Biome.THE_END.getId();
			}

			float f = getNoiseAt(EndBiomeSource.this.simplex, x * 2 + 1, z * 2 + 1);
			if (f > 40.0F) {
				return Biome.END_HIGHLANDS.getId();
			} else if (f >= 0.0F) {
				return Biome.END_MIDLANDS.getId();
			} else {
				return f < -20.0F ? Biome.SMALL_END_ISLANDS.getId() : Biome.END_BARRENS.getId();
			}
		}
	}

}
