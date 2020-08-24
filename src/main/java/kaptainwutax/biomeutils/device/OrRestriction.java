package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.source.BiomeSource;

import java.util.*;

public class OrRestriction extends Restriction {

    private final Restriction[] restrictions;

    protected OrRestriction(int x, int z, Restriction... restrictions) {
        super(x, z);
        this.restrictions = restrictions;
    }

    public static Restriction.Factory<OrRestriction> of(Restriction.Factory<?>... restrictions) {
        return (version, x, z) -> new OrRestriction(x, z, Arrays.stream(restrictions).map(f -> f.create(version, x, z)).toArray(Restriction[]::new));
    }

    @Override
    public List<Integer> getBitPoints() {
        Set<Integer> points = new HashSet<>();

        for(Restriction restriction: this.restrictions) {
            points.addAll(restriction.getBitPoints());
        }

        return new ArrayList<>(points);
    }

    @Override
    public boolean testSeed(long seed, long bits) {
        for(Restriction restriction: this.restrictions) {
            if(restriction.testSeed(seed, bits))return true;
        }

        return false;
    }

    @Override
    public boolean testSource(BiomeSource source) {
        for(Restriction restriction: this.restrictions) {
            if(restriction.testSource(source))return true;
        }

        return false;
    }

}
