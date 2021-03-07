package kaptainwutax.biomeutils.terrain;

import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.noiseutils.ChunkGenerator;
import kaptainwutax.seedutils.mc.Dimension;

public class OverworldChunkGenerator {

	/**
	 * Return an overworldGenerator to generate the world
	 * @deprecated
	 * This method is no longer acceptable use NoiseUtils
	 *
	 */
	@Deprecated()
	public static kaptainwutax.noiseutils.terrain.OverworldChunkGenerator of(BiomeSource biomeSource) {
		return (kaptainwutax.noiseutils.terrain.OverworldChunkGenerator) ChunkGenerator.of(Dimension.OVERWORLD,biomeSource);
	}
}