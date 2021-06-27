package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;
import kaptainwutax.biomeutils.source.BiomeSource;

public abstract class SurfaceBuilder {
	private final SurfaceConfig surfaceConfig;

	public SurfaceBuilder(SurfaceConfig surfaceConfig) {
		this.surfaceConfig = surfaceConfig;
	}

	public SurfaceConfig getSurfaceConfig() {
		return surfaceConfig;
	}

	public SurfaceBuilder getNoiseSurfaceBuilder(BiomeSource source, double noise) {
		return this;
	}
}
