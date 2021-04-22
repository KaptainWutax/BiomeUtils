package kaptainwutax.biomeutils.source;

import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.LayerStack;
import kaptainwutax.mcutils.version.MCVersion;

import java.util.List;

public abstract class LayeredBiomeSource<T extends BiomeLayer> extends BiomeSource {

	protected LayerStack<T> layers = new LayerStack<>();

	public LayeredBiomeSource(MCVersion version, long worldSeed) {
		super(version, worldSeed);
	}

	public List<T> getLayers() {
		return this.layers;
	}

	public T getLayer(int index) {
		return this.getLayers().get(index);
	}

	public int getLayerCount() {
		return this.getLayers().size();
	}

}
