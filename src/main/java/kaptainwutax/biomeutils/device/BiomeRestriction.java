package kaptainwutax.biomeutils.device;

import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.util.math.Vec3i;

public abstract class BiomeRestriction {

	public abstract boolean testSeed(long worldSeed);

	public abstract boolean testSource(BiomeSource source);

	protected Region getRegion(int blockX, int blockZ, int scale) {
		blockX = blockX < 0 ? blockX - scale + 1 : blockX;
		blockZ = blockZ < 0 ? blockZ - scale + 1 : blockZ;
		return new Region(blockX / scale, blockZ / scale);
	}

	public static class Region extends Vec3i {
		protected Region(int x, int z) {
			super(x, 0, z);
		}

		public Region add(int x, int z) {
			return new Region(this.getX() + x, this.getZ() + z);
		}
	}

}
