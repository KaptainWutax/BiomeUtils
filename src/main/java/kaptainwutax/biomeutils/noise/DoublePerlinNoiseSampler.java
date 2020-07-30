package kaptainwutax.biomeutils.noise;

import kaptainwutax.seedutils.mc.ChunkRand;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DoublePerlinNoiseSampler {
	private final double amplitude;
	private final OctavePerlinNoiseSampler firstSampler;
	private final OctavePerlinNoiseSampler secondSampler;

	public DoublePerlinNoiseSampler(ChunkRand rand, IntStream intStream) {
		this(rand, intStream.boxed().collect(Collectors.toList()));
	}

	public DoublePerlinNoiseSampler(ChunkRand rand, List<Integer> octaves) {
		this.firstSampler = new OctavePerlinNoiseSampler(rand, octaves);
		this.secondSampler = new OctavePerlinNoiseSampler(rand, octaves);
		int minNbOctave = octaves.stream().min(Integer::compareTo).orElse(0);
		int maxNbOctave = octaves.stream().max(Integer::compareTo).orElse(0);
		this.amplitude = 0.16666666666666666D / createAmplitude(maxNbOctave - minNbOctave);
	}

	private static double createAmplitude(int octaves) {
		return 0.1D * (1.0D + 1.0D / (double)(octaves + 1));
	}

	public double sample(double x, double y, double z) {
		double d = x * 1.0181268882175227D;
		double e = y * 1.0181268882175227D;
		double f = z * 1.0181268882175227D;
		return (this.firstSampler.sample(x, y, z) + this.secondSampler.sample(d, e, f)) * this.amplitude;
	}
}

