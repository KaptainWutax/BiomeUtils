package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.rand.ChunkRand;

public class WoodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
	public WoodedBadlandsSurfaceBuilder(SurfaceConfig surfaceConfig) {
		super(surfaceConfig);
	}

	@Override
	protected boolean highContion(int y, int elevation) {
		return y > 86 + elevation * 2;
	}
}
