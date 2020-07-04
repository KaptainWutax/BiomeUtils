import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.biomeutils.terrain.ChunkGenerator;
import kaptainwutax.biomeutils.terrain.OverworldChunkGenerator;
import kaptainwutax.seedutils.mc.MCVersion;

public class TerrainTest {
	public static void main(String[] args) {
		int[] heightmap = new int[] {
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
		BiomeSource bs = new OverworldBiomeSource(MCVersion.v1_14, 1L);
		ChunkGenerator cg = new OverworldChunkGenerator(bs);
		for (int i = 0; i < 16 * 16; i++)
			if (heightmap[i] != cg.getHeightOnGround(i / 16, i % 16))
				System.out.println("test failed");
	}
}
