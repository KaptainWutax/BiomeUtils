package kaptainwutax.biomeutils.layer.nether;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.noise.DoublePerlinNoiseSampler;
import kaptainwutax.biomeutils.noise.MixedNoisePoint;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NetherLayer extends BiomeLayer {

    private DoublePerlinNoiseSampler temperature;
    private DoublePerlinNoiseSampler humidity;
    private DoublePerlinNoiseSampler altitude;
    private DoublePerlinNoiseSampler weirdness;

    private final MixedNoisePoint[] biomePoints;
    private final boolean is3D;

    public NetherLayer(MCVersion version, long worldSeed, boolean is3D, MixedNoisePoint[] biomePoints) {
        super(version, (BiomeLayer)null);
        this.is3D = is3D;

        if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_16)) {
            this.temperature = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed), IntStream.rangeClosed(-7, -6));
            this.humidity = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed + 1L), IntStream.rangeClosed(-7, -6));
            this.altitude = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed + 2L), IntStream.rangeClosed(-7, -6));
            this.weirdness = new DoublePerlinNoiseSampler(new ChunkRand(worldSeed + 3L), IntStream.rangeClosed(-7, -6));
        }

        this.biomePoints = biomePoints;
    }

    @Override
    public int sample(int x, int y, int z) {
        if(this.getVersion().isOlderThan(MCVersion.v1_16))return Biome.NETHER_WASTES.getId();

        y = this.is3D ? y : 0;

        MixedNoisePoint point = new MixedNoisePoint(null,
                (float)this.temperature.sample(x, y, z),
                (float)this.humidity.sample(x, y, z),
                (float)this.altitude.sample(x, y, z),
                (float)this.weirdness.sample(x, y, z), 0.0F);

        return Stream.of(this.biomePoints).min(Comparator.comparing(m -> m.distanceTo(point)))
                .map(MixedNoisePoint::getBiome).orElse(Biome.THE_VOID).getId();
    }

}
