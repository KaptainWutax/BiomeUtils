package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer;
import kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class ComputeLayer extends IntBiomeLayer {

	public ComputeLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	public ComputeLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
	}

	@Override
	public int sample(int x, int y, int z) {
		int value = this.getParent(IntBiomeLayer.class).get(x, y, z);
		return compute(value,x,z);
	}

	public abstract int compute(int value, int x, int z);

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName() + " " + x + " " + z + " " + xSize + " " + zSize + " : " + this.getScale());
		// this is a 1 to 1 mapping
		int[] parents = this.getParent(IntBiomeLayer.class).sample(x, y, z, xSize, ySize, zSize);
		int yz = ySize * zSize;
		int[] res = new int[xSize * yz];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					int pos = offsetX * yz + offsetY * zSize + offsetZ;
					res[pos] = compute(parents[pos], x + offsetX, z + offsetZ);
				}
			}
		}
		return res;
	}
}
