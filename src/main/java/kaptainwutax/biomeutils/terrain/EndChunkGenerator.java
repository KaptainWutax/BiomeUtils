package kaptainwutax.biomeutils.terrain;

import kaptainwutax.biomeutils.MathHelper;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.biomeutils.source.EndBiomeSource;
import kaptainwutax.seedutils.util.UnsupportedVersion;

public class EndChunkGenerator extends SurfaceChunkGenerator {

	public EndChunkGenerator(BiomeSource biomeSource) {
		super(biomeSource, 2, 1, 128, true);
		throw new UnsupportedVersion(this.biomeSource.getVersion(),"end chunk generator");
	}

	@Override
	protected double computeNoiseFalloff(double depth, double scale, int y) {
		double fallOff = ((double) y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;

		if(fallOff < 0.0D) {
			fallOff *= 4.0D;
		}

		return fallOff;
	}

	protected double[] computeNoiseRange(int x, int z) {
		double[] ds = new double[2];
		ds[0] = ((EndBiomeSource)this.biomeSource).height.sample(x,0,z) - 8.0f;
		ds[1]  = ds[0]  > 0.0 ? 0.25 : 1.0;
		return ds;
	}

	@Override
	public int getSeaLevel() {
		return 0;
	}

	@Override
	protected void sampleNoiseColumn(double[] buffer, int x, int z) {
		this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
	}

}