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
		this.minRegionSmall = this.getRegion(min.getX(), min.getZ(), 256);
		this.maxRegionSmall = this.getRegion(max.getX(), max.getZ(), 256).add(1, 1);
		this.minRegionLarge = this.getRegion(min.getX(), min.getZ(), 1024);
		this.maxRegionLarge = this.getRegion(max.getX(), max.getZ(), 1024).add(1, 1);
	}

	public static IceSpikes at(int blockX, int blockZ) {
		return new IceSpikes(new BPos(blockX, 0, blockZ), new BPos(blockX, 0, blockZ));
	}

	@Override
	public boolean testSeed(long worldSeed) {
		long layerSeed, localSeed;

		boolean valid = false;

		for(int regionX = this.minRegionLarge.getX(); regionX <= this.maxRegionLarge.getX(); regionX++) {
			for(int regionZ = this.minRegionLarge.getZ(); regionZ <= this.maxRegionLarge.getZ(); regionZ++) {
				layerSeed = BiomeLayer.getLayerSeed(worldSeed, 2L);
				localSeed = BiomeLayer.getLocalSeed(layerSeed, 0, 0);
				if((int)Math.floorMod(localSeed >> 24, 6) != 0)continue;
				valid = true;
				break;
			}

			if(valid)break;
		}

		if(!valid)return false;
		valid = false;

		for(int regionX = this.minRegionSmall.getX(); regionX <= this.maxRegionSmall.getX(); regionX++) {
			for(int regionZ = this.minRegionSmall.getZ(); regionZ <= this.maxRegionSmall.getZ(); regionZ++) {
				layerSeed = BiomeLayer.getLayerSeed(worldSeed, 200L);
				localSeed = BiomeLayer.getLocalSeed(layerSeed, 0, 0);
				if((int)Math.floorMod(localSeed >> 24, 4) == 3)continue;

				layerSeed = BiomeLayer.getLayerSeed(worldSeed, 100L);
				localSeed = BiomeLayer.getLocalSeed(layerSeed, 0, 0);
				if(((int)Math.floorMod(localSeed >> 24, 299999) % 29) != 1)continue;
				valid = true;
				break;
			}

			if(valid)break;
		}

		if(!valid)return false;
		return true;
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
