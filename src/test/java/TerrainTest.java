import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.biomeutils.terrain.ChunkGenerator;
import kaptainwutax.biomeutils.terrain.OverworldChunkGenerator;
import kaptainwutax.seedutils.mc.MCVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerrainTest {
    private static final int[] heightmap = {
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 45,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            45, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            45, 45, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            45, 45, 45, 46, 46, 46, 46, 46, 47, 46, 46, 46, 46, 46, 46, 46,
            44, 45, 45, 46, 46, 46, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47,
            44, 45, 45, 46, 46, 46, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47,
            44, 44, 45, 46, 46, 46, 47, 47, 48, 48, 48, 48, 48, 48, 47, 47,
            43, 45, 45, 46, 47, 47, 47, 48, 48, 48, 48, 48, 48, 48, 47, 47,
            43, 45, 46, 47, 47, 48, 48, 48, 48, 48, 48, 48, 48, 47, 47, 47,
            43, 45, 46, 47, 48, 48, 48, 48, 48, 48, 48, 48, 48, 47, 47, 46
    };

    @Test
    @DisplayName("Test Height map for 1.14")
    public void testHeight() {
        BiomeSource bs = new OverworldBiomeSource(MCVersion.v1_14, 1L);
        ChunkGenerator cg = new OverworldChunkGenerator(bs);
        for (int i = 0; i < 16 * 16; i++) {
            assertEquals(heightmap[i], cg.getHeightOnGround(i / 16, i % 16));
        }

    }
}
