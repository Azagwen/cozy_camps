package net.kings_of_devs.cozy_camping.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class WorldgenMain {
    public static void init(){
        StructureRegistration.setupAndRegisterStructureFeatures();
        StructurePieceTypeRegistration.registerStructurePieceTypes();
        ConfiguredStructureRegistration.registerConfiguredStructures();

        BiomeModifications.create(new Identifier(CozyCampingMain.MOD_ID))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addBuiltInStructure(ConfiguredStructureRegistration.CONFIGURED_CAMPSITE));
    }
}
//BiomeModifications.create(new Identifier(MODID, "restructured_mineshaft_addition")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> {context.getGenerationSettings().addBuiltInStructure(ConfiguredStructureRegistration.CONFIGURED_RESTRUCTURED_MINESHAFT);});