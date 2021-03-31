# BiomeUtils

This library aims to give easy access to the different biomes generations of Minecraft. It is not direct copy of Minecraft
source code as it has been rewritten from the ground up with optimizations in mind.

We support release 1.0 to release 1.16.5 (no snapshot support)

------

# A few words
Before using the library, I want to say a few words about OverWorld biome generation in Minecraft. Currently, as it has been 
implemented, the biome generation is split up in layers which are stacked together to form the biome generator.

This generator can be split in 4 different part, there is first the legacy stack which starts from `ContinentLayer`
and ends up at `DeepOceanLayer`.

Then there is a noise layer (`NoiseLayer` scaled 2 times) which is fed into 2 different stack, one for adding
*new* biomes (starting at `BaseBiomesLayer` and ending at `SmoothScaleLayer`) and the other for the rivers (starting
at `NoiseToRiverLayer` and ending at `SmoothScaleLayer`). 

The two split stacks are merged inside `RiverLayer` and the last stack with `OceanTemperatureLayer` (for 1.13+) is then initialized,
scaled 6 times and merged again with the rest.

At this point the biome generator give the biomes at 1/4 the scale of what you would encounter, a last layer is 
applied, called `VoronoiLayer` which takes since 1.15 the sha2 hash of the world seed to initialize it.
This means that this last layer can be done on the client side and save both on server performance and bandwidth.

For Nether and End biome generation, Simplex and Perlin noise are used, thus they only use the 48 lower bits of the seed
compared to the OverWorld.

------

# How to use

**Here vXXX means any version starting from v1_0 (for the moment, we are extending support) till current supported one.**

Before anything, we recommend using gradle buildsystem to import the library as such:
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation ('com.github.KaptainWutax:BiomeUtils:-SNAPSHOT'){
        transitive = false
    }
    implementation ('com.github.KaptainWutax:SeedUtils:-SNAPSHOT'){
        transitive = false
    }
}
```


To use the library, you have a few possible endpoints depending on your usage:

- If you need simply OverWorld biomes then you use it as follows
  - `OverworldBiomeSource#getBiome` will give you the biome as scale 1/1
  - `OverworldBiomeSource#getBiomeForNoiseGen` will give you the biome as scale 1/4 (aka pre-Voronoi)
  - `OverworldBiomeSource.(base|ocean|noise|variants|biomes|river|full|voronoi)#get` are entry points for different level 
  in the generator.

The common approach:     
```java
import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
OverworldBiomeSource biomeSource = new OverworldBiomeSource(MCVersion.vXXX, seed);
Biome biome=biomeSource.getBiome(x,y,z); // here y is always 0 no matter what you pass
```

- If you need to use Nether biome generation you need to do as follows
   - `NetherBiomeSource#getBiome` will give you the biome as scale 1/1
   - `NetherBiomeSource#getBiomeForNoiseGen` will give you the biome as scale 1/4 (aka pre-Voronoi)
   - `NetherBiomeSource.(full|voronoi)#get` are entry points for different level 
 
   
```java
import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.NetherBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
NetherBiomeSource netherBiomeSource=new NetherBiomeSource(MCVersion.vXXX, seed);
Biome biome=netherBiomeSource.getBiome(x,y,z); // here y matters
```

- If you need to use End biome generation you need to do as follows
   - `EndBiomeSource#getBiome` will give you the biome as scale 1/1
   - `EndBiomeSource#getBiomeForNoiseGen` will give you the biome as scale 1/4 (aka pre-Voronoi)
   - `EndBiomeSource.(full|simplex|voronoi)#get` are entry points for different level 
 
   
```java
import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.EndBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
EndBiomeSource endBiomeSource=new EndBiomeSource(MCVersion.vXXX, seed);
Biome biome=endBiomeSource.getBiome(x,y,z); // here y is always 0 no matter what you pass
```
