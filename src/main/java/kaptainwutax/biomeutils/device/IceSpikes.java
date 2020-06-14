package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.pos.BPos;

public class IceSpikes extends BiomeRestriction {

	protected BPos min;
	protected BPos max;
	protected Region minRegionSmall;
	protected Region maxRegionSmall;
	protected Region minRegionLarge;
	protected Region maxRegionLarge;

	protected IceSpikes(BPos min, BPos max) {
		this.min = min;
		this.max = max;
		this.minRegionSmall = this.getRegion(min.getX(), min.getZ(), 256).add(-1, -1);
		this.maxRegionSmall = this.getRegion(max.getX(), max.getZ(), 256).add(1, 1);
		this.minRegionLarge = this.getRegion(min.getX(), min.getZ(), 1024).add(-1, -1);
		this.maxRegionLarge = this.getRegion(max.getX(), max.getZ(), 1024).add(1, 1);
	}

	public static IceSpikes at(int blockX, int blockZ) {
		return new IceSpikes(new BPos(blockX, 0, blockZ), new BPos(blockX, 0, blockZ));
	}

	public static IceSpikes around(int blockX, int blockZ, int radius) {
		return new IceSpikes(
				new BPos(blockX - radius, 0, blockZ + radius),
				new BPos(blockX + radius, 0, blockZ + radius)
		);
	}

	@Override
	public boolean testSeed(long worldSeed) {
		if(!this.testNoise(worldSeed))return false;
		if(!this.testCold(worldSeed))return false;
		if(!this.testBase(worldSeed))return false;
		return true;
	}

	public boolean testCold(long worldSeed) {
		long layerSeed = BiomeLayer.getLayerSeed(worldSeed, 2L);

		for(int regionX = this.minRegionLarge.getX(); regionX <= this.maxRegionLarge.getX(); regionX++) {
			for(int regionZ = this.minRegionLarge.getZ(); regionZ <= this.maxRegionLarge.getZ(); regionZ++) {
				long localSeed = BiomeLayer.getLocalSeed(layerSeed, regionX, regionZ);
				if((int)Math.floorMod(localSeed >> 24, 6) == 0)return true;
			}
		}

		return false;
	}

	public boolean testBase(long worldSeed) {
		long layerSeed = BiomeLayer.getLayerSeed(worldSeed, 200L);

		for(int regionX = this.minRegionSmall.getX(); regionX <= this.maxRegionSmall.getX(); regionX++) {
			for(int regionZ = this.minRegionSmall.getZ(); regionZ <= this.maxRegionSmall.getZ(); regionZ++) {
				long localSeed = BiomeLayer.getLocalSeed(layerSeed, regionX, regionZ);
				if((int)Math.floorMod(localSeed >> 24, 4) != 3)return true;
			}
		}

		return false;
	}

	public boolean testNoise(long worldSeed) {
		long layerSeed = BiomeLayer.getLayerSeed(worldSeed, 100L);

		for(int regionX = this.minRegionSmall.getX(); regionX <= this.maxRegionSmall.getX(); regionX++) {
			for(int regionZ = this.minRegionSmall.getZ(); regionZ <= this.maxRegionSmall.getZ(); regionZ++) {
				long localSeed = BiomeLayer.getLocalSeed(layerSeed, regionX, regionZ);
				if((int)Math.floorMod(localSeed >> 24, 299999) % 29 == 1)return true;
			}
		}

		return false;
	}

	@Override
	public boolean testSource(BiomeSource source) {
		for(int x = this.min.getX(); x <= this.max.getX(); x++) {
			for(int z = this.min.getZ(); z <= this.max.getZ(); z++) {
				if(source.getBiome(x, 0, z) == Biome.ICE_SPIKES) {
					return true;
				}
			}
		}

		return false;
	}

}
