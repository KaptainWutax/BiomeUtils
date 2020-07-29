package kaptainwutax.biomeutils.terrain;

import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.UnsupportedVersion;

public abstract class ChunkGenerator {

	protected final BiomeSource biomeSource;
	protected final MCVersion version;

	public ChunkGenerator(BiomeSource biomeSource) {
		this.biomeSource = biomeSource;
		this.version = biomeSource.getVersion();

		if(this.version != MCVersion.v1_14) {
			throw new UnsupportedVersion(this.version, "chunk generator");
		}
	}

	public int getSeaLevel() {
		return 63;
	}

	public int getHeightInGround(int x, int z) {
		return this.getHeightOnGround(x, z) - 1;
	}

	public abstract int getHeightOnGround(int x, int z);

}