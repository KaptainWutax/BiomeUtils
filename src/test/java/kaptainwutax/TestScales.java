import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScales {

    private static final String scales_1_14 = "50 1 kaptainwutax.biomeutils.layer.composite.VoronoiLayer\n" +
            "49 4 kaptainwutax.biomeutils.layer.water.OceanTemperatureLayer$Apply\n" +
            "41 4 kaptainwutax.biomeutils.layer.water.RiverLayer\n" +
            "34 4 kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer\n" +
            "33 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "32 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "31 16 kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer\n" +
            "30 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "29 32 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "28 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "27 64 kaptainwutax.biomeutils.layer.land.SunflowerPlainsLayer\n" +
            "26 64 kaptainwutax.biomeutils.layer.land.HillsLayer\n" +
            "22 64 kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer\n" +
            "21 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "20 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "19 256 kaptainwutax.biomeutils.layer.land.BambooJungleLayer\n" +
            "18 256 kaptainwutax.biomeutils.layer.land.BaseBiomesLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "25 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "24 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "23 256 kaptainwutax.biomeutils.layer.land.NoiseLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "40 4 kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer\n" +
            "39 4 kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer\n" +
            "38 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "37 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "36 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "35 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "25 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "24 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "23 256 kaptainwutax.biomeutils.layer.land.NoiseLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "48 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "47 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "46 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "45 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "44 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "43 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "42 256 kaptainwutax.biomeutils.layer.water.OceanTemperatureLayer\n";
    private static final String scales_1_13 = "49 1 kaptainwutax.biomeutils.layer.composite.VoronoiLayer\n" +
            "48 4 kaptainwutax.biomeutils.layer.water.OceanTemperatureLayer$Apply\n" +
            "40 4 kaptainwutax.biomeutils.layer.water.RiverLayer\n" +
            "33 4 kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer\n" +
            "32 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "31 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "30 16 kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer\n" +
            "29 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "28 32 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "27 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "26 64 kaptainwutax.biomeutils.layer.land.SunflowerPlainsLayer\n" +
            "25 64 kaptainwutax.biomeutils.layer.land.HillsLayer\n" +
            "21 64 kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer\n" +
            "20 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "19 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "18 256 kaptainwutax.biomeutils.layer.land.BaseBiomesLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "24 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "23 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "22 256 kaptainwutax.biomeutils.layer.land.NoiseLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "39 4 kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer\n" +
            "38 4 kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer\n" +
            "37 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "36 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "35 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "34 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "24 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "23 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "22 256 kaptainwutax.biomeutils.layer.land.NoiseLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "47 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "46 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "45 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "44 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "43 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "42 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "41 256 kaptainwutax.biomeutils.layer.water.OceanTemperatureLayer\n";

    private static final String scales_1_12 = "43 1 kaptainwutax.biomeutils.layer.composite.VoronoiLayer\n" +
            "42 4 kaptainwutax.biomeutils.layer.water.RiverLayer\n" +
            "35 4 kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer\n" +
            "34 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "33 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "32 16 kaptainwutax.biomeutils.layer.shore.EdgeBiomesLayer\n" +
            "31 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "30 32 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "29 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "28 64 kaptainwutax.biomeutils.layer.land.SunflowerPlainsLayer\n" +
            "27 64 kaptainwutax.biomeutils.layer.land.HillsLayer\n" +
            "21 64 kaptainwutax.biomeutils.layer.shore.EaseEdgeLayer\n" +
            "20 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "19 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "18 256 kaptainwutax.biomeutils.layer.land.BaseBiomesLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "26 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "25 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "22 256 kaptainwutax.biomeutils.layer.land.NoiseLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n" +
            "41 4 kaptainwutax.biomeutils.layer.scale.SmoothScaleLayer\n" +
            "40 4 kaptainwutax.biomeutils.layer.water.NoiseToRiverLayer\n" +
            "39 4 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "38 8 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "37 16 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "36 32 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "24 64 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "23 128 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "22 256 kaptainwutax.biomeutils.layer.land.NoiseLayer\n" +
            "17 256 kaptainwutax.biomeutils.layer.water.DeepOceanLayer\n" +
            "16 256 kaptainwutax.biomeutils.layer.land.MushroomLayer\n" +
            "15 256 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "14 256 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "13 512 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "12 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Special\n" +
            "11 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cool\n" +
            "10 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Temperate\n" +
            "9 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "8 1024 kaptainwutax.biomeutils.layer.temperature.ClimateLayer$Cold\n" +
            "7 1024 kaptainwutax.biomeutils.layer.land.IslandLayer\n" +
            "6 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "5 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "4 1024 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "3 1024 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "2 2048 kaptainwutax.biomeutils.layer.land.LandLayer\n" +
            "1 2048 kaptainwutax.biomeutils.layer.scale.ScaleLayer\n" +
            "0 4096 kaptainwutax.biomeutils.layer.land.ContinentLayer\n";

    public static String printFunc(BiomeLayer last, String str) {
        if (last == null) {
            return str;
        }
        str += (String.format("%d %d %s\n", last.getLayerId(), last.getScale(), last.getClass().getName()));
        if (last.getParents() != null) {
            for (BiomeLayer biomeLayer : last.getParents()) {
                str = printFunc(biomeLayer, str);
            }
        }
        return str;
    }

    @Test
    @DisplayName("Test 1.12 scale and names")
    public void testScale1_12() {
        OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_12, 12228782782872L);
        assertEquals(scales_1_12, printFunc(source.voronoi, ""));
    }

    @Test
    @DisplayName("Test 1.13 scale and names")
    public void testScale1_13() {
        OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_13, 12228782782872L);
        assertEquals(scales_1_13, printFunc(source.voronoi, ""));
    }

    @Test
    @DisplayName("Test 1.14+ scale and names")
    public void testScale1_14() {
        OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_14, 12228782782872L);
        assertEquals(scales_1_14, printFunc(source.voronoi, ""));
    }
}
