package kaptainwutax.biomeutils.biome;

import java.util.Objects;

public class BiomePoint {

	public final Biome biome;
	public final float temperature;
	public final float humidity;
	public final float altitude;
	public final float weirdness;
	public final float weight;

	public BiomePoint(Biome biome, float temperature, float humidity, float altitude, float weirdness, float weight) {
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

	public float distanceTo(BiomePoint other) {
		return (this.temperature - other.temperature) * (this.temperature - other.temperature) +
			(this.humidity - other.humidity) * (this.humidity - other.humidity) +
			(this.altitude - other.altitude) * (this.altitude - other.altitude) +
			(this.weirdness - other.weirdness) * (this.weirdness - other.weirdness) +
			(this.weight - other.weight) * (this.weight - other.weight);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)return true;
		if(!(o instanceof BiomePoint))return false;
		BiomePoint other = (BiomePoint)o;
		return this.biome == other.biome && this.temperature == other.temperature && this.humidity == other.humidity
			&& this.altitude == other.altitude && this.weirdness == other.weirdness && this.weight == other.weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.biome, this.temperature, this.humidity, this.altitude, this.weirdness, this.weight);
	}

	@Override
	public String toString() {
		return "BiomePoint{" +
			"biome=" + this.biome.getName() +
			", temperature=" + this.temperature +
			", humidity=" + this.humidity +
			", altitude=" + this.altitude +
			", weirdness=" + this.weirdness +
			", weight=" + this.weight +
			'}';
	}

}
