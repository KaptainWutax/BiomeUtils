package kaptainwutax.v1_16_5;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.EndBiomeSource;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.BPos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class End {
    @Test
    @DisplayName("Test End layers 3D")
    public void testEnd3D() {
        EndBiomeSource source = new EndBiomeSource(MCVersion.v1_16, 1551515151585454L);
        assertEquals(Biome.SMALL_END_ISLANDS,source.getBiome3D(10000,251,10000));
        int sum=0;
        for (int y = 0; y < 256; y++) {
            sum+=source.getBiome3D(10000,y,10000).getId();
        }
        assertEquals(10689,sum);
    }

    @Test
    @DisplayName("Test End layers 2D")
    public void testEnd2D() {
        EndBiomeSource source = new EndBiomeSource(MCVersion.v1_16, 1551515151585454L);
        int sum=0;
        for (int x = 0; x < 1000; x++) {
            for (int z = 0; z < 1000; z++) {
                sum+=source.getBiome(10000+x,0,10000+z).getId();
            }
        }
        assertEquals(41033489,sum);
    }
}
