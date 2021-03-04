package kaptainwutax.biomeutils.noise;

import kaptainwutax.biomeutils.MathHelper;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.ChunkRand;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OctavePerlinNoiseSampler implements NoiseSampler {
	private final PerlinNoiseSampler[] octaveSamplers;
	
	private static final LCG SKIP_262 = LCG.JAVA.combine(262);

	public final double lacunarity;
	public final double persistence;

	public OctavePerlinNoiseSampler(JRand random, int octaveCount) {
		this.octaveSamplers = new PerlinNoiseSampler[octaveCount];
		for (int i = 0; i < octaveCount; i++) {
			this.octaveSamplers[i] = new PerlinNoiseSampler(random);
		}
		this.lacunarity = 1.0;
		this.persistence = 1.0;
	}

	public OctavePerlinNoiseSampler(ChunkRand rand, IntStream octaves) {
		this(rand, octaves.boxed().collect(Collectors.toList()));
	}

	public OctavePerlinNoiseSampler(ChunkRand rand, List<Integer> octaves) {
		octaves = octaves.stream().sorted(Integer::compareTo).collect(Collectors.toList());

		if(octaves.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		}

		int start = -octaves.get(0);
		int end = octaves.get(octaves.size() - 1);
		int length = start + end + 1;

		if(length < 1) {
			throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
		}

		PerlinNoiseSampler perlin = new PerlinNoiseSampler(rand);

		this.octaveSamplers = new PerlinNoiseSampler[length];

		if(end >= 0 && end < length && octaves.contains(0)) {
			this.octaveSamplers[end] = perlin;
		}

		for(int idx = end + 1; idx < length; ++idx) {
			if(idx >= 0 && octaves.contains(end - idx)) {
				this.octaveSamplers[idx] = new PerlinNoiseSampler(rand);
			} else {
				rand.advance(SKIP_262);
			}
		}

		if(end > 0) {
			long n = (long)(perlin.sample(0.0D, 0.0D, 0.0D, 0.0D, 0.0D) * 9.223372036854776E18D);
			rand.setSeed(n);
			for(int o = end - 1; o >= 0; --o) {
				if(o < length && octaves.contains(end - o)) {
					this.octaveSamplers[o] = new PerlinNoiseSampler(rand);
				} else {
					rand.advance(SKIP_262);
				}
			}
		}

		this.persistence = Math.pow(2.0D, end);
		this.lacunarity = 1.0D / (Math.pow(2.0D, length) - 1.0D);
	}

	public double sample(double x, double y, double z) {
		return this.sample(x, y, z, 0.0D, 0.0D, false);
	}

	public double sample(double x, double y, double z, double d, double e, boolean bl) {
		double noise = 0.0D;
		// contribution of each octaves to the final noise, diminished by a factor of 2 (or increased by factor of 0.5)
		double persistence = this.persistence;
		// distance between octaves, increased for each by a factor of 2
		double lacunarity = this.lacunarity;

		for(PerlinNoiseSampler sampler: this.octaveSamplers) {
			if(sampler != null){
				noise += sampler.sample(maintainPrecision(x * persistence), bl ? -sampler.originY : maintainPrecision(y * persistence),
						maintainPrecision(z * persistence), d * persistence, e * persistence) * lacunarity;
			}
			persistence /= 2.0D;
			lacunarity *= 2.0D;
		}

		return noise;
	}

	public PerlinNoiseSampler getOctave(int octave) {
		return this.octaveSamplers[octave];
	}

	public static double maintainPrecision(double d) {
		return d - (double)MathHelper.lfloor(d / 3.3554432E7D + 0.5D) * 3.3554432E7D;
	}

	@Override
	public double sample(double x, double y, double d, double e) {
		return this.sample(x, y, 0.0D, d, e, false);
	}
}