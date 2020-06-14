import kaptainwutax.biomeutils.device.BiomeDevice;
import kaptainwutax.biomeutils.device.IceSpikes;
import kaptainwutax.biomeutils.device.Mushroom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CombinedTest {

	public static void main(String[] args) {
		long min = 0L, max = min + 2_000_000L;

		List<Long> naiveSeeds = new ArrayList<>();


		BiomeDevice device = new BiomeDevice()
				.restrict(Mushroom.around(0, 0, 100))
				.restrict(IceSpikes.around(0, 0, 100));

		long start = System.nanoTime();

		for(long seed = min; seed < max; seed++) {
			if(device.testSeed(seed))System.out.println(seed);
		}

		System.out.println("Naive took " + (double)(System.nanoTime() - start) / 1_000_000_000.0D + " seconds.");

		List<Long> fastSeeds = new ArrayList<>();
		start = System.nanoTime();

		device.findSeeds(min, max).forEach(System.out::println);

		System.out.println("Fast took " + (double)(System.nanoTime() - start) / 1_000_000_000.0D + " seconds.");

		if(naiveSeeds.equals(fastSeeds)) {
			System.out.println("Output is identical with " + fastSeeds.size() + " elements.");
		} else {
			System.out.println("Output differs.");
			System.out.println("Naive list has " + naiveSeeds.size() + " elements.");
			System.out.println("Fast list has " + fastSeeds.size() + " elements.");
			System.out.println(naiveSeeds);
			System.out.println(fastSeeds);

			List<Long> difference = naiveSeeds.stream().filter(s -> !fastSeeds.contains(s)).collect(Collectors.toList());
			System.out.println("The difference is: " + difference);
		}
	}

}
