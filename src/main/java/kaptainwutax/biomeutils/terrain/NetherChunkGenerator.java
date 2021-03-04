package kaptainwutax.biomeutils.terrain;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.MathHelper;
import kaptainwutax.biomeutils.noise.OctavePerlinNoiseSampler;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

public class NetherChunkGenerator extends SurfaceChunkGenerator {

	public NetherChunkGenerator(BiomeSource biomeSource) {
		super(biomeSource, 1, 2, 128, false);
	}

	@Override
	protected double computeNoiseFalloff(double depth, double scale, int y) {
		double fallOff = ((double) y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;

		if(fallOff < 0.0D) {
			fallOff *= 4.0D;
		}

		return fallOff;
	}

	@Override
	public int getSeaLevel() {
		return 32;
	}

	@Override
	protected double[] computeNoiseRange(int x, int z) {
		if (this.biomeSource.getVersion().isNewerOrEqualTo(MCVersion.v1_16)){
			return super.computeNoiseRange(x,z);
		}
		return new double[]{0.0,0.0};
	}


	@Override
	protected void sampleNoiseColumn(double[] buffer, int x, int z) {
		this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
	}

}