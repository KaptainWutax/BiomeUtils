package kaptainwutax.biomeutils.device;

/**
 * An example of the method for the specific case of finding patterns of mushroom islands. Some things,
 * like the layerseed will be hardcoded which should eventually be computed dynamically
 * depending on which layer and nextInt call the user wishes to target.
 */

public class MushroomCondition {

	final static long SHROOM_LAYER_SEED = -7479281634960481323L; //must be computed for each layer, maybe a LUT?
	final static long EPSILON = (1L << 32);
	final static long MASK = (1L << 32) - 1;
	final static int ARG = 299999; //the arg to nextInt, eventually this will need to be passed as a parameter
	final static int TARGET = 0; // the  target value of nextInt, eventually this should support ranges and shouldn't be hardcoded

	private final long upperSeedMultiplier;
	private final long upperSeedAddend;
	private final long numValidSeeds;
	private final long lower32Bits;
	private final int x;
	private final int z;

	public MushroomCondition(long lower32Bits, int x, int z) {
		this.x = x;
		this.z = z;
		this.lower32Bits = lower32Bits & MASK; //mask for safety

		//these next two lines can be computed for about the cost of one getShroomSeed call at the very least, but lazy
		long mushAddend = getShroomSeed(lower32Bits, x, z); //Here could be a check for safety that addend is 0 mod 4.
		long mushMultiplier = getShroomSeed(this.lower32Bits + EPSILON, x, z) - mushAddend;

		//4 here is the GCD of ARG and 1L << 32;
		int argNoEven = ARG >> Long.numberOfTrailingZeros(ARG);

		//increasing our seed by seedMultiplier leaves our value of nextInt unchanged for the most part
		upperSeedMultiplier = argNoEven * inverse32Bit(mushMultiplier >>> 32);

		long inverse = modInverse((EPSILON >> 24), argNoEven);
		long needed = (TARGET - (((mushAddend & MASK) >>> 24) % argNoEven) + argNoEven) % argNoEven; //most of this crap is cuz % scares me

		long lowestValidMushSeed = (inverse * needed % argNoEven) * EPSILON + (mushAddend & MASK);
		// soSystem.out.println((lowestValidMushSeed >>> 24) % 100);
		upperSeedAddend = (((lowestValidMushSeed - mushAddend) >>> 32) * inverse32Bit(mushMultiplier >>> 32)) & MASK; //reusing stuff I know oops
		numValidSeeds = (EPSILON - (lowestValidMushSeed >>> 32)) / argNoEven;
	}

	private long inverse32Bit(long a) {
		if (a % 2 == 0)
			System.err.println("a is not invertible");
		long x = (((a << 2) ^ (a << 1)) & 8) ^ a; // this can be removed and replaced with one more copy of the later lines
		//4 bits known
		x += x - a * x * x; //8 bits known
		x += x - a * x * x; //16 bits known
		x += x - a * x * x; //32 bits known
		//x += x - a * x * x; //64 bits known
		return x & MASK; // we only want inverse mod 2^32
	}

	//stolen, will replace with faster version I write eventually
	private long modInverse(long a, long m)
	{
		long m0 = m;
		long y = 0, x = 1;

		if (m == 1)
			return 0;

		while (a > 1)
		{
			long q = a / m;
			long t = m;
			m = a % m;
			a = t;
			t = y;
			y = x - q * y;
			x = t;
		}
		if (x < 0)
			x += m0;

		return x;
	}

	/**
	 * A method to get an important intermediate value used to select mushroom areas
	 * @param ws the world seed
	 * @param x the x coordinate to be checked (scale = 256)
	 * @param z the z coordinate to be checked (scale = 256)
	 * @return a number used to choose mushroom islands. If the returned value (retval >> 24) % 100 == 0
	 */
	static long getShroomSeed(long ws, int x, int z) {
		long shroom_layer_seed = SHROOM_LAYER_SEED;
		ws *= ws * 6364136223846793005L + 1442695040888963407L;
		ws += shroom_layer_seed;
		ws *= ws * 6364136223846793005L + 1442695040888963407L;
		ws += shroom_layer_seed;
		ws *= ws * 6364136223846793005L + 1442695040888963407L;
		ws += shroom_layer_seed;
		ws *= ws * 6364136223846793005L + 1442695040888963407L;

		long ss=ws;
		ss += x;
		ss *= ss * 6364136223846793005L + 1442695040888963407L;
		ss += z;
		ss *= ss * 6364136223846793005L + 1442695040888963407L;
		ss += x;
		ss *= ss * 6364136223846793005L + 1442695040888963407L;
		ss += z;
		return ss; //in MC code this returns ss >> 24, my code is clearer if we consider the whole value
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public long getNumValidSeeds() {
		return numValidSeeds;
	}

	public long getUpperSeedMultiplier() {
		return upperSeedMultiplier;
	}

	public long getUpperSeedAddend() {
		return upperSeedAddend;
	}

	public long getLower32Bits() {
		return lower32Bits;
	}

	public static void main(String[] args) {
		MushroomCondition c = new MushroomCondition(10,0,0);
		System.out.println(c.getUpperSeedAddend());
		System.out.println(c.getUpperSeedMultiplier());
		System.out.println(c.getNumValidSeeds());

		for(long lowerBits = 0; lowerBits < 1L << 32; lowerBits++) {
			c = new MushroomCondition(lowerBits,0,0);

			for (long i = 0; i < c.getNumValidSeeds(); i++) {
				long seed = MushroomCondition.getShroomSeed(((c.getUpperSeedAddend()+i*c.getUpperSeedMultiplier()) << 32) + 10,0,0);
				//System.out.println(seed + ": " + (seed >>> 24) % ARG);
			}
		}
	}
}
