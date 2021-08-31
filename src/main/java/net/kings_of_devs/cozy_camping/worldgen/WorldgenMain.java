package net.kings_of_devs.cozy_camping.worldgen;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.worldgen.feature.HeatherFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CaveSurfaceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.Arrays;
import java.util.List;

public class WorldgenMain {

    //private static final Feature<DefaultFeatureConfig> HEATHER = new HeatherFeature(DefaultFeatureConfig.CODEC);
    //public static final ConfiguredFeature<?, ?> CONFIGURED_HEATHER = HEATHER.configure(new DefaultFeatureConfig()).decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR_WG))).spreadHorizontally().repeat(32);
    public static final ConfiguredFeature<?, ?> CONFIGURED_HEATHER = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.HEATHER.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).canReplace().cannotProject().build()).decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR_WG))).spreadHorizontally();
    public static final ConfiguredFeature<?, ?> CONFIGURED_BEARTRAP = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new SimpleBlockStateProvider(BlockRegistry.BEAR_TRAP.getDefaultState()))).decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR_WG))).spreadHorizontally().applyChance(20);

    public static void init(){
        StructureRegistration.setupAndRegisterStructureFeatures();
        StructurePieceTypeRegistration.registerStructurePieceTypes();
        ConfiguredStructureRegistration.registerConfiguredStructures();

        //Registry.register(Registry.FEATURE, new Identifier(CozyCampingMain.MOD_ID, "heather"), HEATHER);
        RegistryKey<ConfiguredFeature<?, ?>> configuredHeather = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(CozyCampingMain.MOD_ID, "configured_heather"));
        RegistryKey<ConfiguredFeature<?, ?>> configuredBeartrap = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(CozyCampingMain.MOD_ID, "configured_bear_trap"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, configuredHeather.getValue(), CONFIGURED_HEATHER);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, configuredBeartrap.getValue(), CONFIGURED_BEARTRAP);

        BiomeModifications.create(new Identifier(CozyCampingMain.MOD_ID))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.EXTREME_HILLS, Biome.Category.FOREST, Biome.Category.JUNGLE, Biome.Category.ICY, Biome.Category.PLAINS, Biome.Category.MESA, Biome.Category.SAVANNA, Biome.Category.TAIGA), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addBuiltInStructure(ConfiguredStructureRegistration.CONFIGURED_CAMPSITE))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.EXTREME_HILLS, Biome.Category.ICY, Biome.Category.TAIGA), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configuredHeather))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.TAIGA, Biome.Category.ICY), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configuredBeartrap));
    }

}
//BiomeModifications.create(new Identifier(MODID, "restructured_mineshaft_addition")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> {context.getGenerationSettings().addBuiltInStructure(ConfiguredStructureRegistration.CONFIGURED_RESTRUCTURED_MINESHAFT);});