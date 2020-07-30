package kaptainwutax.biomeutils.layer;

import kaptainwutax.biomeutils.layer.composite.VoronoiLayer;
import kaptainwutax.biomeutils.layer.scale.ScaleLayer;

import java.util.ArrayList;

public  class LayerStack<T extends BiomeLayer> extends ArrayList<T> {

	protected int layerIdCounter = 0;

	@Override
	public boolean add(T layer) {
		layer.setLayerId(this.layerIdCounter++);
		return super.add(layer);
	}

	public void setScales() {
		setRecursiveScale(this.get(this.size() - 1), 1);
	}

	public void setRecursiveScale(BiomeLayer last, int scale) {
		if(last == null)return;
		int max = 0;

		for(BiomeLayer biomeLayer: last.getParents()) {
			setRecursiveScale(biomeLayer, scale << ((last instanceof VoronoiLayer) ? 2 : (last instanceof ScaleLayer) ? 1 : 0));
			max = Math.max(max, scale);
		}

		last.setScale(max);
	}

}