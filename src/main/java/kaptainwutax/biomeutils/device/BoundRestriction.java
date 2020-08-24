package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.mathutils.util.Mth;

import java.util.Arrays;
import java.util.List;

public abstract class BoundRestriction extends Restriction {

    private final long salt;

    private final long bound;
    private final long min;
    private final long max;

    private final int shortBound;
    private final long shortMin;
    private final long shortMax;

    public BoundRestriction(int x, int z, long salt, long bound, long value) {
        this(x, z, salt, bound, value, value);
    }

    public BoundRestriction(int x, int z, long salt, long bound, long min, long max) {
        super(x, z);

        if(max >= bound) {
            System.err.println("Using " + max + " as maximum for bound " + bound + "? Go Fix.");
            max = bound - 1;
        }

        if(min < 0) {
            System.err.println("Using " + min + " as minimum for bound " + bound + "? Go Fix.");
            min = 0;
        }

        this.salt = salt;
        this.bound = bound;
        this.min = min;
        this.max = max;

        this.shortBound = Long.numberOfTrailingZeros(this.bound);
        long a = Mth.mask(this.min, this.shortBound);
        long b = Mth.mask(this.max, this.shortBound);
        this.shortMin = Math.min(a, b);
        this.shortMax = Math.max(a, b);
    }

    @Override
    public List<Integer> getBitPoints() {
        if(this.shortBound == 0)return super.getBitPoints();
        return Arrays.asList(24 + this.shortBound, 64);
    }

    @Override
    public boolean testSeed(long seed, long bits) {
        long localSeed = BiomeLayer.getLocalSeed(seed, this.salt, this.getX(), this.getZ());

        if(bits == 64) {
            int val = (int)Math.floorMod(localSeed >> 24, this.bound);
            return val >= this.min && val <= this.max;
        } else if(bits >= 24 + this.shortBound) {
            int val = (int)Mth.mask(localSeed >> 24, this.shortBound);
            return val >= this.shortMin && val <= this.shortMax;
        }

        return false;
    }

}
