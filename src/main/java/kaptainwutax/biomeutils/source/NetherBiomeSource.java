package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.biome.BiomePoint;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.noise.MultiNoiseLayer;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.version.MCVersion;

public class NetherBiomeSource extends MultiNoiseBiomeSource {

	private static final BiomePoint[] DEFAULT_BIOME_POINTS = {
		new BiomePoint(Biomes.NETHER_WASTES, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
		new BiomePoint(Biomes.SOUL_SAND_VALLEY, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
		new BiomePoint(Biomes.CRIMSON_FOREST, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F),
		new BiomePoint(Biomes.WARPED_FOREST, 0.0F, 0.5F, 0.0F, 0.0F, 0.375F),
		new BiomePoint(Biomes.BASALT_DELTAS, -0.5F, 0.0F, 0.0F, 0.0F, 0.175F)
	};

	public NetherBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed, DEFAULT_BIOME_POINTS);
	}

	@Override
	public Dimension getDimension() {
		return Dimension.NETHER;
	}

	// I have no idea what this function is supposed to represent...
	public NetherBiomeSource addDimension() {
		this.threeDimensional = true;
		this.full = null;
		this.layers.clear();
		this.build();
		return this;
	}

	protected void build() {
		if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_16)) {
			this.layers.add(this.full = new MultiNoiseLayer(this.getVersion(), this.getWorldSeed(), this.threeDimensional, this.biomePoints));
			this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), true, this.full));
		} else {
			this.layers.add(this.full = new MultiNoiseLayer(this.getVersion(), this.getWorldSeed(), this.threeDimensional, this.biomePoints) {
				@Override
				public int sample(int x, int y, int z) {
					return Biomes.NETHER_WASTES.getId();
				}
			});
			this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full) {
				@Override
				public int sample(int x, int y, int z) {
					return Biomes.NETHER_WASTES.getId();
				}
			});
		}

		this.layers.setScales();
	}

}
