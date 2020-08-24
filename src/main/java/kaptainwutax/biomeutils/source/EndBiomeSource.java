package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.end.EndBiomeLayer;
import kaptainwutax.biomeutils.layer.end.EndHeightLayer;
import kaptainwutax.biomeutils.layer.end.EndSimplexLayer;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class EndBiomeSource extends BiomeSource {

	public EndSimplexLayer simplex;
	public EndHeightLayer height;
	public EndBiomeLayer full;
	public VoronoiLayer voronoi;

	protected final LayerStack<BiomeLayer> layers = new LayerStack<>();

	public EndBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed);
		this.build();
	}

	@Override
	public LayerStack<BiomeLayer> getLayers() {
		return this.layers;
	}

	@Override
	public Dimension getDimension() {
		return Dimension.END;
	}

	protected void build() {
		this.layers.add(this.simplex = new EndSimplexLayer(this.getVersion(), this.getWorldSeed()));
		this.layers.add(this.height = new EndHeightLayer(this.getVersion(), this.getWorldSeed(), this.simplex));
		this.layers.add(this.full = new EndBiomeLayer(this.getVersion(), this.getWorldSeed(), this.height));
		this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full));
		this.layers.setScales();
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biome.REGISTRY.get(this.voronoi.get(x, 0, z));
	}

	// warning end biomes displayed on f3 are actually the sampled 2d map with voronoi on all the different y
	public Biome getBiome3D(int x, int y, int z) {
		return Biome.REGISTRY.get(this.voronoi.get(x, y, z));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biome.REGISTRY.get(this.full.get(x, 0, z));
	}

}
