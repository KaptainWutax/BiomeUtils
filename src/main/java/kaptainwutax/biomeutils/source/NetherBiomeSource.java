package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.BiomePoint;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.nether.NetherLayer;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;

public class NetherBiomeSource extends LayeredBiomeSource<IntBiomeLayer> {

	private static final BiomePoint[] DEFAULT_BIOME_POINTS = {
		new BiomePoint(Biomes.NETHER_WASTES, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
		new BiomePoint(Biomes.SOUL_SAND_VALLEY, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
		new BiomePoint(Biomes.CRIMSON_FOREST, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F),
		new BiomePoint(Biomes.WARPED_FOREST, 0.0F, 0.5F, 0.0F, 0.0F, 0.375F),
		new BiomePoint(Biomes.BASALT_DELTAS, -0.5F, 0.0F, 0.0F, 0.0F, 0.175F)
	};
	private final BiomePoint[] biomePoints;
	public NetherLayer full;
	public VoronoiLayer voronoi;
	private boolean threeDimensional;

	public NetherBiomeSource(MCVersion version, long worldSeed) {
		this(version, worldSeed, DEFAULT_BIOME_POINTS);
	}

	public NetherBiomeSource(MCVersion version, long worldSeed, BiomePoint... biomePoints) {
		super(version, worldSeed);
		this.biomePoints = biomePoints;
		this.build();
	}

	@Override
	public Dimension getDimension() {
		return Dimension.NETHER;
	}

	public NetherBiomeSource addDimension() {
		this.threeDimensional = true;
		this.full = null;
		this.layers.clear();
		this.build();
		return this;
	}

	protected void build() {
		if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_16)) {
			this.layers.add(this.full = new NetherLayer(this.getVersion(), this.getWorldSeed(), this.threeDimensional, this.biomePoints));
			this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), true, this.full));
		} else {
			this.layers.add(this.full = new NetherLayer(this.getVersion(), this.getWorldSeed(), false, null));
			this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full) {
				@Override
				public int sample(int x, int y, int z) {
					return this.getVersion().isOlderThan(MCVersion.v1_16) ? Biomes.NETHER_WASTES.getId() : super.sample(x, y, z);
				}
			});
		}

		this.layers.setScales();
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
