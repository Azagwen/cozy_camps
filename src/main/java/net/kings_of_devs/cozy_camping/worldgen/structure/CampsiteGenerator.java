package net.kings_of_devs.cozy_camping.worldgen.structure;

import net.kings_of_devs.cozy_camping.worldgen.StructurePieceTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
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
            for(int x = this.boundingBox.getMinX(); x <= this.boundingBox.getMaxX(); x++){
                for(int y = this.boundingBox.getMinY(); y <= this.boundingBox.getMaxY(); y++){
                    for(int z = this.boundingBox.getMinZ(); z <= this.boundingBox.getMaxZ(); z++){
                        BlockPos currentPos = offsetPos(x, y, z);
                        if(random.nextFloat() < 0.2){
                            world.setBlockState(currentPos, Blocks.DIORITE.getDefaultState(), Block.NOTIFY_LISTENERS);
                        }
                    }
                }
            }
            world.setBlockState(new BlockPos(0, 0, 0), Blocks.DIORITE.getDefaultState(), Block.NOTIFY_LISTENERS);
            return true;
        }
    }
}
