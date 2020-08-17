package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Restriction {

    private final int x;
    private final int z;

    public Restriction(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public List<Integer> getBitPoints() {
        return Collections.singletonList(64);
    }

    public abstract boolean testSeed(long seed, long bits);

    public abstract boolean testSource(BiomeSource source);

    protected static BiomeLayer getLayer(MCVersion version, Class<? extends BiomeLayer> layerClass) {
        OverworldBiomeSource source = new OverworldBiomeSource(version, 0L);

        for(int i = 0; i < source.getLayers().size(); i++) {
            if(source.getLayer(i).getClass().equals(layerClass))return source.getLayer(i);
        }

        return null;
    }

    protected static int getLayerId(MCVersion version, Class<? extends BiomeLayer> layerClass) {
        return Objects.requireNonNull(getLayer(version, layerClass)).getLayerId();
    }

    protected static long getSalt(MCVersion version, Class<? extends BiomeLayer> layerClass) {
        return Objects.requireNonNull(getLayer(version, layerClass)).salt;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " at (" + this.getX() + ", " + this.getZ() + ")";
    }

    @FunctionalInterface
    public interface Factory<T extends Restriction> {
        T create(MCVersion version, int x, int z);
    }

}
