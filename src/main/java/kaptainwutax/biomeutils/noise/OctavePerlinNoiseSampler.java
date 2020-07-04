package kaptainwutax.biomeutils.noise;

import kaptainwutax.biomeutils.MathHelper;
import kaptainwutax.seedutils.lcg.rand.JRand;

public class OctavePerlinNoiseSampler implements NoiseSampler {
	private final PerlinNoiseSampler[] octaveSamplers;

	public OctavePerlinNoiseSampler(JRand random, int octaveCount) {
		this.octaveSamplers = new PerlinNoiseSampler[octaveCount];
		for (int i = 0; i < octaveCount; i++) {
			this.octaveSamplers[i] = new PerlinNoiseSampler(random);
		}
	}

	public double sample(double x, double y, double z) {
		return this.sample(x, y, z, 0.0D, 0.0D, false);
	}

	public double sample(double x, double y, double z, double d, double e, boolean bl) {
		double f = 0.0D;
		double g = 1.0D;
		PerlinNoiseSampler[] var16 = this.octaveSamplers;
		int var17 = var16.length;

		for(int var18 = 0; var18 < var17; ++var18) {
			PerlinNoiseSampler perlinNoiseSampler = var16[var18];
			f += perlinNoiseSampler.sample(maintainPrecision(x * g), bl ? -perlinNoiseSampler.originY : maintainPrecision(y * g), maintainPrecision(z * g), d * g, e * g) / g;
			g /= 2.0D;
		}

		return f;
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