package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.land.BaseBiomesLayer;
import kaptainwutax.biomeutils.layer.land.ContinentLayer;
import kaptainwutax.biomeutils.layer.noise.NoiseLayer;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;
import kaptainwutax.biomeutils.layer.water.RiverLayer;
import kaptainwutax.mcutils.rand.seed.SeedMixer;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class BiomeLayer {

	private final MCVersion version;
	private final BiomeLayer[] parents;

	public long salt;
	public long layerSeed;
	public long localSeed;
	protected int hintSize = 1;

	protected int scale = -1;
	protected int layerId = -1;

	public BiomeLayer(MCVersion version, BiomeLayer... parents) {
		this.version = version;
		this.parents = parents;
	}

	public BiomeLayer(MCVersion version) {
		this(version, (BiomeLayer)null);
	}

	public BiomeLayer(MCVersion version, long worldSeed, long salt, BiomeLayer... parents) {
		this(version, parents);
		this.salt = salt;
		this.layerSeed = getLayerSeed(worldSeed, this.salt);
	}

	public BiomeLayer(MCVersion version, long worldSeed, long salt) {
		this(version, worldSeed, salt, (BiomeLayer)null);
	}

	public MCVersion getVersion() {
		return this.version;
	}

	public int getScale() {
		return this.scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public boolean hasParent() {
		return this.parents.length > 0;
	}

	public int getLayerId() {
		return layerId;
	}

	public void setLayerId(int layerId) {
		this.layerId = layerId;
	}

	public BiomeLayer getParent() {
		return this.getParent(0);
	}

	public int getHintSize() {
		return hintSize;
	}

	public void setHintSize(int size) {
		this.setHintSize(size, true);
	}

	public void setHintSize(int size, boolean recursive) {
		if(recursive) {
			setRecursiveHintSize(this, size);
		} else {
			this.hintSize = size;
		}
	}

	public void setRecursiveHintSize(BiomeLayer last, int hintSize) {
		if(last == null) return;
		int max = 0;

		for(BiomeLayer biomeLayer : last.getParents()) {
			int shift = 0;
			// TODO check new version
			int offset = last instanceof BaseBiomesLayer || last instanceof NoiseLayer || last instanceof ContinentLayer || last instanceof RiverLayer ? 0 : 2;
			if(last instanceof ScaleLayer) {
				shift = 1;
				offset = 3;
			} else if(last instanceof VoronoiLayer) {
				shift = 2;
				offset = 3;
			}

			this.setRecursiveHintSize(biomeLayer, (hintSize >> shift) + offset);
			max = Math.max(max, hintSize);
		}
		last.setHintSize(max, false);
	}

	@SuppressWarnings("unchecked")
	public <T extends BiomeLayer> T getParent(Class<T> type) {
		return (T)this.getParent(0);
	}

	public BiomeLayer getParent(int id) {
		return this.parents[id];
	}

	@SuppressWarnings("unchecked")
	public <T extends BiomeLayer> T getParent(int id, Class<T> type) {
		return (T)this.getParent(id);
	}

	public boolean isMergingLayer() {
		return this.parents.length > 1;
	}

	public BiomeLayer[] getParents() {
		return this.parents;
	}

	public static long getMidSalt(long salt) {
		long midSalt = SeedMixer.mixSeed(salt, salt);
		midSalt = SeedMixer.mixSeed(midSalt, salt);
		midSalt = SeedMixer.mixSeed(midSalt, salt);
		return midSalt;
	}

	public static long getLayerSeed(long worldSeed, long salt) {
		long midSalt = getMidSalt(salt);
		long layerSeed = SeedMixer.mixSeed(worldSeed, midSalt);
		layerSeed = SeedMixer.mixSeed(layerSeed, midSalt);
		layerSeed = SeedMixer.mixSeed(layerSeed, midSalt);
		return layerSeed;
	}

	public static long getLocalSeed(long layerSeed, int x, int z) {
		layerSeed = SeedMixer.mixSeed(layerSeed, x);
		layerSeed = SeedMixer.mixSeed(layerSeed, z);
		layerSeed = SeedMixer.mixSeed(layerSeed, x);
		layerSeed = SeedMixer.mixSeed(layerSeed, z);
		return layerSeed;
	}

	public static long getLocalSeed(long worldSeed, long salt, int x, int z) {
		return getLocalSeed(getLayerSeed(worldSeed, salt), x, z);
	}

	public void setSeed(int x, int z) {
		this.localSeed = BiomeLayer.getLocalSeed(this.layerSeed, x, z);
	}

	public int nextInt(int bound) {
		// warning for JDK lower than 1.9 its important to left the (long) cast
		// @formatter:off
        int i = (int)Math.floorMod(this.localSeed >> 24, (long)bound);
        // @formatter:on
		this.localSeed = SeedMixer.mixSeed(this.localSeed, this.layerSeed);
		return i;
	}

	public int choose(int a, int b) {
		return this.nextInt(2) == 0 ? a : b;
	}

	public int choose(int a, int b, int c, int d) {
		int i = this.nextInt(4);
		return i == 0 ? a : i == 1 ? b : i == 2 ? c : d;
	}

	/**
	 * Utility to sample any type of layer cache for that layer (useful for biome stack that mix a lot of those)
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @return the biome at this layer or The void
	 */
	public int getBiome(int x, int y, int z) {
		if(this instanceof FloatBiomeLayer) {
			return Float.floatToIntBits(((FloatBiomeLayer)this).get(x, y, z));
		} else if(this instanceof BoolBiomeLayer) {
			return ((BoolBiomeLayer)this).get(x, y, z) ? 1 : 0;
		} else if(this instanceof IntBiomeLayer) {
			return ((IntBiomeLayer)this).get(x, y, z);
		}
		return Biomes.THE_VOID.getId();
	}

}
