package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.block.state.CozyCampProperties;
import net.kings_of_devs.cozy_camping.block.state.TentPiece;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class TentBlock extends Block {
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape NORTH_COLLISION_SHAPE;
    private static final VoxelShape SOUTH_COLLISION_SHAPE;
    private static final VoxelShape EAST_COLLISION_SHAPE;
    private static final VoxelShape WEST_COLLISION_SHAPE;
    private static final VoxelShape UPPER_NORTH_SHAPE;
    private static final VoxelShape UPPER_SOUTH_SHAPE;
    private static final VoxelShape UPPER_EAST_SHAPE;
    private static final VoxelShape UPPER_WEST_SHAPE;
    private static final VoxelShape UPPER_NORTH_EAST_SHAPE;
    private static final VoxelShape UPPER_NORTH_WEST_SHAPE;
    private static final VoxelShape UPPER_SOUTH_EAST_SHAPE;
    private static final VoxelShape UPPER_SOUTH_WEST_SHAPE;
    public static final DirectionProperty FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    public static final EnumProperty<TentPiece> PIECE;

    public TentBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(PIECE, TentPiece.CENTER));
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        var value = 1.0F;
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            if (state.get(PIECE) == TentPiece.CENTER) {
                value = 0.125F;
            }
        }
        return value;
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
        this.placeLowestEnds(world, pos, state);
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
                this.tryBreak(world, pos, state, null);
            }
        }
    }

    private void loopThroughSlice(BlockState state, BiConsumer<Integer, Integer> consumer, boolean includeLowestEnds) {
        var isFacingZ = state.get(FACING).getAxis() == Direction.Axis.Z;
        var minX = includeLowestEnds ? -1 : ( isFacingZ ? -2 : -1 );
        var maxX = includeLowestEnds ?  1 : ( isFacingZ ?  2 :  1 );
        var minZ = includeLowestEnds ? -1 : ( isFacingZ ? -1 : -2 );
        var maxZ = includeLowestEnds ?  1 : ( isFacingZ ?  1 :  2 );

        for (int x = minX; x <= maxX; x++) { //width
            for (int z = minZ; z <= maxZ; z++) { //length
                consumer.accept(x, z);
            }
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var canPlace = new AtomicBoolean(true);
        //Check Lower half and Floor validity
        this.loopThroughSlice(state, (x, z) -> {
            var newPos = pos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
            if (!world.getBlockState(newPos).isAir())
                canPlace.set(false);
            if (!world.getBlockState(newPos.down()).isSideSolidFullSquare(world, newPos.down(), Direction.UP))
                canPlace.set(!world.getBlockState(newPos.down()).isSideSolidFullSquare(world, newPos.down(), Direction.UP));
        }, false);
        //Check Upper half
        this.loopThroughSlice(state, (x, z) -> {
            var newPos = pos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
            if (!world.getBlockState(newPos.up()).isAir())
                canPlace.set(false);
        }, true);
        return canPlace.get();
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.tryBreak(world, pos, state, player);
        super.onBreak(world, pos, state, player);
    }

    private void tryBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        var half = state.get(HALF) == DoubleBlockHalf.UPPER ? Direction.DOWN : Direction.UP;
        switch (state.get(PIECE)) {
            case CENTER -> this.breakFromMiddle(state, half, world, pos, player);
            case NORTH_LOWEST -> this.breakFromLowestSide(world, pos, player, Direction.SOUTH, TentPiece.NORTH);
            case SOUTH_LOWEST -> this.breakFromLowestSide(world, pos, player, Direction.NORTH, TentPiece.SOUTH);
            case EAST_LOWEST -> this.breakFromLowestSide(world, pos, player, Direction.WEST, TentPiece.EAST);
            case WEST_LOWEST -> this.breakFromLowestSide(world, pos, player, Direction.EAST, TentPiece.WEST);
            case NORTH_EAST_LOWEST -> this.breakFromLowestCorner(world, pos, player, Direction.WEST, Direction.SOUTH, TentPiece.NORTH_EAST);
            case NORTH_WEST_LOWEST -> this.breakFromLowestCorner(world, pos, player, Direction.EAST, Direction.SOUTH, TentPiece.NORTH_WEST);
            case SOUTH_EAST_LOWEST -> this.breakFromLowestCorner(world, pos, player, Direction.WEST, Direction.NORTH, TentPiece.SOUTH_EAST);
            case SOUTH_WEST_LOWEST -> this.breakFromLowestCorner(world, pos, player, Direction.EAST, Direction.NORTH, TentPiece.SOUTH_WEST);
            default -> this.checkBlocksAroundAndTryBreak(state, pos, world, player);
        }
    }

    private void breakFromLowestCorner(World world, BlockPos pos, PlayerEntity player, Direction directionX, Direction directionZ, TentPiece piece) {
        this.breakFromLowestSide(world, pos, player, directionZ, piece);
        this.breakFromLowestSide(world, pos, player, directionX, piece);
    }

    private void breakFromLowestSide(World world, BlockPos pos, PlayerEntity player, Direction direction, TentPiece piece) {
        var posToCheck = pos.offset(direction);
        var stateToCheck = world.getBlockState(posToCheck);
        if (stateToCheck.getBlock() instanceof TentBlock && stateToCheck.get(PIECE) == piece) {
            this.checkBlocksAroundAndTryBreak(stateToCheck, posToCheck, world, player);
        }
    }

    private void checkBlocksAroundAndTryBreak(BlockState state, BlockPos pos, World world, PlayerEntity player) {
        this.loopThroughSlice(state, (x, z) -> {
            var newPos = pos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
            var foundState = world.getBlockState(newPos);
            if (foundState.getBlock() instanceof TentBlock && foundState.get(PIECE) == TentPiece.CENTER) {
                var half = foundState.get(HALF) == DoubleBlockHalf.UPPER ? Direction.DOWN : Direction.UP;
                this.breakFromMiddle(state, half, world, newPos, player);
            }
        }, true);
    }

    private void breakFromMiddle(BlockState state, Direction half, World world, BlockPos pos, PlayerEntity player) {
        var canLoot = player == null || !player.isCreative();
        var basePos = pos.offset(half);
        this.loopThroughSlice(state, (x, z) -> {
            var newPos = basePos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
            if (world.getBlockState(newPos).getBlock() instanceof TentBlock) {
                this.breakBlock(world,newPos, canLoot, player);
            }
            if (world.getBlockState(newPos.offset(half.getOpposite())).getBlock() instanceof TentBlock) {
                this.breakBlock(world, newPos.offset(half.getOpposite()), canLoot, player);
            }
        }, false);
    }

    public void breakBlock(World world, BlockPos pos, boolean drop, @Nullable Entity breakingEntity) {
        var blockState = world.getBlockState(pos);
        var fluidState = world.getFluidState(pos);
        var hasBroken = world.setBlockState(pos, fluidState.getBlockState(), Block.NOTIFY_ALL, 512);
        world.addBlockBreakParticles(pos, blockState);

        if (drop) {
            Block.dropStacks(blockState, world, pos, null, breakingEntity, ItemStack.EMPTY);
        }
        if (hasBroken) {
            world.emitGameEvent(breakingEntity, GameEvent.BLOCK_DESTROY, pos);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var defaultShape = switch (state.get(PIECE)) {
            case NORTH_LOWEST -> UPPER_NORTH_SHAPE;
            case SOUTH_LOWEST -> UPPER_SOUTH_SHAPE;
            case EAST_LOWEST -> UPPER_EAST_SHAPE;
            case WEST_LOWEST -> UPPER_WEST_SHAPE;
            case NORTH_EAST_LOWEST -> UPPER_NORTH_EAST_SHAPE;
            case NORTH_WEST_LOWEST -> UPPER_NORTH_WEST_SHAPE;
            case SOUTH_EAST_LOWEST -> UPPER_SOUTH_EAST_SHAPE;
            case SOUTH_WEST_LOWEST -> UPPER_SOUTH_WEST_SHAPE;
            default -> super.getOutlineShape(state, world, pos, context);
        };
        var isUpper = state.get(HALF) == DoubleBlockHalf.UPPER;
        var northShape = isUpper ? UPPER_NORTH_SHAPE : defaultShape;
        var southShape = isUpper ? UPPER_SOUTH_SHAPE : defaultShape;
        var eastShape = isUpper ? UPPER_EAST_SHAPE : defaultShape;
        var westShape = isUpper ? UPPER_WEST_SHAPE : defaultShape;

        return switch (state.get(FACING)) {
            case NORTH, SOUTH -> this.getShapeForPiece(state, defaultShape, NORTH_SHAPE, SOUTH_SHAPE, eastShape, westShape);
            case EAST, WEST -> this.getShapeForPiece(state, defaultShape, northShape, southShape, EAST_SHAPE, WEST_SHAPE);
            case UP, DOWN -> defaultShape;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var isUpper = state.get(HALF) == DoubleBlockHalf.UPPER;
        var centerShape = isUpper ? VoxelShapes.union(NORTH_COLLISION_SHAPE, SOUTH_COLLISION_SHAPE) : VoxelShapes.empty();
        var defaultShape = switch (state.get(PIECE)) {
            case NORTH_LOWEST -> UPPER_NORTH_SHAPE;
            case SOUTH_LOWEST -> UPPER_SOUTH_SHAPE;
            case EAST_LOWEST -> UPPER_EAST_SHAPE;
            case WEST_LOWEST -> UPPER_WEST_SHAPE;
            case NORTH_EAST_LOWEST -> UPPER_NORTH_EAST_SHAPE;
            case NORTH_WEST_LOWEST -> UPPER_NORTH_WEST_SHAPE;
            case SOUTH_EAST_LOWEST -> UPPER_SOUTH_EAST_SHAPE;
            case SOUTH_WEST_LOWEST -> UPPER_SOUTH_WEST_SHAPE;
            default -> super.getOutlineShape(state, world, pos, context);
        };
        var northShapeX = isUpper ? UPPER_NORTH_SHAPE : defaultShape;
        var southShapeX = isUpper ? UPPER_SOUTH_SHAPE : defaultShape;
        var eastShapeZ = isUpper ? UPPER_EAST_SHAPE : defaultShape;
        var westShapeZ = isUpper ? UPPER_WEST_SHAPE : defaultShape;
        var northShapeZ = isUpper ? NORTH_COLLISION_SHAPE : VoxelShapes.empty();
        var southShapeZ = isUpper ? SOUTH_COLLISION_SHAPE : VoxelShapes.empty();
        var eastShapeX = isUpper ? EAST_COLLISION_SHAPE : VoxelShapes.empty();
        var westShapeX = isUpper ? WEST_COLLISION_SHAPE : VoxelShapes.empty();

        if (state.get(PIECE) != TentPiece.CENTER) {
            return switch (state.get(FACING).getAxis()) {
                case Z -> this.getShapeForPiece(state, defaultShape, northShapeZ, southShapeZ, eastShapeZ, westShapeZ);
                case X -> this.getShapeForPiece(state, defaultShape, northShapeX, southShapeX, eastShapeX, westShapeX);
                case Y -> defaultShape;
            };
        } else {
            return centerShape;
        }
    }

    private VoxelShape getShapeForPiece(BlockState state, VoxelShape defaultShape, VoxelShape north, VoxelShape south, VoxelShape east, VoxelShape west) {
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
        return switch(state.get(FACING).getAxis()) {
            case Z -> isUpper ? upperShape : lowerShapeOnZ;
            case X -> isUpper ? upperShape : lowerShapeOnX;
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
        NORTH_COLLISION_SHAPE = Block.createCuboidShape(0D, 8D, 8D, 16D, 16D, 16D);
        SOUTH_COLLISION_SHAPE = Block.createCuboidShape(0D, 8D, 0D, 16D, 16D, 08D);
        EAST_COLLISION_SHAPE = Block.createCuboidShape(0D, 8D, 0D, 08D, 16D, 16D);
        WEST_COLLISION_SHAPE = Block.createCuboidShape(8D, 8D, 0D, 16D, 16D, 16D);
    }
}