package net.kings_of_devs.cozy_camping.worldgen.structure;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.block.StumpBlock;
import net.kings_of_devs.cozy_camping.block.TentBlock;
import net.kings_of_devs.cozy_camping.block.state.StumpShape;
import net.kings_of_devs.cozy_camping.block.state.TentPiece;
import net.kings_of_devs.cozy_camping.util.FastNoiseLite;
import net.kings_of_devs.cozy_camping.worldgen.StructurePieceTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.*;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.lwjgl.system.CallbackI;

import java.util.Arrays;
import java.util.List;
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
                    if(noiseValue > 0.5F && world.getBlockState(currentPos).getMaterial().isSolid()){
                        world.setBlockState(currentPos, Blocks.COARSE_DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
                    }
                }
            }
            System.out.println(this.boundingBox.getMinX());
            //TentBlock tempTent = new TentBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));
            //tempTent.onPlaced((World) world, offsetPos(0, 0, 0), BlockRegistry.TENT.getDefaultState(), null, new ItemStack(Items.ACACIA_WOOD));
            world.getChunk(boundingBox.getCenter()).markBlockForPostProcessing(boundingBox.getCenter());
            BlockPos biomeDefinitionBlockPos = new BlockPos(this.boundingBox.getMinX(), this.boundingBox.getMinY(), this.boundingBox.getMinZ());
            Biome.Category structureBiomeCategory = world.getBiome(biomeDefinitionBlockPos).getCategory();

            Random structureRandom = new Random(this.boundingBox.getMinX());
            BlockPos campfireCenter = new BlockPos(this.boundingBox.getMinX() + this.boundingBox.getBlockCountX() / 2, this.boundingBox.getMinY(), this.boundingBox.getMinZ() + this.boundingBox.getBlockCountZ() / 2);

            world.setBlockState(campfireCenter, Blocks.CAMPFIRE.getDefaultState(), Block.NOTIFY_LISTENERS);
            if(structureRandom.nextFloat() < 0.7F){placeStumpSeat(world, Direction.Axis.X, campfireCenter.add(0, 0, 3), structureBiomeCategory);}
            if(structureRandom.nextFloat() < 0.7F){placeStumpSeat(world, Direction.Axis.X, campfireCenter.add(0, 0, -3), structureBiomeCategory);}
            if(structureRandom.nextFloat() < 0.7F){placeStumpSeat(world, Direction.Axis.Z, campfireCenter.add(3, 0, 0), structureBiomeCategory);}
            if(structureRandom.nextFloat() < 0.7F){placeStumpSeat(world, Direction.Axis.Z, campfireCenter.add(-3, 0, 0), structureBiomeCategory);}

            for(int index = 0; index < 16; index++){
                Random tentRandom = new Random(index + this.boundingBox.getMinX());
                int randomXPos = tentRandom.nextInt(this.boundingBox.getBlockCountX() - 2) + this.boundingBox.getMinX() + 1;
                int randomZPos = tentRandom.nextInt(this.boundingBox.getBlockCountZ() - 2) + this.boundingBox.getMinZ() + 1;
                BlockPos tentPos = new BlockPos(randomXPos, this.boundingBox.getMinY(), randomZPos);
                if(clearForTentPlacement(world, tentPos)){
                    placeTent(world, tentPos, getRandomTentState(tentRandom, structureBiomeCategory));
                }
            }
            //placeTent(world, offsetPos(this.boundingBox.getMinX(), this.boundingBox.getMinY(), this.boundingBox.getMinZ()), getRandomTentState(new Random(1), structureBiomeCategory));
            //placeTent(world, offsetPos(this.boundingBox.getMinX(), this.boundingBox.getMinY(), this.boundingBox.getMaxZ()), getRandomTentState(new Random(2), structureBiomeCategory));
            //placeTent(world, offsetPos(this.boundingBox.getMaxX(), this.boundingBox.getMinY(), this.boundingBox.getMinZ()), getRandomTentState(new Random(3), structureBiomeCategory));
            //placeTent(world, offsetPos(this.boundingBox.getMaxX(), this.boundingBox.getMinY(), this.boundingBox.getMaxZ()), getRandomTentState(new Random(4), structureBiomeCategory));
            return true;
        }

        public boolean clearForTentPlacement(StructureWorldAccess world, BlockPos blockPos){
            for(int x = -3; x <= 3; x++){
                for(int z = -3; z <= 3; z++){
                    if(BlockRegistry.TENTS.contains(world.getBlockState(blockPos.add(x, 0, z)).getBlock())){
                        return false;
                    }
                    if(BlockRegistry.STUMPS.contains(world.getBlockState(blockPos.add(x, 0, z)).getBlock())){
                        return false;
                    }
                    if(world.getBlockState(blockPos.add(x, 0, z)).getBlock() == Blocks.CAMPFIRE){
                        return false;
                    }
                }
            }
            return true;
        }

        public void placeStumpSeat(StructureWorldAccess world, Direction.Axis axis, BlockPos blockPos, Biome.Category biomeCategory){
            Block stumpBlock = switch(biomeCategory){
                case DESERT -> BlockRegistry.BIRCH_STUMP;
                case JUNGLE -> BlockRegistry.JUNGLE_STUMP;
                case ICY, TAIGA, EXTREME_HILLS -> BlockRegistry.SPRUCE_STUMP;
                case SAVANNA -> BlockRegistry.ACACIA_STUMP;
                default -> BlockRegistry.OAK_STUMP;
            };
            BlockState stumpState = stumpBlock.getDefaultState();
            if (axis == Direction.Axis.X) {
                world.setBlockState(blockPos.add(-1, 0, 0), stumpState.with(StumpBlock.SHAPE, StumpShape.X_POSITIVE), Block.NOTIFY_LISTENERS);
                world.setBlockState(blockPos, stumpState.with(StumpBlock.SHAPE, StumpShape.X_CENTERED_LARGE), Block.NOTIFY_LISTENERS);
                world.setBlockState(blockPos.add(1, 0, 0), stumpState.with(StumpBlock.SHAPE, StumpShape.X_NEGATIVE), Block.NOTIFY_LISTENERS);
            } else {
                world.setBlockState(blockPos.add(0, 0, -1), stumpState.with(StumpBlock.SHAPE, StumpShape.Z_POSITIVE), Block.NOTIFY_LISTENERS);
                world.setBlockState(blockPos, stumpState.with(StumpBlock.SHAPE, StumpShape.Z_CENTERED_LARGE), Block.NOTIFY_LISTENERS);
                world.setBlockState(blockPos.add(0, 0, 1), stumpState.with(StumpBlock.SHAPE, StumpShape.Z_NEGATIVE), Block.NOTIFY_LISTENERS);
            }
        }

        public static BlockState getRandomTentState(Random random, Biome.Category biomeCategory){
            boolean isRipped = true;
            Direction randomDirection = random.nextBoolean() ? Direction.NORTH : Direction.EAST;
            int randomColorIndex = random.nextInt(3);
            return switch(randomColorIndex){
                case 0 -> BlockRegistry.WHITE_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                case 1 -> BlockRegistry.LIGHT_GRAY_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                case 2 -> switch(biomeCategory){
                    case EXTREME_HILLS -> BlockRegistry.LIGHT_BLUE_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                    case FOREST -> BlockRegistry.RED_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                    case PLAINS -> BlockRegistry.YELLOW_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                    case TAIGA -> BlockRegistry.BROWN_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                    case SAVANNA -> BlockRegistry.ORANGE_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                    case ICY -> BlockRegistry.LIGHT_BLUE_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                    default -> BlockRegistry.WHITE_TENT.getDefaultState().with(TentBlock.FACING, randomDirection);
                };
                default -> (isRipped ? BlockRegistry.WHITE_TENT : BlockRegistry.WHITE_RIPPED_TENT).getDefaultState().with(TentBlock.FACING, randomDirection);
            };
        }

        public void placeTent(StructureWorldAccess world, BlockPos pos, BlockState state) {
            var upperHalf = state.with(TentBlock.HALF, DoubleBlockHalf.UPPER);
            //Upper
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up(), upperHalf, Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().north(), upperHalf.with(TentBlock.PIECE, TentPiece.NORTH), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().south(), upperHalf.with(TentBlock.PIECE, TentPiece.SOUTH), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().east(), upperHalf.with(TentBlock.PIECE, TentPiece.EAST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().west(), upperHalf.with(TentBlock.PIECE, TentPiece.WEST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().north().east(), upperHalf.with(TentBlock.PIECE, TentPiece.NORTH_EAST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().north().west(), upperHalf.with(TentBlock.PIECE, TentPiece.NORTH_WEST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().south().east(), upperHalf.with(TentBlock.PIECE, TentPiece.SOUTH_EAST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.up().south().west(), upperHalf.with(TentBlock.PIECE, TentPiece.SOUTH_WEST), Block.NOTIFY_LISTENERS);
            //Lower
            world.setBlockState(pos.north(), state.with(TentBlock.PIECE, TentPiece.NORTH), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.south(), state.with(TentBlock.PIECE, TentPiece.SOUTH), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.east(), state.with(TentBlock.PIECE, TentPiece.EAST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.west(), state.with(TentBlock.PIECE, TentPiece.WEST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.north().east(), state.with(TentBlock.PIECE, TentPiece.NORTH_EAST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.north().west(), state.with(TentBlock.PIECE, TentPiece.NORTH_WEST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.south().east(), state.with(TentBlock.PIECE, TentPiece.SOUTH_EAST), Block.NOTIFY_LISTENERS);
            world.setBlockState(pos.south().west(), state.with(TentBlock.PIECE, TentPiece.SOUTH_WEST), Block.NOTIFY_LISTENERS);
            placeLowestEnds(world, pos, state);
        }

        private void placeLowestEnds(StructureWorldAccess world, BlockPos pos, BlockState state) {
            switch (state.get(TentBlock.FACING)) {
                case NORTH, SOUTH -> {
                    world.setBlockState(pos.east(2), state.with(TentBlock.PIECE, TentPiece.EAST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.east(2).north(), state.with(TentBlock.PIECE, TentPiece.NORTH_EAST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.east(2).south(), state.with(TentBlock.PIECE, TentPiece.SOUTH_EAST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.west(2), state.with(TentBlock.PIECE, TentPiece.WEST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.west(2).north(), state.with(TentBlock.PIECE, TentPiece.NORTH_WEST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.west(2).south(), state.with(TentBlock.PIECE, TentPiece.SOUTH_WEST_LOWEST), Block.NOTIFY_LISTENERS);
                }
                case EAST, WEST -> {
                    world.setBlockState(pos.north(2), state.with(TentBlock.PIECE, TentPiece.NORTH_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.north(2).east(), state.with(TentBlock.PIECE, TentPiece.NORTH_EAST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.north(2).west(), state.with(TentBlock.PIECE, TentPiece.NORTH_WEST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.south(2), state.with(TentBlock.PIECE, TentPiece.SOUTH_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.south(2).east(), state.with(TentBlock.PIECE, TentPiece.SOUTH_EAST_LOWEST), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos.south(2).west(), state.with(TentBlock.PIECE, TentPiece.SOUTH_WEST_LOWEST), Block.NOTIFY_LISTENERS);
                }
            }
        }
    }
}
