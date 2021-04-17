package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.CrossLayer;
import kaptainwutax.mcutils.version.MCVersion;

public class OldRiverInBiomes extends BiomeLayer {
    // before the introduction of noise based rivers, river were based on the rare biomes
    public OldRiverInBiomes(MCVersion version, long worldSeed, long salt, BiomeLayer parent) {
        super(version, worldSeed, salt, parent);
    }

    @Override
    public int sample(int x, int y, int z) {
        this.setSeed(x, z);
        int center = this.getParent().get(x, y, z);
        if ((center != Biome.SWAMP.getId() || this.nextInt(6) != 0) && (center != Biome.JUNGLE.getId() && center != Biome.JUNGLE_HILLS.getId() || this.nextInt(8) != 0)) {
            return center;
        }
        return Biome.RIVER.getId();
    }
}
