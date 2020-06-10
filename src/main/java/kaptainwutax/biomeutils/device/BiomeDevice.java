package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class BiomeDevice {

	protected List<BiomeRestriction> restrictions = new ArrayList<>();

	public BiomeDevice restrict(BiomeRestriction call) {
		this.restrictions.add(call);
		return this;
	}

	public LongStream findSeeds(long min, long max) {
		return LongStream.range(min, max).filter(this::testSeed);
	}

	private boolean testSeed(long worldSeed) {
		for(BiomeRestriction call: this.restrictions) {
			if(!call.testSeed(worldSeed))return false;
		}

		OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_15, worldSeed).build();

		for(BiomeRestriction call: this.restrictions) {
			if(!call.testSource(source))return false;
		}

		return true;
	}

}
