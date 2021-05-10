package kaptainwutax.biomeutils.layer.nether;

import kaptainwutax.biomeutils.biome.BiomePoint;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.noiseutils.noise.DoublePerlinNoiseSampler;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NetherLayer extends IntBiomeLayer {

	private final BiomePoint[] biomePoints;
	private final boolean is3D;
	private DoublePerlinNoiseSampler temperature;
	private DoublePerlinNoiseSampler humidity;
	private DoublePerlinNoiseSampler altitude;
	private DoublePerlinNoiseSampler weirdness;

	public NetherLayer(MCVersion version, long worldSeed, boolean is3D, BiomePoint[] biomePoints) {
		super(version, (IntBiomeLayer) null);
		this.is3D = is3D;

		if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_16)) {
			this.temperature = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed), IntStream.rangeClosed(-7, -6));
			this.humidity = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed + 1L), IntStream.rangeClosed(-7, -6));
			this.altitude = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed + 2L), IntStream.rangeClosed(-7, -6));
			this.weirdness = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed + 3L), IntStream.rangeClosed(-7, -6));
		}

		this.biomePoints = biomePoints;
	}

	@Override
	public int sample(int x, int y, int z) {
		if (this.getVersion().isOlderThan(MCVersion.v1_16)) return Biomes.NETHER_WASTES.getId();

		y = this.is3D ? y : 0;

		BiomePoint point = new BiomePoint(null,
				(float) this.temperature.sample(x, y, z),
				(float) this.humidity.sample(x, y, z),
				(float) this.altitude.sample(x, y, z),
				(float) this.weirdness.sample(x, y, z), 0.0F);

		return Stream.of(this.biomePoints).min(Comparator.comparing(m -> m.distanceTo(point)))
				.map(BiomePoint::getBiome).orElse(Biomes.THE_VOID).getId();
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName()+" "+x+" "+z+" "+xSize+" "+zSize+" : "+this.getScale());
		for (int offsetX = -2; offsetX <= 2; offsetX++) {
			for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {

			}
		}
		return new int[xSize*ySize*zSize];
	}

}
