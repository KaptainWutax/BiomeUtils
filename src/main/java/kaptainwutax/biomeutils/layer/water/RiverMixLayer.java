package kaptainwutax.biomeutils.layer.water;

import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class RiverMixLayer extends IntBiomeLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	public RiverMixLayer(MCVersion version, long worldSeed, long salt, IntBiomeLayer... parents) {
		super(version, worldSeed, salt, parents);
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
		int landStackCenter = this.getParent(0, IntBiomeLayer.class).get(x, y, z);
		int riverStackCenter = this.getParent(1, IntBiomeLayer.class).get(x, y, z);
		return compute(landStackCenter,riverStackCenter);
	}

	private int compute(int landStackCenter,int riverStackCenter){
		if (is1_6down.call()) {
			// Warning this is the only case like so because isOcean have Frozen ocean which is bypassed everywhere except here
			// thus we make this weird case, sorry about the confusion.
			if (landStackCenter == Biomes.OCEAN.getId()) return landStackCenter;
		} else if (Biome.isOcean(landStackCenter)) return landStackCenter;

		if (riverStackCenter == Biomes.RIVER.getId()) {
			if (is_beta_1_8_1down.call()){
				// FROZEN RIVER didn't exists back then nor SNOWY_TAIGA but let's not play with fire (98%)
				return riverStackCenter;
			}
			if (landStackCenter == Biomes.SNOWY_TUNDRA.getId()) {
				return Biomes.FROZEN_RIVER.getId();
			} else {
				return landStackCenter != Biomes.MUSHROOM_FIELDS.getId() && landStackCenter != Biomes.MUSHROOM_FIELD_SHORE.getId() ? riverStackCenter & 255 : Biomes.MUSHROOM_FIELD_SHORE.getId();
			}
		}

		return landStackCenter;
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName()+" "+x+" "+z+" "+xSize+" "+zSize+" : "+this.getScale());
		// this is a 1 to 1 mapping
		int[] lands=this.getParent(0, IntBiomeLayer.class).sample(x,y,z,xSize,ySize,zSize);
		int[] rivers=this.getParent(1, IntBiomeLayer.class).sample(x,y,z,xSize,ySize,zSize);
		int yz=ySize*zSize;
		int[] res=new int[xSize*yz];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					int pos=offsetX*yz+offsetY*zSize+offsetZ;
					res[pos]=compute(lands[pos],rivers[pos]);
				}
			}
		}
		return res;
	}

}
