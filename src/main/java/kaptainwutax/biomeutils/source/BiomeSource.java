package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.BPos;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

	public BPos locateBiome(int x, int y, int z, int radius, List<Biome> list, Random random) {
		return this.method_24385(x, y, z, radius, 1, list, random, false);
	}

	public BPos method_24385(int centerX, int centerY, int centerZ, int radius, int increment, List<Biome> list, Random random, boolean checkByLayer) {
		centerX >>= 2; centerZ >>= 2; centerY >>= 2; radius >>= 2;
		BPos blockPos = null;
		int r = 0;
		int s = checkByLayer ? 0 : radius;

		for(int depth = s; depth <= radius; depth += increment) {
			for(int oz = -depth; oz <= depth; oz += increment) {
				boolean isZEdge = Math.abs(oz) == depth;

				for(int ox = -depth; ox <= depth; ox += increment) {
					if(checkByLayer) {
						boolean isXEdge = Math.abs(ox) == depth;
						if(!isXEdge && !isZEdge)continue;
					}

					int x = centerX + ox;
					int z = centerZ + oz;

					if(list.contains(this.getBiomeForNoiseGen(x, centerY, z))) {
						if(blockPos == null || random.nextInt(r + 1) == 0) {
							blockPos = new BPos(x << 2, centerY, z << 2);
							if (checkByLayer) {
								return blockPos;
							}
						}

						++r;
					}
				}
			}
		}

		return blockPos;
	}

	public interface BiomeSupplier {
		Biome getBiome(int x, int y, int z);
	}

}
