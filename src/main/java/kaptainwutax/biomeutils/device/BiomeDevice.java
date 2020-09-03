package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.LongConsumer;

public class BiomeDevice {

    private final MCVersion version;
    protected List<Restriction> restrictions = new ArrayList<>();

    public BiomeDevice(MCVersion version) {
        this.version = version;
    }

    public BiomeDevice add(Restriction restriction) {
        this.restrictions.add(restriction);
        return this;
    }

    public BiomeDevice add(Restriction.Factory<?> factory, int x, int z) {
        return this.add(factory.create(this.version, x, z));
    }

    public void findSeeds(LongConsumer onSeedFound) {
        List<BitGroup> groups = this.groupRestrictions();
        System.out.println(groups);

        BitGroup entry = groups.get(0);

        if(entry.bits < 64) {
            search(entry, 0, 0, onSeedFound);
        } else {
            long worldSeed = 0;

            do {
                boolean valid = true;

                for(BitGroup group: groups) {
                    if(!group.testSeed(worldSeed)) {
                        valid = false;
                        break;
                    }
                }

                if(valid && groups.get(groups.size() - 1).testSource(worldSeed)) {
                    onSeedFound.accept(worldSeed);
                }

                worldSeed++;
            } while(worldSeed != 0);
        }
    }

    public void search(BitGroup group, long baseSeed, int bits, LongConsumer onSeedFound) {
        long searchSpace = Mth.getPow2(group.bits - bits);
        System.out.println("[" + baseSeed + "] is good for the lowest " + bits + " bits! Lifting the next " + (group.bits - bits) + " bits...");

        for(long i = 0; i < searchSpace; i++) {
            long seed = baseSeed | (i << bits);

            if(!group.testSeed(seed))continue;

            if(group.next == null) {
                if(group.testSource(seed))onSeedFound.accept(seed);
            } else {
                this.search(group.next, seed, group.bits, onSeedFound);
            }
        }
    }

    private List<BitGroup> groupRestrictions() {
        Map<Integer, BitGroup> raw = new TreeMap<>(Integer::compare);

        for(Restriction restriction: this.restrictions) {
            for(int bitPoint: restriction.getBitPoints()) {
                raw.computeIfAbsent(bitPoint, i -> new BitGroup(i, new ArrayList<>())).restrictions.add(restriction);
            }
        }

        List<BitGroup> result = new ArrayList<>(raw.values());

        for(int i = 0; i < result.size() - 1; i++) {
            result.get(i).next = result.get(i + 1);
        }

        return result;
    }

    public static final class BitGroup {
        public final int bits;
        public final List<Restriction> restrictions;
        public BitGroup next;

        public BitGroup(int bits, List<Restriction> restrictions) {
            this.bits = bits;
            this.restrictions = restrictions;
        }

        public boolean testSeed(long seed) {
            for(Restriction restriction: this.restrictions) {
                if(!restriction.testSeed(seed, this.bits))return false;
            }

            return true;
        }

        public boolean testSource(long seed) {
            OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_16_2, seed);

            for(Restriction restriction: this.restrictions) {
                if(!restriction.testSource(source))return false;
            }

            return true;
        }

        @Override
        public String toString() {
            return "lift " + this.bits + " bits: " + this.restrictions;
        }
    }

}
