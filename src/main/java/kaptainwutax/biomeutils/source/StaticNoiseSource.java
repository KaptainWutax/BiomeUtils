package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.layer.cache.FloatLayerCache;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.noiseutils.simplex.OctaveSimplexNoiseSampler;

import java.util.stream.IntStream;

public class StaticNoiseSource {
	// use mainly for snow and lava stuff
	public static final OctaveSimplexNoiseSampler TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(1234L), IntStream.of(0));
	// used for frozen ocean
	public static final OctaveSimplexNoiseSampler FROZEN_TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(3456L), IntStream.of(-2, -1, 0));
	// used for flowers
	public static final OctaveSimplexNoiseSampler BIOME_INFO_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(2345L), IntStream.of(0));

	public static final FloatLayerCache TEMPERATURE_CACHE = new FloatLayerCache(1024);

}
