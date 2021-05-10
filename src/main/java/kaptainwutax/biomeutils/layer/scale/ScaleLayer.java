package kaptainwutax.biomeutils.layer.scale;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.HashSet;
import java.util.Set;

public class ScaleLayer extends IntBiomeLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	private final Type type;

	public ScaleLayer(MCVersion version, long worldSeed, long salt, Type type, IntBiomeLayer parent) {
		super(version, worldSeed, salt, parent);
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	@Override
	public int sample(int x, int y, int z) {
		if (this.hasParent() && this.getParent() instanceof EdgeBiomesLayer) {
			//if (this.getParent().hasParent() && this.getParent().getParent() instanceof EdgeBiomesLayer) {
				//if (this.getParent().getParent().hasParent() && this.getParent().getParent().getParent() instanceof EdgeBiomesLayer) {
					ScaleLayer.xx.add(x);
					ScaleLayer.zz.add(z);
					if (ScaleLayer.minX == null) ScaleLayer.minX = x;
					if (ScaleLayer.minZ == null) ScaleLayer.minZ = z;
					if (ScaleLayer.maxX == null) ScaleLayer.maxX = x;
					if (ScaleLayer.maxZ == null) ScaleLayer.maxZ = z;
					ScaleLayer.minX = Math.min(ScaleLayer.minX, x);
					ScaleLayer.minZ = Math.min(ScaleLayer.minZ, z);
					ScaleLayer.maxX = Math.max(ScaleLayer.maxX, x);
					ScaleLayer.maxZ = Math.max(ScaleLayer.maxZ, z);
				//}
			//}
		}
		IntBiomeLayer parent = this.getParent(IntBiomeLayer.class);

		int center = parent.get(x >> 1, y, z >> 1);
		this.setSeed(x & -2, z & -2);
		int xb = x & 1, zb = z & 1;

		if (xb == 0 && zb == 0) return center;

		int s = parent.get(x >> 1, y, (z + 1) >> 1);
		int zPlus = this.choose(center, s);

		if (xb == 0) return zPlus;

		int e = parent.get((x + 1) >> 1, y, z >> 1);
		int xPlus = this.choose(center, e);

		if (zb == 0) return xPlus;

		int se = parent.get((x + 1) >> 1, y, (z + 1) >> 1);
		return this.sample(center, e, s, se);
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName()+" "+x+" "+z+" "+xSize+" "+zSize+" : "+this.getScale());
		int[] parents=this.getParent( IntBiomeLayer.class).sample(x>>1,y,z>>1,(xSize>>1)+1,ySize,(zSize>>1)+1);
		int yz=ySize*zSize;
		int[] res=new int[xSize*yz];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					int pos=offsetX*yz+offsetY*zSize+offsetZ;

				}
			}
		}
		return res;
	}

	public int sample(int center, int e, int s, int se) {
		int ret = this.choose(center, e, s, se);

		if (this.type == Type.FUZZY) {
			return ret;
		}

		if (e == s && e == se) return e;
		if (center == e && s != se) return center;
		if (center == s && e != se) return center;
		if (center == se && e != s) return center;
		if (e == s && center != se) return e;
		if (e == se && center != s) return e;
		if (s == se && center != e) return s;
		return ret;
	}

	// ((center == s && center == se) ||
	// (center == s && center == e) ||
	// (center == e && center == se) ||
	// (center == s && e != se) ||
	// (center == e && s != se) ||
	// (center == se && s != e))
	// with the precondition s!=se if e==s or e==se


	public enum Type {
		NORMAL, FUZZY
	}

}
