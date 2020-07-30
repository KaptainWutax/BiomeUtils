package kaptainwutax.biomeutils;

public class MathHelper {
	public static long lfloor(double d) {
		long l = (long)d;
		return d < (double)l ? l - 1L : l;
	}

	public static double lerp(double delta, double first, double second) {
		return first + delta * (second - first);
	}

	public static double lerp2(double deltaX, double deltaY, double d, double e, double f, double g) {
		return lerp(deltaY, lerp(deltaX, d, e), lerp(deltaX, f, g));
	}

	public static double lerp3(double deltaX, double deltaY, double deltaZ, double d, double e, double f, double g, double h, double i, double j, double k) {
		return lerp(deltaZ, lerp2(deltaX, deltaY, d, e, f, g), lerp2(deltaX, deltaY, h, i, j, k));
	}

	public static double clampedLerp(double first, double second, double delta) {
		if (delta < 0.0D) {
			return first;
		} else {
			return delta > 1.0D ? second : lerp(delta, first, second);
		}
	}

	public static float sqrt(float f) {
		return (float)Math.sqrt(f);
	}

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else {
			return Math.min(value, max);
		}
	}
}