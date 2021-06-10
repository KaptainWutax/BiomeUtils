package kaptainwutax.v1_16_1;

import kaptainwutax.TestFramework;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.mcutils.version.MCVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Minecraft v1.16.1 Overworld")
@Tag("v1.16.1")
@TestFramework.Overworld
public class Overworld {
	private static final String version = "1.16.1";
	private OverworldBiomeSource overworldBiomeSource;
	private int size;

	@BeforeEach
	public void setup() {
		this.overworldBiomeSource = new OverworldBiomeSource(MCVersion.v1_16_1, 541515181818L);
		this.size = 16;
	}

	@Test
	@DisplayName("Test full for " + version)
	public void testFull() {
		assertEquals(5839678890417025249L, getHash(1, 84), "One biome was wrong");
		assertEquals(8370355333428707571L, getHash(2, 84), "One biome was wrong");
		assertEquals(-165205488069234670L, getHash(4, 84), "One biome was wrong");
		assertEquals(3844629667750350445L, getHash(8, 84), "One biome was wrong");
		assertEquals(6540824892827124293L, getHash(16, 84), "One biome was wrong");
		assertEquals(-8968826312020734049L, getHash(32, 84), "One biome was wrong");
		assertEquals(7838822547083600260L, getHash(64, 84), "One biome was wrong");
		assertEquals(6922406005583211374L, getHash(128, 84), "One biome was wrong");
		assertEquals(6819316744870579513L, getHash(256, 84), "One biome was wrong");
		assertEquals(-7733744126525058874L, getHash(512, 84), "One biome was wrong");
		assertEquals(653477928679205168L, getHash(1024, 84), "One biome was wrong");
		assertEquals(-6601932306769119281L, getHash(2048, 84), "One biome was wrong");
		//assertEquals(-6909801988874714309L, getHash(4096, 84), "One biome was wrong");
	}

	@Test
	@DisplayName("Test voronoi for " + version)
	public void testVoronoi() {
		assertEquals(5839678890417025249L, getHashVoronoi(1, 84), "One biome was wrong");
		assertEquals(8370355333428707571L, getHashVoronoi(2, 84), "One biome was wrong");
		assertEquals(-165205488069234670L, getHashVoronoi(4, 84), "One biome was wrong");
		assertEquals(-79735791615299849L, getHashVoronoi(8, 84), "One biome was wrong");
		assertEquals(-2132543437809608847L, getHashVoronoi(16, 84), "One biome was wrong");
		assertEquals(2342560580002914529L, getHashVoronoi(32, 84), "One biome was wrong");
		assertEquals(7013273056434338339L, getHashVoronoi(64, 84), "One biome was wrong");
		assertEquals(-1505716045303541443L, getHashVoronoi(128, 84), "One biome was wrong");
		assertEquals(-2457046325124408004L, getHashVoronoi(256, 84), "One biome was wrong");
		assertEquals(-8709344793858922284L, getHashVoronoi(512, 84), "One biome was wrong");
		assertEquals(5675689783006304125L, getHashVoronoi(1024, 84), "One biome was wrong");
		assertEquals(-3925323828398010134L, getHashVoronoi(2048, 84), "One biome was wrong");
		//assertEquals(-8990950935806208166L, getHashVoronoi(4096, 84), "One biome was wrong");
	}

	public static long murmur64(long value) {
		value ^= value >>> 33;
		value *= 0xFF51AFD7ED558CCDL;
		value ^= value >>> 33;
		value *= 0xC4CEB9FE1A85EC53L;
		value ^= value >>> 33;
		return value;
	}

	public static long getHash(int size, long worldSeed) {
		BiomeSource overworldBiomeSource = new OverworldBiomeSource(MCVersion.v1_16_1, worldSeed);
		long hash = 0;
		for(int x = -size; x <= size; x++) {
			for(int z = -size; z <= size; z++) {
				int id = overworldBiomeSource.getBiomeForNoiseGen(x >> 2, 0, z >> 2).getId();
				hash = murmur64(id * hash ^ id);
			}
		}
		return hash;
	}

	public static long getHashVoronoi(int size, long worldSeed) {
		BiomeSource overworldBiomeSource = new OverworldBiomeSource(MCVersion.v1_16_1, worldSeed);
		long hash = 0;
		for(int x = -size; x <= size; x++) {
			for(int z = -size; z <= size; z++) {
				int id = overworldBiomeSource.getBiome(x, 0, z).getId();
				hash = murmur64(id * hash ^ id);
			}
		}
		return hash;
	}
}

/*
Bootstrap.register();
long seed=84;
BiomeManager biomeManager=new BiomeManager(new OverworldBiomeProvider(seed,false,false),BiomeManager.func_235200_a_(seed), FuzzedBiomeMagnifier.INSTANCE);
int size=16;
long hash=0;
for (int x = -size; x <= size; x++) {
    for (int z = -size; z <= size; z++) {
        Biome biome=biomeManager.getBiomeForNoiseGen(new BlockPos(z,0,z));
        long w=Registry.BIOME.getId(biome);
        hash = murmur64(w*hash^w);
    }
}
System.out.println(hash);
*/
