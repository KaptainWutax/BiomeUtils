package kaptainwutax.biomeutils.biome.surface.builder;

import kaptainwutax.biomeutils.biome.surface.SurfaceConfig;

public abstract class SurfaceBuilder {
	private final SurfaceConfig surfaceConfig;

	public SurfaceBuilder(SurfaceConfig surfaceConfig) {
		this.surfaceConfig = surfaceConfig;
	}

	public SurfaceConfig getSurfaceConfig() {
		return surfaceConfig;
	}
}
