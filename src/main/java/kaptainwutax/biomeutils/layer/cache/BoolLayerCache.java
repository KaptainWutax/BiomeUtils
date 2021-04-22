package kaptainwutax.biomeutils.layer.cache;

import kaptainwutax.mathutils.util.Mth;

import java.util.Arrays;

public class BoolLayerCache {

	private final long[] keys;
	private final boolean[] values;
	private final int mask;

	public BoolLayerCache(int capacity) {
		if (capacity < 1 || !Mth.isPowerOf2(capacity)) {
			throw new UnsupportedOperationException("capacity must be a power of 2");
		}

		this.keys = new long[capacity];
		Arrays.fill(this.keys, -1);
		this.values = new boolean[capacity];
		this.mask = (int) Mth.getMask(Long.numberOfTrailingZeros(capacity));
	}

	public boolean get(int x, int y, int z, Sampler sampler) {
		long key = this.uniqueHash(x, y, z);
		int id = this.murmur64(key) & this.mask;

		if (this.keys[id] == key) {
			return this.values[id];
		}

		boolean value = sampler.sample(x, y, z);
		this.keys[id] = key;
		this.values[id] = value;
		return value;
	}

	public long uniqueHash(int x, int y, int z) {
		long hash = (long) x & Mth.getMask(26);
		hash |= ((long) z & Mth.getMask(26)) << 26;
		hash |= ((long) y & Mth.getMask(8)) << 52;
		return hash;
	}

	public int murmur64(long value) {
		value ^= value >>> 33;
		value *= 0xFF51AFD7ED558CCDL;
		value ^= value >>> 33;
		value *= 0xC4CEB9FE1A85EC53L;
		value ^= value >>> 33;
		return (int) value;
	}

	@FunctionalInterface
	public interface Sampler {
		boolean sample(int x, int y, int z);
	}

}
