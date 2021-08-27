package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.block.state.CozyCampProperties;
import net.kings_of_devs.cozy_camping.block.state.TentPiece;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TentBlock extends Block {
    public static final DirectionProperty FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    public static final EnumProperty<TentPiece> PIECE;
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape UPPER_NORTH_SHAPE;
    private static final VoxelShape UPPER_SOUTH_SHAPE;
    private static final VoxelShape UPPER_EAST_SHAPE;
    private static final VoxelShape UPPER_WEST_SHAPE;
    private static final VoxelShape UPPER_NORTH_EAST_SHAPE;
    private static final VoxelShape UPPER_NORTH_WEST_SHAPE;
    private static final VoxelShape UPPER_SOUTH_EAST_SHAPE;
    private static final VoxelShape UPPER_SOUTH_WEST_SHAPE;

    public TentBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(PIECE, TentPiece.CENTER));
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        var value = 1.0F;
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            if (state.get(PIECE) == TentPiece.CENTER) {
                value = 0.25F;
            }
        }
        return value;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        var upperHalf = state.with(HALF, DoubleBlockHalf.UPPER);
        //Upper
        world.setBlockState(pos.up(), upperHalf, Block.NOTIFY_ALL);
        world.setBlockState(pos.up().north(), upperHalf.with(PIECE, TentPiece.NORTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().south(), upperHalf.with(PIECE, TentPiece.SOUTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().east(), upperHalf.with(PIECE, TentPiece.EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().west(), upperHalf.with(PIECE, TentPiece.WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().north().east(), upperHalf.with(PIECE, TentPiece.NORTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().north().west(), upperHalf.with(PIECE, TentPiece.NORTH_WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().south().east(), upperHalf.with(PIECE, TentPiece.SOUTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().south().west(), upperHalf.with(PIECE, TentPiece.SOUTH_WEST), Block.NOTIFY_ALL);
        //Lower
        world.setBlockState(pos.north(), state.with(PIECE, TentPiece.NORTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.south(), state.with(PIECE, TentPiece.SOUTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.east(), state.with(PIECE, TentPiece.EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.west(), state.with(PIECE, TentPiece.WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.north().east(), state.with(PIECE, TentPiece.NORTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.north().west(), state.with(PIECE, TentPiece.NORTH_WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.south().east(), state.with(PIECE, TentPiece.SOUTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.south().west(), state.with(PIECE, TentPiece.SOUTH_WEST), Block.NOTIFY_ALL);
//        this.placeLowestEnds(world, pos, state);
    }

    private void placeLowestEnds(World world, BlockPos pos, BlockState state) {
        switch (state.get(FACING)) {
            case NORTH, SOUTH -> {
                world.setBlockState(pos.east(2), state.with(PIECE, TentPiece.EAST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.east(2).north(), state.with(PIECE, TentPiece.NORTH_EAST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.east(2).south(), state.with(PIECE, TentPiece.SOUTH_EAST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.west(2), state.with(PIECE, TentPiece.WEST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.west(2).north(), state.with(PIECE, TentPiece.NORTH_WEST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.west(2).south(), state.with(PIECE, TentPiece.SOUTH_WEST_LOWEST), Block.NOTIFY_ALL);
            }
            case EAST, WEST -> {
                world.setBlockState(pos.north(2), state.with(PIECE, TentPiece.NORTH_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.north(2).east(), state.with(PIECE, TentPiece.NORTH_EAST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.north(2).west(), state.with(PIECE, TentPiece.NORTH_WEST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.south(2), state.with(PIECE, TentPiece.SOUTH_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.south(2).east(), state.with(PIECE, TentPiece.SOUTH_EAST_LOWEST), Block.NOTIFY_ALL);
                world.setBlockState(pos.south(2).west(), state.with(PIECE, TentPiece.SOUTH_WEST_LOWEST), Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            if (!world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP)) {
                world.breakBlock(pos, true, null);
                this.checkBlocksAroundAndTryBreak(state, pos, world, null);
            }
//            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        switch (state.get(FACING)) {
            case NORTH, SOUTH -> {
                switch (state.get(PIECE)) {
                    case EAST, NORTH_EAST, SOUTH_EAST -> this.tryBreakSide(state, world, pos, Direction.EAST);
                    case WEST, NORTH_WEST, SOUTH_WEST -> this.tryBreakSide(state, world, pos, Direction.WEST);
                }
            }
            case EAST, WEST -> {
                switch (state.get(PIECE)) {
                    case NORTH, NORTH_EAST, SOUTH_EAST -> this.tryBreakSide(state, world, pos, Direction.NORTH);
                    case SOUTH, NORTH_WEST, SOUTH_WEST -> this.tryBreakSide(state, world, pos, Direction.SOUTH);
                }
            }
        }
    }

    private void tryBreakSide(BlockState state, World world, BlockPos pos, Direction direction) {
        if (!(world.getBlockState(pos.offset(direction)).getBlock() instanceof TentBlock)) {
            this.checkBlocksAroundAndTryBreak(state, pos, world, null);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (int x = -1; x <= 1; x++) { //width
            for (int z = -1; z <= 1; z++) { //length
                var newPos = pos.offset(Direction.NORTH, x).offset(Direction.EAST, z);
                if (!world.getBlockState(newPos).isAir() || !world.getBlockState(newPos.up()).isAir()) {
                    return false;
                }
                if (!world.getBlockState(newPos.down()).isSideSolidFullSquare(world, newPos.down(), Direction.UP)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.checkBlocksAroundAndTryBreak(state, pos, world, player);
        super.onBreak(world, pos, state, player);
    }

    private void breakFromMiddle(BlockState state, Direction half, World world, BlockPos pos, PlayerEntity player) {
        var values = new int[4];
        switch (state.get(FACING)) {
            case NORTH, SOUTH -> values = new int[] {-2, 2, -1, 1};
            case EAST, WEST -> values = new int[] {-1, 1, -2, 2};
        }
//        this.loopBasedBreaking(values[0], values[1], values[2], values[3], half, world, pos, player);
        this.loopBasedBreaking(-1, 1, -1, 1, half, world, pos, player);
    }

    private void loopBasedBreaking(int minX, int maxX, int minZ, int maxZ, Direction half, World world, BlockPos pos, @Nullable PlayerEntity player) {
        var canLoot = player == null || !player.isCreative();
        var basePos = pos.offset(half);
        for (int x = minX; x <= maxX; x++) { //width
            for (int z = minZ; z <= maxZ; z++) { //length
                var newPos = basePos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
                if (world.getBlockState(newPos).getBlock() instanceof TentBlock) {
                    world.breakBlock(newPos, canLoot, player);
                }
                if (world.getBlockState(newPos.offset(half.getOpposite())).getBlock() instanceof TentBlock) {
                    world.breakBlock(newPos.offset(half.getOpposite()), canLoot, player);
                }
            }
        }
    }

    private void checkBlocksAroundAndTryBreak(BlockState state, BlockPos pos, World world, PlayerEntity player) {
        for (int x = -1; x <= 1; x++) { //width
            for (int z = -1; z <= 1; z++) { //length
                var newPos = pos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
                var foundState = world.getBlockState(newPos);
                if (foundState.getBlock() instanceof TentBlock) {
                    if (foundState.get(PIECE) == TentPiece.CENTER) {
                        var half = foundState.get(HALF) == DoubleBlockHalf.UPPER ? Direction.DOWN : Direction.UP;
                        this.breakFromMiddle(state, half, world, newPos, player);
                    }
                }
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        var defaultShape = super.getOutlineShape(state, view, pos, context);
        var isUpper = state.get(HALF) == DoubleBlockHalf.UPPER;
        var northShape = isUpper ? UPPER_NORTH_SHAPE : defaultShape;
        var southShape = isUpper ? UPPER_SOUTH_SHAPE : defaultShape;
        var eastShape = isUpper ? UPPER_EAST_SHAPE : defaultShape;
        var westShape = isUpper ? UPPER_WEST_SHAPE : defaultShape;

        return switch (state.get(FACING)) {
            case NORTH, SOUTH -> this.findOutline(state, defaultShape, NORTH_SHAPE, SOUTH_SHAPE, eastShape, westShape);
            case EAST, WEST -> this.findOutline(state, defaultShape, northShape, southShape, EAST_SHAPE, WEST_SHAPE);
            case UP, DOWN -> defaultShape;
        };
    }

    private VoxelShape findOutline(BlockState state, VoxelShape defaultShape, VoxelShape north, VoxelShape south, VoxelShape east, VoxelShape west) {
        return switch (state.get(PIECE)) {
            case NORTH -> north;
            case SOUTH -> south;
            case EAST -> east;
            case WEST -> west;
            case NORTH_EAST -> this.getCornerOutline(state, defaultShape, UPPER_NORTH_EAST_SHAPE, NORTH_SHAPE, EAST_SHAPE);
            case NORTH_WEST -> this.getCornerOutline(state, defaultShape, UPPER_NORTH_WEST_SHAPE, NORTH_SHAPE, WEST_SHAPE);
            case SOUTH_EAST -> this.getCornerOutline(state, defaultShape, UPPER_SOUTH_EAST_SHAPE, SOUTH_SHAPE, EAST_SHAPE);
            case SOUTH_WEST -> this.getCornerOutline(state, defaultShape, UPPER_SOUTH_WEST_SHAPE, SOUTH_SHAPE, WEST_SHAPE);
            default -> defaultShape;
        };
    }

    private VoxelShape getCornerOutline(BlockState state, VoxelShape defaultShape, VoxelShape upperShape, VoxelShape lowerShapeOnZ, VoxelShape lowerShapeOnX) {
        var isUpper = state.get(HALF) == DoubleBlockHalf.UPPER;
        return switch(state.get(FACING)) {
            case NORTH, SOUTH -> isUpper ? upperShape : lowerShapeOnZ;
            case EAST, WEST -> isUpper ? upperShape : lowerShapeOnX;
            default -> defaultShape;
        };
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, PIECE);
    }

    static {
        HALF = Properties.DOUBLE_BLOCK_HALF;
        FACING = Properties.HORIZONTAL_FACING;
        PIECE = CozyCampProperties.TENT_PIECE;
        UPPER_NORTH_EAST_SHAPE = Block.createCuboidShape(0D, 0D, 8D, 08D, 8D, 16D);
        UPPER_NORTH_WEST_SHAPE = Block.createCuboidShape(8D, 0D, 8D, 16D, 8D, 16D);
        UPPER_SOUTH_EAST_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 08D, 8D, 08D);
        UPPER_SOUTH_WEST_SHAPE = Block.createCuboidShape(8D, 0D, 0D, 16D, 8D, 08D);
        UPPER_NORTH_SHAPE = VoxelShapes.union(UPPER_NORTH_EAST_SHAPE, UPPER_NORTH_WEST_SHAPE);
        UPPER_SOUTH_SHAPE = VoxelShapes.union(UPPER_SOUTH_EAST_SHAPE, UPPER_SOUTH_WEST_SHAPE);
        UPPER_EAST_SHAPE = VoxelShapes.union(UPPER_NORTH_EAST_SHAPE, UPPER_SOUTH_EAST_SHAPE);
        UPPER_WEST_SHAPE = VoxelShapes.union(UPPER_NORTH_WEST_SHAPE, UPPER_SOUTH_WEST_SHAPE);
        NORTH_SHAPE = Block.createCuboidShape(0D, 0D, 8D, 16D, 16D, 16D);
        SOUTH_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 16D, 16D, 08D);
        EAST_SHAPE = Block.createCuboidShape(0D, 0D, 0D, 08D, 16D, 16D);
        WEST_SHAPE = Block.createCuboidShape(8D, 0D, 0D, 16D, 16D, 16D);
    }
}
