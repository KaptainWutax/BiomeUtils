package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.IntBiomeLayer;
import kaptainwutax.mcutils.rand.seed.SeedMixer;
import kaptainwutax.mcutils.rand.seed.WorldSeed;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class VoronoiLayer extends IntBiomeLayer {
	public static Integer minX=null;
	public static Integer maxX=null;
	public static Integer minZ=null;
	public static Integer maxZ=null;
	public static Set<Integer> xx=new HashSet<>();
	public static Set<Integer> zz=new HashSet<>();
	private final long seed;
	private final boolean is3D;

	public VoronoiLayer(MCVersion version, long worldSeed, boolean is3D, IntBiomeLayer parent) {
		super(version, worldSeed, version.isOlderThan(MCVersion.v1_15) ? 10L : 0L, parent);
		this.seed = version.isOlderThan(MCVersion.v1_15) ? worldSeed : WorldSeed.toHash(worldSeed);
		this.is3D = is3D;
	}

	public long getSeed() {
		return this.seed;
	}

	public boolean is3D() {
		return this.is3D;
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
		int[] coords=getCoords(x,y,z);
		return this.getParent(IntBiomeLayer.class).get(coords[0],coords[1],coords[2]);
	}

	public int[] getCoords(int x,int y, int z){
		return this.getVersion().isOlderThan(MCVersion.v1_15) ? this.sample14minus(x, z) : this.sample15plus(x, y, z);
	}

	@Override
	public int[] sample(int x, int y, int z, int xSize, int ySize, int zSize) {
		System.out.println(this.getClass().getName()+" "+x+" "+z+" "+xSize+" "+zSize+" : "+this.getScale());
		// TODO OPTIMIZE THE HELL OF THIS
		int[] coords=getCoords(x,y,z);
		int minX=coords[0];
		int minY=coords[1];
		int minZ=coords[2];
		int maxX=coords[0];
		int maxY=coords[1];
		int maxZ=coords[2];
		for (int offsetX = 0; offsetX < xSize; offsetX++) {
			for (int offsetY = 0; offsetY < ySize; offsetY++) {
				for (int offsetZ = 0; offsetZ < zSize; offsetZ++) {
					coords=getCoords(x+offsetX,y+offsetY,z+offsetZ);
					minX=Math.min(coords[0],minX);
					minY=Math.min(coords[1],minY);
					minZ=Math.min(coords[2],minZ);
					maxX=Math.max(coords[0],maxX);
					maxY=Math.max(coords[1],maxY);
					maxZ=Math.max(coords[2],maxZ);
				}
			}
		}
		this.getParent(IntBiomeLayer.class).sample(minX,minY,minZ,maxX-minX+1,maxY-minY+1,maxZ-minZ+1);
		return new int[xSize*ySize*zSize];
	}

	private int[] sample14minus(int x, int z) {
		int offset;
		x -= 2;
		z -= 2;
		int pX = x >> 2;
		int pZ = z >> 2;
		int sX = pX << 2;
		int sZ = pZ << 2;
		double[] off_0_0 = calcOffset(this.layerSeed, sX, sZ, 0, 0);
		double[] off_1_0 = calcOffset(this.layerSeed, sX, sZ, 4, 0);
		double[] off_0_1 = calcOffset(this.layerSeed, sX, sZ, 0, 4);
		double[] off_1_1 = calcOffset(this.layerSeed, sX, sZ, 4, 4);

		int cell = (z & 3) * 4 + (x & 3);
		double corner0 = calcContribution(off_0_0, cell >> 2, cell & 3);
		double corner1 = calcContribution(off_1_0, cell >> 2, cell & 3);
		double corner2 = calcContribution(off_0_1, cell >> 2, cell & 3);
		double corner3 = calcContribution(off_1_1, cell >> 2, cell & 3);
		if (corner0 < corner1 && corner0 < corner2 && corner0 < corner3) {
			offset = 0;
		} else if (corner1 < corner0 && corner1 < corner2 && corner1 < corner3) {
			offset = 1;
		} else if (corner2 < corner0 && corner2 < corner1 && corner2 < corner3) {
			offset = 2;
		} else {
			offset = 3;
		}


		//  X -> (offset&1)
		// _________
		// | 0 | 1 |   Z (offset>>1)
		// |---|---|   |
		// | 2 | 3 |  \_/
		// |___|___|
		if (DEBUG) {
			System.out.println("VORONOI Coords: (x,z): " + (pX + (offset & 1)) + " " + (pZ + (offset >> 1)));
		}
		return new int[]{pX + (offset & 1), 0, pZ + (offset >> 1)};
	}

	private int[] sample15plus(int x, int y, int z) {
		x-=2;
		y-=2;
		z-=2;
		int pX = x >> 2;
		int pY = y >> 2;
		int pZ = z >> 2;
		double sX = (double) (x & 3) / 4.0D;
		double sY = (double) (y & 3) / 4.0D;
		double sZ = (double) (z & 3) / 4.0D;
		double[] cells = new double[8];

		for (int cellId = 0; cellId < 8; ++cellId) {
			boolean highBit = (cellId & 4) == 0;
			boolean midBit = (cellId & 2) == 0;
			boolean lowBit = (cellId & 1) == 0;
			int xx = highBit ? pX : pX + 1;
			int yy = midBit ? pY : pY + 1;
			int zz = lowBit ? pZ : pZ + 1;
			double xFrac = highBit ? sX : sX - 1.0D;
			double yFrac = midBit ? sY : sY - 1.0D;
			double zFrac = lowBit ? sZ : sZ - 1.0D;
			cells[cellId] = calcSquaredDistance(this.seed, xx, yy, zz, xFrac, yFrac, zFrac);
		}

		int index = 0;
		double min = cells[0];

		for (int cellId = 1; cellId < 8; ++cellId) {
			if (cells[cellId] >= min) continue;
			index = cellId;
			min = cells[cellId];
		}

		int xFinal = (index & 4) == 0 ? pX : pX + 1;
		int yFinal = (index & 2) == 0 ? pY : pY + 1;
		int zFinal = (index & 1) == 0 ? pZ : pZ + 1;
		if (DEBUG) {
			System.out.printf("Voronoi coords (x,y,z):(%d,%d,%d)%n", xFinal, yFinal, zFinal);
		}
		return  new int[]{xFinal, this.is3D ? yFinal : 0, zFinal};
	}

	private static double calcContribution(double[] d, int x, int z) {
		return ((double) x - d[1]) * ((double) x - d[1]) + ((double) z - d[0]) * ((double) z - d[0]);
	}

	private static double[] calcOffset(long layerSeed, int x, int z, int offX, int offZ) {
		long mixedSeed = SeedMixer.mixSeed(layerSeed, x + offX);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, z + offZ);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, x + offX);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, z + offZ);
		double d1 = (((double) ((int) Math.floorMod(mixedSeed >> 24, 1024L)) / 1024.0D) - 0.5D) * 3.6D + offX;
		mixedSeed = SeedMixer.mixSeed(mixedSeed, layerSeed);
		double d2 = (((double) ((int) Math.floorMod(mixedSeed >> 24, 1024L)) / 1024.0D) - 0.5D) * 3.6D + offZ;
		return new double[] {d1, d2};
	}

	private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
		long mixedSeed = SeedMixer.mixSeed(seed, x);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, y);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, x);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, y);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, z);
		double d = distribute(mixedSeed);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, seed);
		double e = distribute(mixedSeed);
		mixedSeed = SeedMixer.mixSeed(mixedSeed, seed);
		double f = distribute(mixedSeed);
		return square(zFraction + f) + square(yFraction + e) + square(xFraction + d);
	}

	private static double distribute(long seed) {
		double d = (double) ((int) Math.floorMod(seed >> 24, 1024L)) / 1024.0D;
		return (d - 0.5D) * 0.9D;
	}

	private static double square(double d) {
		return d * d;
	}

}
