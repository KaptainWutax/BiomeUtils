package kaptainwutax.biomeutils.noise;

import kaptainwutax.seedutils.lcg.rand.JRand;

public class OctaveSimplexNoiseSampler implements NoiseSampler {
	private final SimplexNoiseSampler[] octaveSamplers;
	private final int octaveCount;

	public OctaveSimplexNoiseSampler(JRand random, int octaveCount) {
		this.octaveCount = octaveCount;
		this.octaveSamplers = new SimplexNoiseSampler[octaveCount];

		for (int i = 0; i < octaveCount; ++i) {
			this.octaveSamplers[i] = new SimplexNoiseSampler(random);
		}
	}

	public double sample(double x, double y) {
		return this.sample(x, y, false);
	}

	public double sample(double x, double y, boolean bl) {
		double noise = 0.0D;
		// contribution of each octaves to the final noise, diminished by a factor of 2 (or increased by factor of 0.5)
		double persistence = 1.0D;

		for (int i = 0; i < this.octaveCount; ++i) {
			noise += this.octaveSamplers[i].sample2D(x * persistence + (bl ? this.octaveSamplers[i].originX : 0.0D), y * persistence + (bl ? this.octaveSamplers[i].originY : 0.0D)) / persistence;
			persistence /= 2.0D;
		}

		return noise;
	}

	@Override
	public double sample(double x, double y, double d, double e) {
		return this.sample(x, y, true) * 0.55D;
	}
}