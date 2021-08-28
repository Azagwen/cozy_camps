package net.kings_of_devs.cozy_camping.worldgen;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.worldgen.feature.CampsiteFeature;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class StructureRegistration {
    public static StructureFeature<DefaultFeatureConfig> CAMPSITE = new CampsiteFeature(DefaultFeatureConfig.CODEC);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(CozyCampingMain.MOD_ID, "campsite"), CAMPSITE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(5, 4, 95120001)).superflatFeature(CAMPSITE.configure(FeatureConfig.DEFAULT)).register();
    }
}
