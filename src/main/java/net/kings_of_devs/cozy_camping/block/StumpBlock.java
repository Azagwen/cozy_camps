package net.kings_of_devs.cozy_camping.block;

import com.google.common.collect.Maps;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.state.CozyCampProperties;
import net.kings_of_devs.cozy_camping.block.state.StumpShape;
import net.kings_of_devs.cozy_camping.entity.EntityRegistry;
import net.kings_of_devs.cozy_camping.entity.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class StumpBlock extends Block implements Waterloggable {
    public static final EnumProperty<StumpShape> SHAPE;
    public static final BooleanProperty WATERLOGGED;
    private static final Map<StumpShape, VoxelShape> SHAPE_MAP;

    public StumpBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(SHAPE, StumpShape.VERTICAL));
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_MAP.get(state.get(SHAPE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.hasVehicle() || player.isSpectator() || !player.getMainHandStack().isEmpty()) {
                return ActionResult.FAIL;
            }

            var posX = (pos.getX() + 8 / 16F);
            var posY = (pos.getY() + (state.get(SHAPE) == StumpShape.VERTICAL ? 8 / 16F : 6 / 16F));
            var posZ = (pos.getZ() + 8 / 16F);

            var active = world.getNonSpectatingEntities(SeatEntity.class, new Box(pos));
            if (!active.isEmpty()) {
                return ActionResult.FAIL;
            }

            var yaw = player.getHorizontalFacing().asRotation();
            var entity = EntityRegistry.SEAT.create(world);
            entity.updatePositionAndAngles(posX, posY, posZ, yaw, 0);
            entity.setNoGravity(true);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setHeadYaw(yaw);
            entity.setBodyYaw(yaw);
            if (world.spawnEntity(entity)) {
                player.startRiding(entity, true);
                player.setBodyYaw(yaw);
                player.setHeadYaw(yaw);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis() == state.get(SHAPE).getAxis()) {
            return this.getPlacementState(state, world, pos, direction);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos(), ctx.getSide());
    }

    private BlockState getPlacementState(BlockState state, WorldAccess world, BlockPos pos, Direction direction) {
        var axis = direction.getAxis();
        var positiveState = world.getBlockState(pos.offset(Direction.get(Direction.AxisDirection.POSITIVE, axis)));
        var negativeState = world.getBlockState(pos.offset(Direction.get(Direction.AxisDirection.NEGATIVE, axis)));
        var connectsOnPos = positiveState.getBlock() instanceof StumpBlock && positiveState.get(SHAPE).getAxis() == axis;
        var connectsOnNeg = negativeState.getBlock() instanceof StumpBlock && negativeState.get(SHAPE).getAxis() == axis;
        var side = this.determineSide(state, axis, connectsOnPos, connectsOnNeg);

        return state.with(SHAPE, side);
    }

    private StumpShape determineSide(BlockState state, Direction.Axis axis, boolean hasPositive, boolean hasNegative) {
        var checkNegative = (hasNegative ? StumpShape.NEGATIVE : StumpShape.CENTER);
        var checkPositive = (hasPositive ? StumpShape.POSITIVE : checkNegative);
        var side = (hasPositive && hasNegative ? StumpShape.CENTER_LARGE : checkPositive);

        switch (axis) {
            case X -> side = side * StumpShape.X_MULTIPLIER;
            case Z -> side = side * StumpShape.Z_MULTIPLIER;
        }

        return StumpShape.SHAPE_MAP.get(side) != null ? StumpShape.SHAPE_MAP.get(side) : state.get(SHAPE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    static {
        SHAPE = CozyCampProperties.STUMP_SHAPE;
        WATERLOGGED = Properties.WATERLOGGED;

        SHAPE_MAP = Maps.newHashMap();
        SHAPE_MAP.put(StumpShape.VERTICAL, Block.createCuboidShape(3D, 0D, 3D, 13D, 12D, 13D));
        SHAPE_MAP.put(StumpShape.X_POSITIVE, Block.createCuboidShape(4D, 0D, 3D, 16D, 10D, 13D));
        SHAPE_MAP.put(StumpShape.X_NEGATIVE, Block.createCuboidShape(0D, 0D, 3D, 12D, 10D, 13D));
        SHAPE_MAP.put(StumpShape.X_CENTERED, Block.createCuboidShape(2D, 0D, 3D, 14D, 10D, 13D));
        SHAPE_MAP.put(StumpShape.X_CENTERED_LARGE, VoxelShapes.union(SHAPE_MAP.get(StumpShape.X_POSITIVE), SHAPE_MAP.get(StumpShape.X_NEGATIVE)));
        SHAPE_MAP.put(StumpShape.Z_POSITIVE, Block.createCuboidShape(3D, 0D, 4D, 13D, 10D, 16D));
        SHAPE_MAP.put(StumpShape.Z_NEGATIVE, Block.createCuboidShape(3D, 0D, 0D, 13D, 10D, 12D));
        SHAPE_MAP.put(StumpShape.Z_CENTERED, Block.createCuboidShape(3D, 0D, 2D, 13D, 10D, 14D));
        SHAPE_MAP.put(StumpShape.Z_CENTERED_LARGE, VoxelShapes.union(SHAPE_MAP.get(StumpShape.Z_POSITIVE), SHAPE_MAP.get(StumpShape.Z_NEGATIVE)));
    }
}
