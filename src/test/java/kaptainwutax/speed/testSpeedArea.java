package kaptainwutax.speed;

import kaptainwutax.TestFramework;
import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.version.MCVersion;
import org.junit.jupiter.api.*;
import org.junitpioneer.jupiter.CartesianEnumSource;
import org.junitpioneer.jupiter.CartesianProductTest;

import java.util.concurrent.TimeUnit;

@DisplayName("Measure speed")
@Tag("Speed")
@TestFramework.Overworld
@TestFramework.Nether
@TestFramework.End
public class testSpeedArea {


	private BiomeSource biomesource;

	@BeforeEach
	public void setup(TestInfo info) {}

	@AfterEach
	public void tearDown(TestInfo info) {
		this.biomesource = null;
	}

	public void init(Dimension dimension, MCVersion version, TestInfo info) {
		this.biomesource = BiomeSource.of(dimension, version, 1L);
	}

	public long run(int size, int offset) {
		long start = System.nanoTime();
		int sum = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < 256; y++) {
				for (int z = 0; z < size; z++) {
					Biome biome = this.biomesource.getBiomeForNoiseGen(x + offset, y, z + offset);
					sum += biome.getId();
				}
			}
		}
		long end = System.nanoTime();
		return end - start;
	}

	@DisplayName("Minecraft Speed Area")
	@CartesianProductTest(name = "Area Speed Minecraft {0} {1}")
	@Timeout(value = 2)
	@CartesianEnumSource(MCVersion.class)
	@CartesianEnumSource(Dimension.class)
	public void speedArea(MCVersion version, Dimension dimension, TestInfo info) {
		init(dimension, version, info);
		long elapsed = run(64, 0);
		System.out.printf("Took %.2f ms for version %s in dimension %s%n", elapsed / 1e6, version, dimension);
	}

	@DisplayName("Minecraft Speed Single Block")
	@CartesianProductTest(name = "Single Block Speed Minecraft {0} {1}")
	@Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
	@CartesianEnumSource(MCVersion.class)
	@CartesianEnumSource(Dimension.class)
	public void speedSingleBlock(MCVersion version, Dimension dimension, TestInfo info) {
		init(dimension, version, info);
		long elapsed = run(1, 0);
		System.out.printf("Took %.2f ms for version %s in dimension %s%n", elapsed / 1e6, version, dimension);
	}
}
