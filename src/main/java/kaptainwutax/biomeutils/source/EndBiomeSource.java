package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
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

	private LayerStack<BiomeLayer> layers = new LayerStack<>();

	public EndBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed);
		JRand rand = new JRand(worldSeed);
		rand.advance(SIMPLEX_SKIP);
		this.simplex = new SimplexNoiseSampler(rand);
		this.build();
	}

	@Override
	public LayerStack<BiomeLayer> getLayers() {
		return this.layers;
	}

	@Override
	protected void build() {
		this.layers.add(this.full = new EndBiomeSource.Layer(this.getVersion()));
		this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full));
		this.layers.setScales();
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biome.REGISTRY.get(this.voronoi.get(x >> 2, y,z >> 2));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biome.REGISTRY.get(this.full.get(x >> 4, y,z >> 4));
	}

	public static float getHeightAt(SimplexNoiseSampler simplex, int x, int z) {
		int scaledX = x / 2;
		int scaledZ = z / 2;
		int oddX = x % 2;
		int oddZ = z % 2;
		float height = 100.0F - (float)Math.sqrt((float)(x * x + z * z)) * 8.0F;
		height = clamp(height, -100.0F, 80.0F);

		for(int rx = -12; rx <= 12; ++rx) {
			for(int rz = -12; rz <= 12; ++rz) {
				long shiftedX = scaledX + rx;
				long shiftedZ = scaledZ + rz;
				if (shiftedX * shiftedX + shiftedZ * shiftedZ > 4096L && simplex.sample2D((double)shiftedX, (double)shiftedZ) < -0.8999999761581421D) {
					float elevation = (Math.abs((float)shiftedX) * 3439.0F + Math.abs((float)shiftedZ) * 147.0F) % 13.0F + 9.0F;
					float smoothX = (float)(oddX - rx * 2);
					float smoothZ = (float)(oddZ - rz * 2);
					float noise = 100.0F - (float)Math.sqrt(smoothX * smoothX + smoothZ * smoothZ) * elevation;
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
			if((long)x * (long)x + (long)z * (long)z <= 4096L) {
				return Biome.THE_END.getId();
			}

			float height = getHeightAt(EndBiomeSource.this.simplex, x * 2 + 1, z * 2 + 1);

			if(height > 40.0F) {
				return Biome.END_HIGHLANDS.getId();
			} else if(height >= 0.0F) {
				return Biome.END_MIDLANDS.getId();
			} else {
				return height < -20.0F ? Biome.SMALL_END_ISLANDS.getId() : Biome.END_BARRENS.getId();
			}
		}
	}

}
