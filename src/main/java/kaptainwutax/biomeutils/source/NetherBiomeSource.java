package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.noise.DoublePerlinNoiseSampler;
import kaptainwutax.biomeutils.noise.MixedNoisePoint;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NetherBiomeSource extends BiomeSource {

	private static final MixedNoisePoint[] DEFAULT_BIOME_POINTS = {
			new MixedNoisePoint(Biome.NETHER_WASTES, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
			new MixedNoisePoint(Biome.SOUL_SAND_VALLEY,0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
			new MixedNoisePoint(Biome.CRIMSON_FOREST,0.4F, 0.0F, 0.0F, 0.0F, 0.0F),
			new MixedNoisePoint(Biome.WARPED_FOREST,0.0F, 0.5F, 0.0F, 0.0F, 0.375F),
			new MixedNoisePoint(Biome.BASALT_DELTAS,-0.5F, 0.0F, 0.0F, 0.0F, 0.175F)
	};

	private DoublePerlinNoiseSampler temperature;
	private DoublePerlinNoiseSampler humidity;
	private DoublePerlinNoiseSampler altitude;
	private DoublePerlinNoiseSampler weirdness;
	private final MixedNoisePoint[] biomePoints;

	public NetherBiomeSource.Layer full;
	public VoronoiLayer voronoi;
	private LayerStack<BiomeLayer> layers = new LayerStack<>();

	private boolean threeDimensionalSampling;

	public NetherBiomeSource(MCVersion version, long worldSeed) {
		this(version, worldSeed, DEFAULT_BIOME_POINTS);
	}

	public NetherBiomeSource(MCVersion version, long worldSeed, MixedNoisePoint... biomePoints) {
		super(version, worldSeed);
		this.biomePoints = biomePoints;
		this.build();
	}

	public NetherBiomeSource addDimension() {
		this.threeDimensionalSampling = true;
		this.layers.add(this.full = new NetherBiomeSource.Layer(this.getVersion()));
		this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), true, this.full));
		this.layers.setScales();
		return this;
	}

	@Override
	public LayerStack<?> getLayers() {
		return this.layers;
	}

	@Override
	protected void build() {
		if(this.getVersion().isOlderThan(MCVersion.v1_16))return;
		this.temperature = new DoublePerlinNoiseSampler(new ChunkRand(this.getWorldSeed()), IntStream.rangeClosed(-7, -6));
		this.humidity = new DoublePerlinNoiseSampler(new ChunkRand(this.getWorldSeed() + 1L), IntStream.rangeClosed(-7, -6));
		this.altitude = new DoublePerlinNoiseSampler(new ChunkRand(this.getWorldSeed() + 2L), IntStream.rangeClosed(-7, -6));
		this.weirdness = new DoublePerlinNoiseSampler(new ChunkRand(this.getWorldSeed() + 3L), IntStream.rangeClosed(-7, -6));

		this.layers.add(this.full = new NetherBiomeSource.Layer(this.getVersion()));
		this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full));
		this.layers.setScales();
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		if(this.getVersion().isOlderThan(MCVersion.v1_16)) {
			return Biome.NETHER_WASTES;
		}

		return Biome.REGISTRY.get(this.voronoi.get(x, y, z));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		if(this.getVersion().isOlderThan(MCVersion.v1_16)) {
			return Biome.NETHER_WASTES;
		}

		return Biome.REGISTRY.get(this.full.get(x >> 2, y,z >> 2));
	}

	public class Layer extends BiomeLayer {
		public Layer(MCVersion version) {
			super(version, -1, -1, (BiomeLayer)null);
		}

		public int sample(int x, int y, int z) {
			y = NetherBiomeSource.this.threeDimensionalSampling ? y : 0;

			MixedNoisePoint point = new MixedNoisePoint(null,
					(float)NetherBiomeSource.this.temperature.sample(x, y, z),
					(float)NetherBiomeSource.this.humidity.sample(x, y, z),
					(float)NetherBiomeSource.this.altitude.sample(x, y, z),
					(float)NetherBiomeSource.this.weirdness.sample(x, y, z), 0.0F);

			return Stream.of(NetherBiomeSource.this.biomePoints).min(Comparator.comparing(m -> m.distanceTo(point)))
					.map(MixedNoisePoint::getBiome).orElse(Biome.THE_VOID).getId();
		}
	}

}
