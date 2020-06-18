package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.UnsupportedVersion;

public class NetherBiomeSource extends BiomeSource {

	public NetherBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed);

		if(!this.getVersion().isOlderThan(MCVersion.v1_16)) {
			throw new UnsupportedVersion(this.getVersion(), "nether biomes");
		}

		this.build();
	}

	@Override
	protected void build() {

	}

	@Override
	public Biome getBiome(int blockX, int blockY, int blockZ) {
		if(this.getVersion().isOlderThan(MCVersion.v1_16)) {
			return Biome.NETHER_WASTES;
		}

		return null;
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return this.getBiome(x, y, z);
	}

}
