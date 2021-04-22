package kaptainwutax.biomeutils.layer.end;

import kaptainwutax.biomeutils.layer.BoolBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.noiseutils.simplex.SimplexNoiseSampler;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.rand.JRand;

public class EndSimplexLayer extends BoolBiomeLayer {

	public static final LCG SIMPLEX_SKIP = LCG.JAVA.combine(17292);
	protected final SimplexNoiseSampler simplex;

	public EndSimplexLayer(MCVersion version, long worldSeed) {
		super(version);
		JRand rand = new JRand(worldSeed);
		rand.advance(SIMPLEX_SKIP);
		this.simplex = new SimplexNoiseSampler(rand);
	}

	@Override
	public boolean sample(int x, int y, int z) {
		return this.simplex.sample2D(x, z) < (double) -0.9F;
	}

}
