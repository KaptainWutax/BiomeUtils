import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.device.BiomeDevice;
import kaptainwutax.biomeutils.device.Mushroom;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.ArrayList;
import java.util.List;

public class MushroomTest {

	public static void main(String[] args) {
		long min = 500_000_000L, max = min + 200_000L;

		List<Long> naiveSeeds = new ArrayList<>();
		long start = System.nanoTime();

		for(long seed = min; seed < max; seed++) {
			OverworldBiomeSource layers = new OverworldBiomeSource(MCVersion.v1_15, seed);

			if(layers.getBiome(-4000,0,-4000).getCategory() == Biome.Category.MUSHROOM) {
				naiveSeeds.add(seed);
			}
		}

		System.out.println("Naive took " + (double)(System.nanoTime() - start) / 1_000_000_000.0D + " seconds.");

		List<Long> fastSeeds = new ArrayList<>();
		start = System.nanoTime();

		BiomeDevice device = new BiomeDevice().restrict(Mushroom.at(-4000, -4000));
		device.findSeeds(min, max).forEach(fastSeeds::add);

		System.out.println("Fast took " + (double)(System.nanoTime() - start) / 1_000_000_000.0D + " seconds.");

		if(naiveSeeds.equals(fastSeeds)) {
			System.out.println("Output is identical with " + fastSeeds.size() + " elements.");
		} else {
			System.out.println("Output differs.");
			System.out.println("Naive list has " + naiveSeeds.size() + " elements.");
			System.out.println("Fast list has " + fastSeeds.size() + " elements.");
			System.out.println(naiveSeeds);
			System.out.println(fastSeeds);
		}
	}

}
