import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

public class MushroomTest {

	public static void main(String[] args) {
		for(long seed = 0; seed < Long.MAX_VALUE; seed++) {
			OverworldBiomeSource layers = new OverworldBiomeSource(MCVersion.v1_16,seed).build();

			if(layers.getBiome(0,0,0) == Biome.MUSHROOM_FIELDS) {
				System.out.println(seed);
			}
		}
	}

}
