package net.kings_of_devs.cozy_camping.block;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.kings_of_devs.cozy_camping.block.state.CozyCampProperties;
import net.kings_of_devs.cozy_camping.block.state.StumpAxis;
import net.kings_of_devs.cozy_camping.block.state.StumpSide;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class StumpBlock extends Block {
    public static final EnumProperty<StumpAxis> AXIS;
    public static final EnumProperty<StumpSide> SIDE;
    private static final VoxelShape VERTICAL_SHAPE;
    private static final VoxelShape Z_POSITIVE;
    private static final VoxelShape Z_NEGATIVE;
    private static final VoxelShape Z_CENTERED;
    private static final VoxelShape Z_CENTERED_LARGE;
    private static final VoxelShape X_POSITIVE;
    private static final VoxelShape X_NEGATIVE;
    private static final VoxelShape X_CENTERED;
    private static final VoxelShape X_CENTERED_LARGE;
    private static final Table<StumpAxis, StumpSide, VoxelShape> SHAPE_TABLE;

    public StumpBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_TABLE.get(state.get(AXIS), state.get(SIDE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, SIDE);
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
        var connectsOnPosX = world.getBlockState(pos.offset(Direction.get(Direction.AxisDirection.POSITIVE, Direction.Axis.X))).getBlock() instanceof StumpBlock;
        var connectsOnNegX = world.getBlockState(pos.offset(Direction.get(Direction.AxisDirection.NEGATIVE, Direction.Axis.X))).getBlock() instanceof StumpBlock;
        var connectsOnPosZ = world.getBlockState(pos.offset(Direction.get(Direction.AxisDirection.POSITIVE, Direction.Axis.Z))).getBlock() instanceof StumpBlock;
        var connectsOnNegZ = world.getBlockState(pos.offset(Direction.get(Direction.AxisDirection.NEGATIVE, Direction.Axis.Z))).getBlock() instanceof StumpBlock;
        var axis = StumpAxis.VERTICAL;
        var side = StumpSide.CENTERED;

        switch (direction.getAxis()) {
            case X -> {
                axis = StumpAxis.X;
                side = this.determineSide(connectsOnPosX, connectsOnNegX);
            }
            case Z -> {
                axis = StumpAxis.Z;
                side = this.determineSide(connectsOnPosZ, connectsOnNegZ);
            }
        }

        return this.getDefaultState().with(AXIS, axis).with(SIDE, side);
    }

    private StumpSide determineSide(boolean hasPositive, boolean hasNegative) {
        if (hasPositive && hasNegative)
            return StumpSide.CENTERED_LARGE;
        else if (hasPositive)
            return StumpSide.POSITIVE;
        else if (hasNegative)
            return StumpSide.NEGATIVE;
        else
            return StumpSide.CENTERED;
    }

    static {
        AXIS = CozyCampProperties.STUMP_AXIS;
        SIDE = CozyCampProperties.STUMP_SIDE;

        VERTICAL_SHAPE = Block.createCuboidShape(3D, 0D, 3D, 13D, 12D, 13D);
        Z_POSITIVE = Block.createCuboidShape(3D, 0D, 0D, 13D, 10D, 12D);
        Z_NEGATIVE = Block.createCuboidShape(3D, 0D, 4D, 13D, 10D, 16D);
        Z_CENTERED = VoxelShapes.union(Z_POSITIVE, Z_NEGATIVE);
        Z_CENTERED_LARGE = VoxelShapes.combine(Z_POSITIVE, Z_NEGATIVE, BooleanBiFunction.AND);
        X_POSITIVE = Block.createCuboidShape(0D, 0D, 3D, 12D, 10D, 13D);
        X_NEGATIVE = Block.createCuboidShape(4D, 0D, 3D, 16D, 10D, 13D);
        X_CENTERED = VoxelShapes.union(X_POSITIVE, X_NEGATIVE);
        X_CENTERED_LARGE = VoxelShapes.combine(X_POSITIVE, X_NEGATIVE, BooleanBiFunction.AND);

        SHAPE_TABLE = HashBasedTable.create();
        SHAPE_TABLE.put(StumpAxis.X, StumpSide.CENTERED, X_CENTERED);
        SHAPE_TABLE.put(StumpAxis.X, StumpSide.CENTERED_LARGE, X_CENTERED_LARGE);
        SHAPE_TABLE.put(StumpAxis.X, StumpSide.POSITIVE, X_POSITIVE);
        SHAPE_TABLE.put(StumpAxis.X, StumpSide.NEGATIVE, X_NEGATIVE);
        SHAPE_TABLE.put(StumpAxis.VERTICAL, StumpSide.CENTERED, VERTICAL_SHAPE);
        SHAPE_TABLE.put(StumpAxis.Z, StumpSide.CENTERED, Z_CENTERED);
        SHAPE_TABLE.put(StumpAxis.Z, StumpSide.CENTERED_LARGE, Z_CENTERED_LARGE);
        SHAPE_TABLE.put(StumpAxis.Z, StumpSide.POSITIVE, Z_POSITIVE);
        SHAPE_TABLE.put(StumpAxis.Z, StumpSide.NEGATIVE, Z_NEGATIVE);
    }
}
