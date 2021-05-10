package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class ContinentLayer extends IntBiomeLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	public ContinentLayer(MCVersion version, long worldSeed, long salt) {
		super(version, worldSeed, salt);
	}

	@Override
	public int sample(int x, int y, int z) {
		xx.add(x);
		zz.add(z);
		if (minX==null) minX=x;
		if (minZ==null) minZ=z;
		if (maxX==null) maxX=x;
		if (maxZ==null) maxZ=z;
		minX=Math.min(minX,x);
		minZ=Math.min(minZ,z);
		maxX=Math.max(maxX,x);
		maxZ=Math.max(maxZ,z);
		return compute(x,z);
	}

	public int compute(int x,int z){
		this.setSeed(x, z);
		if (x == 0 && z == 0) return Biomes.PLAINS.getId();
		return this.nextInt(10) == 0 ? Biomes.PLAINS.getId() : Biomes.OCEAN.getId();
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName() + " " + x + " " + z + " " + xSize + " " + zSize + " : " + this.getScale());
		// this is a 0 to 1 mapping
		int yz = ySize * zSize;
		int[] res = new int[xSize * yz];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					int pos = offsetX * yz + offsetY * zSize + offsetZ;
					res[pos] = compute(x + offsetX, z + offsetZ);
				}
			}
		}
		return res;
	}
}
