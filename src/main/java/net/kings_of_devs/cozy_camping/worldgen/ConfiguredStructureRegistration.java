package net.kings_of_devs.cozy_camping.worldgen;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class ConfiguredStructureRegistration {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_CAMPSITE = StructureRegistration.CAMPSITE.configure(DefaultFeatureConfig.DEFAULT);
    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(CozyCampingMain.MOD_ID, "configured_campsite"), CONFIGURED_CAMPSITE);
    }
}