package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.noise.SimplexNoiseSampler;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.MCVersion;

public class EndLayer extends BiomeLayer {

    public static final LCG SIMPLEX_SKIP = LCG.JAVA.combine(17292);
    protected final SimplexNoiseSampler simplex;

    public EndLayer(MCVersion version, long worldSeed) {
        super(version, worldSeed, -1, (BiomeLayer)null);
        JRand rand = new JRand(worldSeed);
        rand.advance(SIMPLEX_SKIP);
        this.simplex = new SimplexNoiseSampler(rand);
    }

    @Override
    public int sample(int x, int y, int z) {
        x >>= 2;
        z >>= 2;

        if((long)x * (long)x + (long)z * (long)z <= 4096L) {
            return Biome.THE_END.getId();
        }

        float height = getHeightAt(this.simplex, x * 2 + 1, z * 2 + 1);

        if(height > 40.0F) {
            return Biome.END_HIGHLANDS.getId();
        } else if(height >= 0.0F) {
            return Biome.END_MIDLANDS.getId();
        } else {
            return height < -20.0F ? Biome.SMALL_END_ISLANDS.getId() : Biome.END_BARRENS.getId();
        }
    }

    public static float getHeightAt(SimplexNoiseSampler simplex, int x, int z) {
        int scaledX = x / 2;
        int scaledZ = z / 2;
        int oddX = x % 2;
        int oddZ = z % 2;
        float height = 100.0F - (float)Math.sqrt((float)(x * x + z * z)) * 8.0F;
        height = clamp(height);

        for(int rx = -12; rx <= 12; ++rx) {
            for(int rz = -12; rz <= 12; ++rz) {
                long shiftedX = scaledX + rx;
                long shiftedZ = scaledZ + rz;
                if (shiftedX * shiftedX + shiftedZ * shiftedZ > 4096L && simplex.sample2D((double)shiftedX, (double)shiftedZ) < -0.8999999761581421D) {
                    float elevation = (Math.abs((float)shiftedX) * 3439.0F + Math.abs((float)shiftedZ) * 147.0F) % 13.0F + 9.0F;
                    float smoothX = (float)(oddX - rx * 2);
                    float smoothZ = (float)(oddZ - rz * 2);
                    float noise = 100.0F - (float)Math.sqrt(smoothX * smoothX + smoothZ * smoothZ) * elevation;
                    noise = clamp(noise);
                    height = Math.max(height, noise);
                }
            }
        }

        return height;
    }

    protected static float clamp(float value) {
        if(value < -100.0F)return -100.0F;
        return Math.min(value, 80.0F);
    }

}
