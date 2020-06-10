package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.pos.BPos;

public class Mushroom extends BiomeRestriction {

	protected BPos min;
	protected BPos max;
	protected Region minRegion;
	protected Region maxRegion;

	protected Mushroom(BPos min, BPos max) {
		this.min = min;
		this.max = max;
		this.minRegion = this.getRegion(min.getX(), min.getZ(), 256).add(0, 0);
		this.maxRegion = this.getRegion(max.getX(), max.getZ(), 256).add(1, 1);
	}

	public static Mushroom at(int blockX, int blockZ) {
		return new Mushroom(new BPos(blockX, 0, blockZ), new BPos(blockX, 0, blockZ));
	}

	@Override
	public boolean testSeed(long worldSeed) {
		long layerSeed = BiomeLayer.getLayerSeed(worldSeed, 5L);

		for(int regionX = this.minRegion.getX(); regionX <= this.maxRegion.getX(); regionX++) {
			for(int regionZ = this.minRegion.getZ(); regionZ <= this.maxRegion.getZ(); regionZ++) {
				long localSeed = BiomeLayer.getLocalSeed(layerSeed, regionX, regionZ);
				int i = (int)Math.floorMod(localSeed >> 24, 100);
				if(i == 0)return true;
			}
		}

		return false;
	}

	@Override
	public boolean testSource(BiomeSource source) {
		for(int x = this.min.getX(); x <= this.max.getX(); x++) {
			for(int z = this.min.getZ(); z <= this.max.getZ(); z++) {
				if(source.getBiome(x, 0, z).getCategory() == Biome.Category.MUSHROOM) {
					return true;
				}
			}
		}

		return false;
	}

}
