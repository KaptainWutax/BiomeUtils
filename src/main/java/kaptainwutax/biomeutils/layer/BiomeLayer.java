package kaptainwutax.biomeutils.layer;

import kaptainwutax.seedutils.prng.SeedMixer;

import java.util.HashMap;
import java.util.Map;

public abstract class BiomeLayer {

	protected final BiomeLayer parent;
	private final long layerSeed;
	private long localSeed;

	private Map<Long, Integer> cache = new HashMap<>();

	public BiomeLayer(long worldSeed, long salt, BiomeLayer parent) {
		this.layerSeed = getLayerSeed(worldSeed, salt);
		this.parent = parent;
	}

	public BiomeLayer(long worldSeed, long salt) {
		this(worldSeed, salt, null);
	}

	public int get(int x, int z) {
		long v = (long)x << 32 | z;
		Integer r = this.cache.get(v);

		if(r == null) {
			r = this.sample(x, z);
			this.cache.put(v, r);
			return r;
		}

		return r;
	}

	public abstract int sample(int x, int z);

	public static long getLayerSeed(long seed, long salt) {
		long midSalt = SeedMixer.mixSeed(salt, salt);
		midSalt = SeedMixer.mixSeed(midSalt, salt);
		midSalt = SeedMixer.mixSeed(midSalt, salt);
		long layerSeed = SeedMixer.mixSeed(seed, midSalt);
		layerSeed = SeedMixer.mixSeed(layerSeed, midSalt);
		layerSeed = SeedMixer.mixSeed(layerSeed, midSalt);
		return layerSeed;
	}

	public static long getLocalSeed(long layerSeed, int x, int z) {
		layerSeed = SeedMixer.mixSeed(layerSeed, x);
		layerSeed = SeedMixer.mixSeed(layerSeed, z);
		layerSeed = SeedMixer.mixSeed(layerSeed, x);
		layerSeed = SeedMixer.mixSeed(layerSeed, z);
		return layerSeed;
	}

	public void setSeed(int x, int z) {
		this.localSeed = BiomeLayer.getLocalSeed(this.layerSeed, x, z);
	}

	public int nextInt(int bound) {
		int i = (int)Math.floorMod(this.localSeed >> 24, bound);
		this.localSeed = SeedMixer.mixSeed(this.localSeed, this.layerSeed);
		return i;
	}

	public int choose(int a, int b) {
		return this.nextInt(2) == 0 ? a : b;
	}

	public int choose(int a, int b, int c, int d) {
		int i = this.nextInt(4);
		if(i == 0)return a;
		else if(i == 1)return b;
		else return i == 2 ? c : d;
	}

}
