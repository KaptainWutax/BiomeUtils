package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer;
import kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer;
import kaptainwutax.mcutils.version.MCVersion;

public abstract class CrossLayer extends IntBiomeLayer {

	public CrossLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
	}

	@Override
	public int sample(int x, int y, int z) {
		if (this instanceof SmoothScaleLayer) {
			SmoothScaleLayer.xx.add(x);
			SmoothScaleLayer.zz.add(z);
			if (SmoothScaleLayer.minX == null) SmoothScaleLayer.minX = x;
			if (SmoothScaleLayer.minZ == null) SmoothScaleLayer.minZ = z;
			if (SmoothScaleLayer.maxX == null) SmoothScaleLayer.maxX = x;
			if (SmoothScaleLayer.maxZ == null) SmoothScaleLayer.maxZ = z;
			SmoothScaleLayer.minX = Math.min(SmoothScaleLayer.minX, x);
			SmoothScaleLayer.minZ = Math.min(SmoothScaleLayer.minZ, z);
			SmoothScaleLayer.maxX = Math.max(SmoothScaleLayer.maxX, x);
			SmoothScaleLayer.maxZ = Math.max(SmoothScaleLayer.maxZ, z);
		}
		if (this instanceof NoiseToRiverLayer) {
			NoiseToRiverLayer.xx.add(x);
			NoiseToRiverLayer.zz.add(z);
			if (NoiseToRiverLayer.minX == null) NoiseToRiverLayer.minX = x;
			if (NoiseToRiverLayer.minZ == null) NoiseToRiverLayer.minZ = z;
			if (NoiseToRiverLayer.maxX == null) NoiseToRiverLayer.maxX = x;
			if (NoiseToRiverLayer.maxZ == null) NoiseToRiverLayer.maxZ = z;
			NoiseToRiverLayer.minX = Math.min(NoiseToRiverLayer.minX, x);
			NoiseToRiverLayer.minZ = Math.min(NoiseToRiverLayer.minZ, z);
			NoiseToRiverLayer.maxX = Math.max(NoiseToRiverLayer.maxX, x);
			NoiseToRiverLayer.maxZ = Math.max(NoiseToRiverLayer.maxZ, z);
		}
		this.setSeed(x, z);

		return this.sample(
				this.getParent(IntBiomeLayer.class).get(x, y, z - 1),
				this.getParent(IntBiomeLayer.class).get(x + 1, y, z),
				this.getParent(IntBiomeLayer.class).get(x, y, z + 1),
				this.getParent(IntBiomeLayer.class).get(x - 1, y, z),
				this.getParent(IntBiomeLayer.class).get(x, y, z)
		);
	}

	public int apply(int[] arr, int posX, int posY, int posZ, int dimY, int dimZ, Sampler sampler) {
		// we assume you did correctly the bound and that no operation here will throw an error
		// we don't want to make the overhead of bound checking
		// WARNING hard fail if so
		int dimYZ=dimY*dimZ;
		int pos=posX*dimYZ+posY*dimZ+posZ;
		return sampler.sample(arr[pos-1],arr[pos+dimYZ],arr[pos+1],arr[pos-dimYZ],arr[pos]);
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName()+" "+x+" "+z+" "+xSize+" "+zSize+" : "+this.getScale());
		int newXSize=xSize+2;
		int newZSize=zSize+2;
		int[] parents=this.getParent(IntBiomeLayer.class).sample(x-1,y,z-1,newXSize,ySize,newZSize);
		int yz=ySize*zSize;
		int[] res=new int[xSize*yz];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					int pos=offsetX*yz+offsetY*zSize+offsetZ;
					res[pos]=this.apply(parents,offsetX+1,offsetY,offsetZ+1,ySize,newZSize,this::sample);
				}
			}
		}
		return res;
	}



	public abstract int sample(int n, int e, int s, int w, int center);

	@FunctionalInterface
	public interface Sampler {
		int sample(int n, int e, int s, int w, int center);
	}
}
