package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.land.LandLayer;
import kaptainwutax.biomeutils.layer.land.MushroomLayer;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class XCrossLayer extends IntBiomeLayer {

	public XCrossLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		if (this instanceof MushroomLayer) {
			//System.out.println(x+" "+z);
			MushroomLayer.xx.add(x);
			MushroomLayer.zz.add(z);
			if (MushroomLayer.minX == null) MushroomLayer.minX = x;
			if (MushroomLayer.minZ == null) MushroomLayer.minZ = z;
			if (MushroomLayer.maxX == null) MushroomLayer.maxX = x;
			if (MushroomLayer.maxZ == null) MushroomLayer.maxZ = z;
			MushroomLayer.minX = Math.min(MushroomLayer.minX, x);
			MushroomLayer.minZ = Math.min(MushroomLayer.minZ, z);
			MushroomLayer.maxX = Math.max(MushroomLayer.maxX, x);
			MushroomLayer.maxZ = Math.max(MushroomLayer.maxZ, z);
		}

		if (this instanceof LandLayer && this.hasParent() && this.salt == 1) {
			LandLayer.xx.add(x);
			LandLayer.zz.add(z);
			if (LandLayer.minX == null) LandLayer.minX = x;
			if (LandLayer.minZ == null) LandLayer.minZ = z;
			if (LandLayer.maxX == null) LandLayer.maxX = x;
			if (LandLayer.maxZ == null) LandLayer.maxZ = z;
			LandLayer.minX = Math.min(LandLayer.minX, x);
			LandLayer.minZ = Math.min(LandLayer.minZ, z);
			LandLayer.maxX = Math.max(LandLayer.maxX, x);
			LandLayer.maxZ = Math.max(LandLayer.maxZ, z);
		}
		this.setSeed(x, z);
		if (DEBUG)System.out.println(x + " " + z);

		int sw = this.getParent(IntBiomeLayer.class).get(x - 1, y, z + 1);
		int se = this.getParent(IntBiomeLayer.class).get(x + 1, y, z + 1);
		int ne = this.getParent(IntBiomeLayer.class).get(x + 1, y, z - 1);
		int nw = this.getParent(IntBiomeLayer.class).get(x - 1, y, z - 1);
		int center = this.getParent(IntBiomeLayer.class).get(x, y, z);
		if (DEBUG)System.out.println(sw + " " + se + " " + ne + " " + nw + " " + center);
		return this.sample(sw, se, ne, nw, center);
	}

	public int apply(int[] arr, int posX, int posY, int posZ, int dimY, int dimZ, CrossLayer.Sampler sampler) {
		// we assume you did correctly the bound and that no operation here will throw an error
		// we don't want to make the overhead of bound checking
		// WARNING hard fail if so
		int dimYZ = dimY * dimZ;
		int pos = posX * dimYZ + posY * dimZ + posZ;
		int sw = arr[pos + 1 - dimYZ];
		int se = arr[pos + dimYZ + 1];
		int ne = arr[pos + dimYZ - 1];
		int nw = arr[pos - dimYZ - 1];
		int center = arr[pos];
		if (DEBUG)System.out.println(sw + " " + se + " " + ne + " " + nw + " " + center);
		return sampler.sample(sw, se, ne, nw, center);
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName() + " " + x + " " + z + " " + xSize + " " + zSize + " : " + this.getScale());
		int newXSize = xSize + 2;
		int newZSize = zSize + 2;
		int[] parents = this.getParent(IntBiomeLayer.class).sample(x - 1, y, z - 1, newXSize, ySize, newZSize);
		int yz = ySize * zSize;
		if (DEBUG)System.out.println(newXSize+" "+newZSize);
		int[] res = new int[xSize * yz];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					int pos = offsetX * yz + offsetY * zSize + offsetZ;
					this.setSeed(x + offsetX, z + offsetZ);
					if (DEBUG)System.out.println((x+offsetX)+" "+(z+offsetZ));
					res[pos] = this.apply(parents, offsetZ + 1, offsetY, offsetX + 1, ySize, newZSize, this::sample);
				}
			}
		}
		return res;
	}


	public abstract int sample(int sw, int se, int ne, int nw, int center);

}
