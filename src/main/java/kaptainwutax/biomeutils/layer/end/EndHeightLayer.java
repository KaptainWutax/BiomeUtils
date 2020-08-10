package kaptainwutax.biomeutils.layer.end;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.seedutils.mc.MCVersion;

public class EndHeightLayer extends BiomeLayer {

    public EndHeightLayer(MCVersion version, long worldSeed, BiomeLayer parent) {
        super(version, parent);
    }

    @Override
    public int sample(int x, int y, int z) {
        int scaledX = x / 2;
        int scaledZ = z / 2;
        int oddX = x % 2;
        int oddZ = z % 2;
        float height = 100.0F - (float)Math.sqrt((float)(x * x + z * z)) * 8.0F;
        height = clamp(height);

        for(int rx = -12; rx <= 12; ++rx) {
            for(int rz = -12; rz <= 12; ++rz) {
                int shiftedX = scaledX + rx;
                int shiftedZ = scaledZ + rz;
                if (shiftedX * shiftedX + shiftedZ * shiftedZ > 4096L && this.getParent().get(shiftedX, 0, shiftedZ) == 1) {
                    float elevation = (Math.abs((float)shiftedX) * 3439.0F + Math.abs((float)shiftedZ) * 147.0F) % 13.0F + 9.0F;
                    float smoothX = (float)(oddX - rx * 2);
                    float smoothZ = (float)(oddZ - rz * 2);
                    float noise = 100.0F - (float)Math.sqrt(smoothX * smoothX + smoothZ * smoothZ) * elevation;
                    noise = clamp(noise);
                    height = Math.max(height, noise);
                }
            }
        }

        return Float.floatToIntBits(height);
    }

    protected static float clamp(float value) {
        if(value < -100.0F)return -100.0F;
        return Math.min(value, 80.0F);
    }

}
