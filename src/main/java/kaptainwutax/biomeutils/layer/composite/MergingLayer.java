package kaptainwutax.biomeutils.layer.composite;

import kaptainwutax.biomeutils.layer.BiomeLayer;

public abstract class MergingLayer extends BiomeLayer {

	protected final BiomeLayer[] parents;

	public MergingLayer(long worldSeed, long salt, BiomeLayer... parents) {
		super(worldSeed, salt, null);
		this.parents = parents;
	}

	public MergingLayer(long worldSeed, long salt) {
		this(worldSeed, salt, (BiomeLayer[])null);
	}

}
