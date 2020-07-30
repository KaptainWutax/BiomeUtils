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
		return Biome.REGISTRY.get(this.voronoi.get(x >> 2, y,z >> 2));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biome.REGISTRY.get(this.full.get(x >> 4, y,z >> 4));
	}

	public static float getHeightAt(SimplexNoiseSampler simplexNoiseSampler, int x, int z) {
		int scaled_x = x / 2;
		int scaled_z = z / 2;
		int odd_x = x % 2;
		int odd_z = z % 2;
		float height = 100.0F - (float)Math.sqrt((float)(x * x + z * z)) * 8.0F;
		height = clamp(height, -100.0F, 80.0F);

		for(int rx = -12; rx <= 12; ++rx) {
			for(int rz = -12; rz <= 12; ++rz) {
				long shifted_x = scaled_x + rx;
				long shifted_z = scaled_z + rz;
				if (shifted_x * shifted_x + shifted_z * shifted_z > 4096L && simplexNoiseSampler.sample2D((double)shifted_x, (double)shifted_z) < -0.8999999761581421D) {
					float elevation = (Math.abs((float)shifted_x) * 3439.0F + Math.abs((float)shifted_z) * 147.0F) % 13.0F + 9.0F;
					float smooth_x = (float)(odd_x - rx * 2);
					float smooth_z = (float)(odd_z - rz * 2);
					float noise = 100.0F - (float)Math.sqrt(smooth_x * smooth_x + smooth_z * smooth_z) * elevation;
					noise = clamp(noise, -100.0F, 80.0F);
					height = Math.max(height, noise);
				}
			}
		}

		return height;
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
		public int sample(int x, int y, int z) {
			if ((long)x * (long)x + (long)z * (long)z <= 4096L) {
				return Biome.THE_END.getId();
			}
			float height = getHeightAt(EndBiomeSource.this.simplex, x * 2 + 1, z * 2 + 1);
			if (height > 40.0F) {
				return Biome.END_HIGHLANDS.getId();
			} else if (height >= 0.0F) {
				return Biome.END_MIDLANDS.getId();
			} else {
				return height < -20.0F ? Biome.SMALL_END_ISLANDS.getId() : Biome.END_BARRENS.getId();
			}
		}
	}

}
