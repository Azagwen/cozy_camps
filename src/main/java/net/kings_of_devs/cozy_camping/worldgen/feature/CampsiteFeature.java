package net.kings_of_devs.cozy_camping.worldgen.feature;

import com.mojang.serialization.Codec;
import net.kings_of_devs.cozy_camping.worldgen.structure.CampsiteGenerator;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class CampsiteFeature extends StructureFeature<DefaultFeatureConfig> {
    public CampsiteFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }
    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return CampsiteFeature.Start::new;
    }
    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, ChunkRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, DefaultFeatureConfig featureConfig, HeightLimitView heightLimitView){
        return(chunkRandom.nextInt(3) == 1);
    }
    public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureIn, ChunkPos chunkPos, int referenceIn, long seedIn) {
            super(structureIn, chunkPos, referenceIn, seedIn);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, DefaultFeatureConfig defaultFeatureConfig, HeightLimitView heightLimitView) {
            int x = (chunkPos.x << 4) + 7;
            int z = (chunkPos.z << 4) + 7;
            int y = chunkGenerator.getHeightOnGround(x, z, Heightmap.Type.WORLD_SURFACE_WG, heightLimitView);

            int widthRadiusX = random.nextInt(6) + 6;
            int widthRadiusZ = random.nextInt(6) + 6;

            StructurePiece campsite = new CampsiteGenerator.CampsitePiece(-1, this.random, x, y, z, widthRadiusX, widthRadiusZ);
            this.addPiece(campsite);
            /*

            Old jigsaw code, leaving this here for a rainy day

            StructurePoolFeatureConfig structureSettingsAndStartPool = new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(CaveTweaksMain.MODID, "elevator_start_pool")), 20);
            StructurePoolBasedGenerator.method_30419(dynamicRegistryManager, structureSettingsAndStartPool, PoolStructurePiece::new, chunkGenerator, structureManager, floorBlockPos, this, this.random, false, false, heightLimitView);

            */
        }
    }
}
