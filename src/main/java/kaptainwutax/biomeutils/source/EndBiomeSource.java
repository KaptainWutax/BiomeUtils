package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.EndLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class EndBiomeSource extends BiomeSource {

	public EndLayer full;
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

	protected void build() {
		this.layers.add(this.full = new EndLayer(this.getVersion(), this.getWorldSeed()));
		this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full));
		this.layers.setScales();
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return Biome.REGISTRY.get(this.voronoi.get(x, 0, z));
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int y, int z) {
		return Biome.REGISTRY.get(this.full.get(x, 0, z));
	}

}
