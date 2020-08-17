package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.mathutils.util.Mth;

import java.util.Arrays;
import java.util.List;

public abstract class BoundRestriction extends Restriction {

    private final long salt;

    private final long bound;
    private final long value;

    private final int shortBound;
    private final long shortValue;

    public BoundRestriction(int x, int z, long salt, long bound, long value) {
        super(x, z);
        this.salt = salt;
        this.bound = bound;
        this.value = value;

        this.shortBound = Long.numberOfTrailingZeros(this.bound);
        this.shortValue = Mth.mask(this.value, this.shortBound);
    }

    @Override
    public List<Integer> getBitPoints() {
        if(this.shortBound == 0)return super.getBitPoints();
        return Arrays.asList(24 + this.shortBound, 64);
    }

    @Override
    public boolean testSeed(long seed, long bits) {
        long localSeed = BiomeLayer.getLocalSeed(seed, this.salt, this.getX(), this.getZ());
        return bits == 64
                ? (int)Math.floorMod(localSeed >> 24, this.bound) == this.value
                : Mth.mask(localSeed >> 24, this.shortBound)  == this.shortValue;
    }

}
