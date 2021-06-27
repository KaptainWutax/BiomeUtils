package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.biome.surface.builder.BadlandsSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.BasaltDeltasSurfaceBuilder;
import kaptainwutax.biomeutils.biome.surface.builder.SoulSandValleySurfaceBuilder;
import kaptainwutax.biomeutils.layer.cache.FloatLayerCache;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.util.data.Pair;
import kaptainwutax.mcutils.util.data.Quad;
import kaptainwutax.mcutils.util.data.Triplet;
import kaptainwutax.noiseutils.perlin.OctavePerlinNoiseSampler;
import kaptainwutax.noiseutils.simplex.OctaveSimplexNoiseSampler;
import kaptainwutax.seedutils.rand.JRand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is specific to what we call block noises, they are only called per block and should be aggressively cached, we made sure to not spread them around to
 * ensure peak performance, at no point a noise should be recreated if unnecessary
 */
@SuppressWarnings("unused")
public class StaticNoiseSource {
	// use mainly for snow and lava stuff
	public static final OctaveSimplexNoiseSampler TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(1234L), IntStream.of(0));
	// used for frozen ocean
	public static final OctaveSimplexNoiseSampler FROZEN_TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(3456L), IntStream.of(-2, -1, 0));
	// used for flowers
	public static final OctaveSimplexNoiseSampler BIOME_INFO_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(2345L), IntStream.of(0));

	public static final FloatLayerCache TEMPERATURE_CACHE = new FloatLayerCache(1024);
	// since valley noise are computed as seed + 1,2,3,4 we need to cache those generator to avoid making them again
	// for that we do so incrementally by invalidating olds seeds (we assume an order for each BiomeSource)
	private static final int TRESHOLD_VALLEY = 20;
	private static final HashMap<Long, OctavePerlinNoiseSampler> CACHED_VALLEY_NOISE = new HashMap<>(TRESHOLD_VALLEY);
	// patch noise are either + 4 or + 5 either way we cache those since they aren't fixed to a seed.
	private static final int TRESHOLD_PATCH = 5;
	private static final HashMap<Long, OctavePerlinNoiseSampler> CACHED_PATCH_NOISE = new HashMap<>(TRESHOLD_PATCH);

	private final long worldSeed;
	private Quad<Block[], OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> badlandsSurface = null;
	private Pair<OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> noises = null;
	private OctavePerlinNoiseSampler netherForestsNoise = null;
	private OctavePerlinNoiseSampler netherNoise = null;
	private List<OctavePerlinNoiseSampler> valleyNoise = null;
	private List<OctavePerlinNoiseSampler> patchNoise = null;

	public StaticNoiseSource(long worldSeed) {
		this.worldSeed = worldSeed;
	}

	public long getWorldSeed() {
		return this.worldSeed;
	}

	public Pair<OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> getNoises() {
		if(this.noises == null) {
			JRand rand = new JRand(this.getWorldSeed());
			OctaveSimplexNoiseSampler noise = new OctaveSimplexNoiseSampler(rand, IntStream.rangeClosed(-3, 0));
			OctaveSimplexNoiseSampler roofNoise = new OctaveSimplexNoiseSampler(rand, Collections.singletonList(0));
			this.noises = new Pair<>(noise, roofNoise);
		}
		return this.noises;
	}

	public List<OctavePerlinNoiseSampler> getValleyNoise() {
		// max is 5 but since we want adaptive we don't assume that
		if(this.valleyNoise == null) {
			int maxBlocksFloor = Math.max(BasaltDeltasSurfaceBuilder.FLOOR_BLOCK_STATES.size(), SoulSandValleySurfaceBuilder.FLOOR_BLOCK_STATES.size());
			int maxBlocksCeiling = Math.max(BasaltDeltasSurfaceBuilder.CEILING_BLOCK_STATES.size(), SoulSandValleySurfaceBuilder.CEILING_BLOCK_STATES.size());
			this.valleyNoise = new ArrayList<>();
			// floor and ceiling noise
			for(int i = 0; i < maxBlocksFloor + maxBlocksCeiling; i++) {
				OctavePerlinNoiseSampler sampler = CACHED_VALLEY_NOISE.computeIfAbsent(this.getWorldSeed() + i, key -> new OctavePerlinNoiseSampler(new JRand(key), Collections.singletonList(-4)));
				valleyNoise.add(sampler);
			}
			this.purgeCache(TRESHOLD_VALLEY);
		}
		return this.valleyNoise;
	}

	public List<OctavePerlinNoiseSampler> getPatchNoise() {
		if(this.patchNoise == null) {
			int souldSandSize = SoulSandValleySurfaceBuilder.FLOOR_BLOCK_STATES.size() + SoulSandValleySurfaceBuilder.CEILING_BLOCK_STATES.size();
			int basaltSize = BasaltDeltasSurfaceBuilder.FLOOR_BLOCK_STATES.size() + BasaltDeltasSurfaceBuilder.CEILING_BLOCK_STATES.size();
			this.patchNoise = new ArrayList<>();
			this.patchNoise.add(CACHED_PATCH_NOISE.computeIfAbsent(this.getWorldSeed() + souldSandSize, key -> new OctavePerlinNoiseSampler(new JRand(key), Collections.singletonList(0))));
			this.patchNoise.add(CACHED_PATCH_NOISE.computeIfAbsent(this.getWorldSeed() + basaltSize, key -> new OctavePerlinNoiseSampler(new JRand(key), Collections.singletonList(0))));
			this.purgeCache(TRESHOLD_PATCH);
		}
		return this.valleyNoise;
	}

	private void purgeCache(int treshold) {
		if(CACHED_VALLEY_NOISE.size() > treshold) {
			// we assumed correct order
			List<Long> worldSeeds = CACHED_VALLEY_NOISE.keySet().stream()
				.filter(e -> e < this.getWorldSeed())
				.collect(Collectors.toList());
			worldSeeds.forEach(CACHED_VALLEY_NOISE::remove);
			if(CACHED_VALLEY_NOISE.size() > treshold) {
				// in case someone didn't get the memo we remove all the seeds not in the range current;current+20
				worldSeeds = CACHED_VALLEY_NOISE.keySet().stream()
					.filter(e -> e > this.getWorldSeed() + treshold)
					.collect(Collectors.toList());
				worldSeeds.forEach(CACHED_VALLEY_NOISE::remove);
			}
		}
	}

	/**
	 * Compute or get the noises and bands for the Badlands Surface Generator
	 *
	 * @return quad as block[64], pillar noise, pillar roof noise, claybandsOffsetNoise
	 */
	public Quad<Block[], OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> getBadlandsSurface() {
		if(this.badlandsSurface == null) {
			Pair<Block[], OctaveSimplexNoiseSampler> bands = BadlandsSurfaceBuilder.generateBands(this.getWorldSeed());
			Pair<OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> noises = getNoises();
			// we store a reference to the noises here but we don't duplicate them.
			this.badlandsSurface = new Quad<>(bands.getFirst(), noises.getFirst(), noises.getSecond(), bands.getSecond());
		}
		return this.badlandsSurface;
	}

	/**
	 * Compute or get the noises for the Frozen Ocean Surface Generator
	 *
	 * @return pair as iceberg, iceberg roof noises
	 */
	public Pair<OctaveSimplexNoiseSampler, OctaveSimplexNoiseSampler> getFrozenOceanSurface() {
		return this.getNoises();
	}

	/**
	 * Compute or get the noise for Nether Forests Surface Generator
	 *
	 * @return decoration noise
	 */
	public OctavePerlinNoiseSampler getNetherForestsNoise() {
		if(this.netherForestsNoise == null) {
			JRand rand = new JRand(this.getWorldSeed());
			this.netherForestsNoise = new OctavePerlinNoiseSampler(rand, Collections.singletonList(0));
		}
		return this.netherForestsNoise;
	}

	/**
	 * Compute or get the noise for Nether Surface Generator
	 *
	 * @return decoration noise
	 */
	public OctavePerlinNoiseSampler getNetherNoise() {
		if(this.netherNoise == null) {
			JRand rand = new JRand(this.getWorldSeed());
			this.netherNoise = new OctavePerlinNoiseSampler(rand, IntStream.rangeClosed(-3, 0));
		}
		return this.netherNoise;
	}

	/**
	 * Compute or get the noise for Basalt Deltas Surface Generator
	 *
	 * @return floor noise, ceiling noise and patch noise
	 */
	public Triplet<Pair<OctavePerlinNoiseSampler, OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler, OctavePerlinNoiseSampler> getBasaltDeltasNoise() {
		List<OctavePerlinNoiseSampler> valleyNoise = getValleyNoise();
		List<OctavePerlinNoiseSampler> patchNoise = getPatchNoise();
		return new Triplet<>(new Pair<>(valleyNoise.get(0), valleyNoise.get(1)), valleyNoise.get(2), patchNoise.get(1));
	}

	/**
	 * Compute or get the noise for Soul Sand Valley Surface Generator
	 *
	 * @return floor noise, ceiling noise and patch noise
	 */
	public Triplet<Pair<OctavePerlinNoiseSampler, OctavePerlinNoiseSampler>, Pair<OctavePerlinNoiseSampler, OctavePerlinNoiseSampler>, OctavePerlinNoiseSampler> getSoulSandValleyNoise() {
		List<OctavePerlinNoiseSampler> valleyNoise = getValleyNoise();
		List<OctavePerlinNoiseSampler> patchNoise = getPatchNoise();
		return new Triplet<>(new Pair<>(valleyNoise.get(0), valleyNoise.get(1)), new Pair<>(valleyNoise.get(2), valleyNoise.get(3)), patchNoise.get(0));
	}

}
