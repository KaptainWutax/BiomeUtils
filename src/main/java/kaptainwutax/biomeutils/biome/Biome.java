package kaptainwutax.biomeutils.biome;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.biomeutils.source.StaticNoiseSource;
import kaptainwutax.mcutils.block.Block;
import kaptainwutax.mcutils.block.Blocks;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class Biome {
	private final MCVersion version;
	private final Dimension dimension;
	private final int id;
	private final String name;
	private final Category category;
	private final Precipitation precipitation;
	private final float temperature;

	private final float scale;
	private final float depth;

	private final Biome parent;
	private final Block topBlock;
	private Biome child;

	public Biome(MCVersion version, Dimension dimension, int id, String name, Category category, Precipitation precipitation,
				 float temperature, float scale, float depth, Biome parent) {
		this(version, dimension, id, name, category, precipitation, temperature, scale, depth, parent, Blocks.GRASS);
	}

	public Biome(MCVersion version, Dimension dimension, int id, String name, Category category, Precipitation precipitation,
				 float temperature, float scale, float depth, Biome parent, Block topBlock) {
		this.version = version;
		this.dimension = dimension;
		this.id = id;
		this.name = name;
		this.category = category;
		this.precipitation = precipitation;
		this.temperature = temperature;
		this.scale = scale;
		this.depth = depth;
		this.parent = parent;
		this.topBlock = topBlock;

		if(this.parent != null) {
			this.parent.child = this;
		}
	}

	public MCVersion getVersion() {
		return this.version;
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
		if(this.category == Biome.Category.OCEAN) {
			return Temperature.OCEAN;
		} else if(this.getTemperature() < 0.2F) {
			return Temperature.COLD;
		} else if(this.getTemperature() < 1.0F) {
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

	public Block getTopBlock() {
		return topBlock;
	}

	public static boolean isShallowOcean(int id, MCVersion version) {
		// TODO validate me **CRITICAL** (this is an undiscovered bug, frozen ocean was dormant)
		if(version.isOlderThan(MCVersion.v1_13)) {
			return id == Biomes.OCEAN.getId();
		}

		return id == Biomes.OCEAN.getId() || id == Biomes.WARM_OCEAN.getId() || id == Biomes.LUKEWARM_OCEAN.getId()
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

	public static boolean areSimilar(int id, Biome b2, MCVersion version) {
		if(b2 == null) return false;
		if(id == b2.getId()) return true;

		Biome b = Biomes.REGISTRY.get(id);
		if(b == null) return false;

		if(version.isNewerOrEqualTo(MCVersion.v1_16_2)) {
			// special check for the new badlands_plateau category
			if(b == Biomes.WOODED_BADLANDS_PLATEAU || b == Biomes.BADLANDS_PLATEAU) {
				return b2 == Biomes.WOODED_BADLANDS_PLATEAU || b2 == Biomes.BADLANDS_PLATEAU;
			} else if(b2 == Biomes.WOODED_BADLANDS_PLATEAU || b2 == Biomes.BADLANDS_PLATEAU) {
				return false; // very important check (non commutativity)
			}

			return b.getCategory() == b2.getCategory();
		}

		if(id != Biomes.WOODED_BADLANDS_PLATEAU.getId() && id != Biomes.BADLANDS_PLATEAU.getId()) {
			if(b.getCategory() != Biome.Category.NONE && b2.getCategory()
				!= Biome.Category.NONE && b.getCategory() == b2.getCategory()) {
				return true;
			}

			return b == b2;
		}

		return b2 == Biomes.WOODED_BADLANDS_PLATEAU || b2 == Biomes.BADLANDS_PLATEAU;
	}

	public static boolean applyAll(Function<Integer, Boolean> function, int... ints) {
		for(int i : ints) {
			if(!function.apply(i)) {
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
		if(this == o) return true;
		if(!(o instanceof Biome)) return false;
		Biome biome = (Biome)o;
		return id == biome.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Biome{" +
			"id=" + this.id +
			", name='" + this.name + '\'' +
			", category=" + this.category +
			", precipitation=" + this.precipitation +
			", temperature=" + this.temperature +
			", scale=" + this.scale +
			", depth=" + this.depth +
			", parent=" + (this.parent == null ? null : this.parent.name) +
			", dimension=" + dimension +
			", child=" + (this.child == null ? null : this.child.name) +
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
		return StaticNoiseSource.TEMPERATURE_CACHE.get(x, y, z, this::getTemperature);
	}

	public float getTempAt(BPos pos) {
		return StaticNoiseSource.TEMPERATURE_CACHE.get(pos.getX(), pos.getY(), pos.getZ(), this::getTemperature);
	}

	private float getTemperature(int x, int y, int z) {
		float temperature = this.temperature;
		if(this.equals(Biomes.FROZEN_OCEAN) || this.equals(Biomes.DEEP_FROZEN_OCEAN)) {
			double d0 = StaticNoiseSource.FROZEN_TEMPERATURE_NOISE.sample((double)x * 0.05D, (double)z * 0.05D, false) * 7.0D;
			double d1 = StaticNoiseSource.BIOME_INFO_NOISE.sample((double)x * 0.2D, (double)z * 0.2D, false);
			double d2 = d0 + d1;
			if(d2 < 0.3D) {
				double d3 = StaticNoiseSource.BIOME_INFO_NOISE.sample((double)x * 0.09D, (double)z * 0.09D, false);
				if(d3 < 0.8D) {
					temperature = 0.2F;
				}
			}
		}
		if(y > 64) {
			float f1 = (float)(StaticNoiseSource.TEMPERATURE_NOISE.sample((float)x / 8.0F, (float)z / 8.0F, false) * 4.0D);
			return temperature - (f1 + (float)y - 64.0F) * 0.05F / 30.0F;
		}
		return temperature;
	}

	@FunctionalInterface
	public interface TemperatureSampler {
		float sample(int x, int y, int z);
	}

}
