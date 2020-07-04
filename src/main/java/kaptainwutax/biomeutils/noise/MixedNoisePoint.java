package kaptainwutax.biomeutils.noise;

import kaptainwutax.biomeutils.Biome;

public class MixedNoisePoint {

	public final Biome biome;
	public final float temperature;
	public final float humidity;
	public final float altitude;
	public final float weirdness;
	public final float weight;

	public MixedNoisePoint(Biome biome, float temperature, float humidity, float altitude, float weirdness, float weight) {
		this.biome = biome;
		this.temperature = temperature;
		this.humidity = humidity;
		this.altitude = altitude;
		this.weirdness = weirdness;
		this.weight = weight;
	}

	public Biome getBiome() {
		return this.biome;
	}

	public float distanceTo(MixedNoisePoint other) {
		return (this.temperature - other.temperature) * (this.temperature - other.temperature) +
				(this.humidity - other.humidity) * (this.humidity - other.humidity) +
				(this.altitude - other.altitude) * (this.altitude - other.altitude) +
				(this.weirdness - other.weirdness) * (this.weirdness - other.weirdness) +
				(this.weight - other.weight) * (this.weight - other.weight);
	}

}
