package kaptainwutax.biomeutils.biome;

import kaptainwutax.biomeutils.VersionedGen;
import kaptainwutax.biomeutils.layer.cache.FloatLayerCache;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.noiseutils.simplex.OctaveSimplexNoiseSampler;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Biome extends VersionedGen {

	// use mainly for snow and lava stuff
	public static final OctaveSimplexNoiseSampler TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(1234L), IntStream.of(0));
	// used for frozen ocean
	public static final OctaveSimplexNoiseSampler FROZEN_TEMPERATURE_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(3456L), IntStream.of(-2, -1, 0));
	// used for flowers
	public static final OctaveSimplexNoiseSampler BIOME_INFO_NOISE = new OctaveSimplexNoiseSampler(new ChunkRand(2345L), IntStream.of(0));

	public static final FloatLayerCache TEMPERATURE_CACHE = new FloatLayerCache(1024);

	private final int id;
	private final String name;
	private final Category category;
	private final Precipitation precipitation;
	private final float temperature;

	private final float scale;
	private final float depth;

	private final Biome parent;
	private final Dimension dimension;
	private Biome child;

	public Biome(MCVersion version, Dimension dimension, int id, String name, Category category, Precipitation precipitation,
				 float temperature, float scale, float depth, Biome parent) {
		super(version);
		this.dimension = dimension;
		this.id = id;
		this.name = name;
		this.category = category;
		this.precipitation = precipitation;
		this.temperature = temperature;
		this.scale = scale;
		this.depth = depth;
		this.parent = parent;
		if (this.parent != null) this.parent.child = this;
	}

	public Dimension getDimension() {
		return this.dimension;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Category getCategory() {
		return this.category;
	}

	public Precipitation getPrecipitation() {
		return this.precipitation;
	}

	public float getTemperature() {
		return this.temperature;
	}

	public Temperature getTemperatureGroup() {
		if (this.category == Biome.Category.OCEAN) {
			return Temperature.OCEAN;
		} else if (this.getTemperature() < 0.2F) {
			return Temperature.COLD;
		} else if (this.getTemperature() < 1.0F) {
			return Temperature.MEDIUM;
		}

		return Temperature.WARM;
	}


	public float getScale() {
		return scale;
	}

	public float getDepth() {
		return depth;
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	public Biome getParent() {
		return this.parent;
	}

	public boolean hasChild() {
		return this.child != null;
	}

	public Biome getChild() {
		return this.child;
	}

	public static boolean isShallowOcean(int id, VersionedGen versionedGen) {
		if (versionedGen.is1_12down.call()) { // TODO validate me **CRITICAL** (this is an undiscovered bug, frozen ocean was dormant)
			return id == Biomes.OCEAN.getId();
		}
		return id == Biomes.WARM_OCEAN.getId() || id == Biomes.LUKEWARM_OCEAN.getId() || id == Biomes.OCEAN.getId()
				|| id == Biomes.COLD_OCEAN.getId() || id == Biomes.FROZEN_OCEAN.getId();
	}

	public static boolean isOcean(int id) {
		return id == Biomes.WARM_OCEAN.getId() || id == Biomes.LUKEWARM_OCEAN.getId() || id == Biomes.OCEAN.getId()
				|| id == Biomes.COLD_OCEAN.getId() || id == Biomes.FROZEN_OCEAN.getId()
				|| id == Biomes.DEEP_WARM_OCEAN.getId() || id == Biomes.DEEP_LUKEWARM_OCEAN.getId()
				|| id == Biomes.DEEP_OCEAN.getId() || id == Biomes.DEEP_COLD_OCEAN.getId()
				|| id == Biomes.DEEP_FROZEN_OCEAN.getId();
	}


	public static boolean isRiver(int id) {
		return id == Biomes.RIVER.getId() || id == Biomes.FROZEN_RIVER.getId();
	}

	public static boolean areSimilar(int id, Biome b2, VersionedGen versionedGen) {
		if (b2 == null) return false;
		if (id == b2.getId()) return true;

		Biome b = Biomes.REGISTRY.get(id);
		if (b == null) return false;
		if (versionedGen.is1_16_2up.call()) {
			// special check for the new badlands_plateau category
			if (b == Biomes.WOODED_BADLANDS_PLATEAU || b == Biomes.BADLANDS_PLATEAU) {
				return b2 == Biomes.WOODED_BADLANDS_PLATEAU || b2 == Biomes.BADLANDS_PLATEAU;
			} else if (b2 == Biomes.WOODED_BADLANDS_PLATEAU || b2 == Biomes.BADLANDS_PLATEAU) {
				return false; // very important check (non commutativity)
			}
			return b.getCategory() == b2.getCategory();
		}

		if (id != Biomes.WOODED_BADLANDS_PLATEAU.getId() && id != Biomes.BADLANDS_PLATEAU.getId()) {
			if (b.getCategory() != Biome.Category.NONE && b2.getCategory()
					!= Biome.Category.NONE && b.getCategory() == b2.getCategory()) {
				return true;
			}

			return b == b2;
		}

		return b2 == Biomes.WOODED_BADLANDS_PLATEAU || b2 == Biomes.BADLANDS_PLATEAU;
	}

	public static boolean applyAll(Function<Integer, Boolean> function, int... ints) {
		for (int i : ints) {
			if (!function.apply(i)) {
				return false;
			}
		}

		return true;
	}

	public static int equalsOrDefault(int comparator, int comparable, int fallback) {
		return comparator == comparable ? comparable : fallback;
	}

	public Biome.Data at(int x, int z) {
		return new Biome.Data(this, x, z);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Biome)) return false;
		Biome biome = (Biome) o;
		return id == biome.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Biome{" +
				"id=" + id +
				", name='" + name + '\'' +
				", category=" + category +
				", precipitation=" + precipitation +
				", temperature=" + temperature +
				", scale=" + scale +
				", depth=" + depth +
				", parent=" + (parent == null ? null : parent.name) +
				", dimension=" + dimension +
				", child=" + (child == null ? null : child.name) +
				'}';
	}

	public enum Category {
		// badlands plateau is a new 1.16 one (they also removed theend and nether)
		NONE("none"), TAIGA("taiga"), EXTREME_HILLS("extreme_hills"),
		JUNGLE("jungle"), MESA("mesa"), BADLANDS_PLATEAU("badlands_plateau"), PLAINS("plains"), SAVANNA("savanna"),
		ICY("icy"), THE_END("the_end"), BEACH("beach"), FOREST("forest"),
		OCEAN("ocean"), DESERT("desert"), RIVER("river"), SWAMP("swamp"),
		MUSHROOM("mushroom"), NETHER("nether");

		private final String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	public enum Temperature {
		OCEAN("ocean"), COLD("cold"), MEDIUM("medium"), WARM("warm");

		private final String name;

		Temperature(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	public enum Precipitation {
		NONE("none"), RAIN("rain"), SNOW("snow");

		private final String name;

		Precipitation(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	public static class Data {
		public final Predicate<Biome> predicate;
		public final Biome biome;
		public final int x;
		public final int z;

		public Data(Biome biome, int x, int z) {
			this(b -> b == biome, biome, x, z);
		}

		public Data(Predicate<Biome> predicate, int x, int z) {
			this(predicate, null, x, z);
		}

		protected Data(Predicate<Biome> predicate, Biome biome, int x, int z) {
			this.predicate = predicate;
			this.biome = biome;
			this.x = x;
			this.z = z;
		}

		public boolean test(OverworldBiomeSource source) {
			return this.predicate.test(source.getBiome(this.x, 0, this.z));
		}
	}

	public float getTempAt(int x, int y, int z) {
		return TEMPERATURE_CACHE.get(x, y, z, this::getTemperature);

	}

	public float getTempAt(BPos pos) {
		return TEMPERATURE_CACHE.get(pos.getX(), pos.getY(), pos.getZ(), this::getTemperature);
	}

	private float getTemperature(int x, int y, int z) {
		float temperature = this.temperature;
		if (this.equals(Biomes.FROZEN_OCEAN) || this.equals(Biomes.DEEP_FROZEN_OCEAN)) {
			double d0 = Biome.FROZEN_TEMPERATURE_NOISE.sample((double) x * 0.05D, (double) z * 0.05D, false) * 7.0D;
			double d1 = Biome.BIOME_INFO_NOISE.sample((double) x * 0.2D, (double) z * 0.2D, false);
			double d2 = d0 + d1;
			if (d2 < 0.3D) {
				double d3 = Biome.BIOME_INFO_NOISE.sample((double) x * 0.09D, (double) z * 0.09D, false);
				if (d3 < 0.8D) {
					temperature = 0.2F;
				}
			}
		}
		if (y > 64) {
			float f1 = (float) (TEMPERATURE_NOISE.sample((float) x / 8.0F, (float) z / 8.0F, false) * 4.0D);
			return temperature - (f1 + (float) y - 64.0F) * 0.05F / 30.0F;
		}
		return temperature;
	}

	@FunctionalInterface
	public interface TemperatureSampler {
		float sample(int x, int y, int z);
	}

}
