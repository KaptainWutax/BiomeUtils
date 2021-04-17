package kaptainwutax;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.mcutils.util.pos.BPos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpawnTest {
    @Test
    @DisplayName("Test Spawn for 1.12")
    public void testSpawn() {
        OverworldBiomeSource bs = new OverworldBiomeSource(MCVersion.v1_12, 4L);
        // wrong its -710, 64, 569 just to illustrate a point
        assertEquals(bs.getSpawnPoint(), new BPos(-76, 64, 128));

    }
}
