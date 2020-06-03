package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class BiomeSource {

	private final MCVersion version;
	private final long worldSeed;

	public BiomeSource(MCVersion version, long worldSeed) {
		this.version = version;
		this.worldSeed = worldSeed;
	}

	public MCVersion getVersion() {
		return this.version;
	}

	public long getWorldSeed() {
		return this.worldSeed;
	}

	public abstract Biome getBiome(int x, int y, int z);

	public abstract Biome getBiomeForNoiseGen(int x, int y, int z);

	public boolean iterateUniqueBiomes(int x, int y, int z, int radius, Predicate<Biome> shouldContinue) {
		int i = x - radius >> 2;
		int j = y - radius >> 2;
		int k = z - radius >> 2;
		int l = x + radius >> 2;
		int m = y + radius >> 2;
		int n = z + radius >> 2;
		int o = l - i + 1;
		int p = m - j + 1;
		int q = n - k + 1;

		Set<Integer> set = new HashSet<>();

		for(int r = 0; r < q; ++r) {
			for(int s = 0; s < o; ++s) {
				for(int t = 0; t < p; ++t) {
					int u = i + s;
					int v = j + t;
					int w = k + r;

					Biome b = this.getBiomeForNoiseGen(u, v, w);

					if(!set.contains(b.getId())) {
						if(!shouldContinue.test(b))return false;
					}

					set.add(b.getId());
				}
			}
		}

		return true;
	}

	public boolean iterateUniqueBiomes(int x, int z, int radius, Predicate<Biome> shouldContinue) {
		int i = x - radius >> 2;
		int k = z - radius >> 2;
		int l = x + radius >> 2;
		int n = z + radius >> 2;
		int o = l - i + 1;
		int q = n - k + 1;

		Set<Integer> set = new HashSet<>();

		for(int r = 0; r < q; ++r) {
			for(int s = 0; s < o; ++s) {
				int u = i + s;
				int w = k + r;

				Biome b = this.getBiomeForNoiseGen(u, 0, w);

				if(!set.contains(b.getId())) {
					if(!shouldContinue.test(b))return false;
				}

				set.add(b.getId());
			}
		}

		return true;
	}

	public interface BiomeSupplier {
		Biome getBiome(int x, int y, int z);
	}

}
