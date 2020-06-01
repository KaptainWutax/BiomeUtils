import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;

public class MushroomTest {

	public static void main(String[] args) {
		for(long seed = 0; seed < Long.MAX_VALUE; seed++) {
			BiomeSource layers = new BiomeSource(seed, 4, 4).init();

			if(layers.voronoi.sampleBiome(0, 0) == Biome.MUSHROOM_FIELDS) {
				System.out.println(seed);
			}
		}
	}

}
