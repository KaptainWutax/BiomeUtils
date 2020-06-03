package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.MCVersion;

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

	public abstract Biome getBiome(int blockX, int blockY, int blockZ);

}
