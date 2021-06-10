package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.BiomePoint;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.noise.MultiNoiseLayer;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class MultiNoiseBiomeSource extends LayeredBiomeSource<IntBiomeLayer> {
	protected final BiomePoint[] biomePoints;
	public MultiNoiseLayer full;
	public VoronoiLayer voronoi;
	protected boolean threeDimensional;

	public MultiNoiseBiomeSource(MCVersion version, long worldSeed, BiomePoint... biomePoints) {
		super(version, worldSeed);
		this.biomePoints = biomePoints;
		this.build();
	}

	@Override
	public abstract Dimension getDimension();

	public MultiNoiseBiomeSource addDimension() {
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
			this.layers.add(this.full = new MultiNoiseLayer(this.getVersion(), this.getWorldSeed(), false, null));
			this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full) {
				@Override
				public int sample(int x, int y, int z) {
					return this.getVersion().isOlderThan(MCVersion.v1_16) ? Biomes.NETHER_WASTES.getId() : super.sample(x, y, z);
				}
			});
		}

		this.layers.setScales();
	}

	public boolean is3D() {
		return threeDimensional;
	}

	public BiomePoint[] getBiomePoints() {
		return biomePoints;
	}

	@Override
	public Biome getBiome(BPos bpos) {
		return Biomes.REGISTRY.get(this.voronoi.get(bpos.getX(), bpos.getY(), bpos.getZ()));
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biomes.REGISTRY.get(this.voronoi.get(x, y, z));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biomes.REGISTRY.get(this.full.get(x, y, z));
	}

}
