package kaptainwutax.biomeutils.layer.end;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.noise.SimplexNoiseSampler;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.MCVersion;

public class EndSimplexLayer extends BiomeLayer {

    public static final LCG SIMPLEX_SKIP = LCG.JAVA.combine(17292);
    protected final SimplexNoiseSampler simplex;

    public EndSimplexLayer(MCVersion version, long worldSeed) {
        super(version);
        JRand rand = new JRand(worldSeed);
        rand.advance(SIMPLEX_SKIP);
        this.simplex = new SimplexNoiseSampler(rand);
    }

    @Override
    public int sample(int x, int y, int z) {
        return this.simplex.sample2D(x, z) < -0.8999999761581421D ? 1 : 0;
    }

}
