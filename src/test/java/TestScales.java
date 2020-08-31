import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

public class TestScales {
    public static void main(String[] args) {
        OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_12, 12228782782872L);

        printFunc(source.voronoi);
    }
    public static void printFunc(BiomeLayer last){
        while (last != null) {
            System.out.println(last.getLayerId()+" "+last.getScale()+" "+last.getClass().getName());
            if (last.getParents()!=null){
                for (BiomeLayer biomeLayer : last.getParents()) {
                    printFunc(biomeLayer);
                }
                return;
            }
            last = last.getParent();
        }
    }
}
