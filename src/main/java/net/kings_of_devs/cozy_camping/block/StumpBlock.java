package net.kings_of_devs.cozy_camping.block;

import com.google.common.collect.Maps;
import net.kings_of_devs.cozy_camping.block.state.Properties;
import net.kings_of_devs.cozy_camping.block.state.StumpFacing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class StumpBlock extends Block {
    public static final EnumProperty<StumpFacing> FACING;
    private static final Map<StumpFacing, VoxelShape> SHAPE_MAP;

    public StumpBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_MAP.get(state.get(FACING));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return this.getPlacementState(world, pos, direction);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(ctx.getWorld(), ctx.getBlockPos(), ctx.getSide());
    }

    private BlockState getPlacementState(WorldAccess world, BlockPos pos, Direction direction) {
        var hasInstanceOnNorth = world.getBlockState(pos.north()).getBlock() instanceof StumpBlock;
        var hasInstanceOnSouth = world.getBlockState(pos.south()).getBlock() instanceof StumpBlock;
        var hasInstanceOnEast = world.getBlockState(pos.east()).getBlock() instanceof StumpBlock;
        var hasInstanceOnWest = world.getBlockState(pos.west()).getBlock() instanceof StumpBlock;

        return this.getDefaultState().with(FACING, switch (direction) {
            case UP, DOWN -> StumpFacing.VERTICAL;
            case NORTH -> hasInstanceOnSouth ? StumpFacing.Z : StumpFacing.NORTH;
            case SOUTH -> hasInstanceOnNorth ? StumpFacing.Z : StumpFacing.SOUTH;
            case EAST -> hasInstanceOnEast ? StumpFacing.X : StumpFacing.EAST;
            case WEST -> hasInstanceOnWest ? StumpFacing.X : StumpFacing.WEST;
        });
    }

    static {
        FACING = Properties.STUMP_FACING;
        SHAPE_MAP = Maps.newHashMap();
        SHAPE_MAP.put(StumpFacing.VERTICAL, Block.createCuboidShape(3D, 0D, 3D, 13D, 12D, 13D));
        SHAPE_MAP.put(StumpFacing.NORTH, Block.createCuboidShape(3D, 0D, 0D, 13D, 10D, 12D));
        SHAPE_MAP.put(StumpFacing.SOUTH, Block.createCuboidShape(3D, 0D, 4D, 13D, 10D, 16D));
        SHAPE_MAP.put(StumpFacing.EAST, Block.createCuboidShape(0D, 0D, 3D, 12D, 10D, 13D));
        SHAPE_MAP.put(StumpFacing.WEST, Block.createCuboidShape(4D, 0D, 3D, 16D, 10D, 13D));
        SHAPE_MAP.put(StumpFacing.X, VoxelShapes.union(SHAPE_MAP.get(StumpFacing.EAST), SHAPE_MAP.get(StumpFacing.WEST)));
        SHAPE_MAP.put(StumpFacing.Z, VoxelShapes.union(SHAPE_MAP.get(StumpFacing.NORTH), SHAPE_MAP.get(StumpFacing.SOUTH)));
    }
}
