package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.composite.ComputeLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class OldRiverInBiomes extends ComputeLayer {
	// before the introduction of noise based rivers, river were based on the rare biomes
	public OldRiverInBiomes(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int compute(int value, int x, int z) {
		this.setSeed(x, z);
		if ((value != Biomes.SWAMP.getId() || this.nextInt(6) != 0)
				&& (value != Biomes.JUNGLE.getId() && value != Biomes.JUNGLE_HILLS.getId() || this.nextInt(8) != 0)) {
			return value;
		}
		return Biomes.RIVER.getId();
	}

}
