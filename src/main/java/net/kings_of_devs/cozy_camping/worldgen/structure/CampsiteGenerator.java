package net.kings_of_devs.cozy_camping.worldgen.structure;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.block.TentBlock;
import net.kings_of_devs.cozy_camping.block.state.TentPiece;
import net.kings_of_devs.cozy_camping.util.FastNoiseLite;
import net.kings_of_devs.cozy_camping.worldgen.StructurePieceTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public class CampsiteGenerator {

    public static class CampsitePiece extends StructurePiece {
        public CampsitePiece(int chainLength, ChunkRandom random, int x, int y, int z, int widthRadiusX, int widthRadiusZ) {
            super(StructurePieceTypes.CAMPSITE, chainLength, new BlockBox(x - widthRadiusX, y, z - widthRadiusZ, x + widthRadiusX, y + 8, z + widthRadiusZ));
        }

        public CampsitePiece(ServerWorld serverWorld, NbtCompound nbtCompound) {
            super(StructurePieceTypes.CAMPSITE, nbtCompound);
        }

        @Override
        protected void writeNbt(ServerWorld world, NbtCompound nbt) {

        }

        @Override
        public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            FastNoiseLite simplexNoise = new FastNoiseLite((int) world.getSeed());
            simplexNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
            simplexNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
            simplexNoise.SetFrequency(0.1F);
            simplexNoise.SetFractalOctaves(1);

            /*
            for(int x = this.boundingBox.getMinX(); x <= this.boundingBox.getMaxX(); x++){
                for(int y = this.boundingBox.getMinY(); y <= this.boundingBox.getMaxY(); y++){
                    for(int z = this.boundingBox.getMinZ(); z <= this.boundingBox.getMaxZ(); z++){
                        BlockPos currentPos = offsetPos(x, y, z);
                        world.setBlockState(currentPos, Blocks.GLASS.getDefaultState(), Block.NOTIFY_LISTENERS);
                    }
                }
            }
            */

            for(int x = this.boundingBox.getMinX() - 3; x <= this.boundingBox.getMaxX() + 3; x++){
                for(int z = this.boundingBox.getMinZ() - 3; z <= this.boundingBox.getMaxZ() + 3; z++){
                    BlockPos currentPos = offsetPos(x, this.boundingBox.getMinY() - 1, z);
                    float noiseValue = (simplexNoise.GetNoise(x, z) * 0.5F + 0.5F);
                    if(noiseValue > 0.8F && world.getBlockState(currentPos).getMaterial().isSolid()){
                        world.setBlockState(currentPos, Blocks.COARSE_DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
                    }
                }
            }
            //TentBlock tempTent = new TentBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));
            //tempTent.onPlaced((World) world, offsetPos(0, 0, 0), BlockRegistry.TENT.getDefaultState(), null, new ItemStack(Items.ACACIA_WOOD));
            return true;
        }
    }
}
