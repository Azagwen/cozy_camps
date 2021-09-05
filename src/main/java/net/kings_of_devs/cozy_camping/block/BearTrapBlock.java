package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.entity.BearTrapEntity;
import net.kings_of_devs.cozy_camping.entity.EntityRegistry;
import net.kings_of_devs.cozy_camping.entity.SeatEntity;
import net.kings_of_devs.cozy_camping.util.BlockDuck;
import net.kings_of_devs.cozy_camping.util.EntityDuck;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
import net.minecraft.world.WorldView;

public class BearTrapBlock extends Block implements BlockDuck {
    public static final BooleanProperty OPEN;
    public static final VoxelShape SHAPE;

    public BearTrapBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(OPEN, true));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onWalkedUpon(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            if (world.getBlockState(pos).get(OPEN)) {
                if (entity instanceof ItemEntity item) {
                    item.kill();
                    this.close(world, pos, state);
                    return;
                }

                var active = world.getNonSpectatingEntities(BearTrapEntity.class, new Box(pos));
                if (!active.isEmpty()) {
                    return;
                }

                var posX = (pos.getX() + 8 / 16F);
                var posY = (pos.getY() + 8 / 16F);
                var posZ = (pos.getZ() + 8 / 16F);
                var yaw = entity.getYaw();
                var trapEntity = EntityRegistry.TRAP.create(world);
                trapEntity.updatePositionAndAngles(posX, posY, posZ, yaw, 0);
                trapEntity.setNoGravity(true);
                trapEntity.setSilent(true);
                trapEntity.setInvisible(true);
                trapEntity.setHeadYaw(yaw);
                trapEntity.setBodyYaw(yaw);
                if (world.spawnEntity(trapEntity)) {
                    ((EntityDuck) entity).setTrapped(trapEntity);
                    entity.setBodyYaw(yaw);
                    entity.setHeadYaw(yaw);
                    this.close(world, pos, state);
                }
            }
        }
    }

    private void close(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(OPEN, false));
        world.playSound(null, pos, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1, 0);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(OPEN)) {
            world.setBlockState(pos, state.with(OPEN, true));
            world.playSound(null, pos, SoundEvents.BLOCK_COPPER_BREAK, SoundCategory.BLOCKS, 1, 1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return !state.get(OPEN);
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return !world.getBlockState(pos).get(OPEN) && Direction.UP != direction ? 15 : 0;
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return direction == Direction.DOWN ? state.getWeakRedstonePower(world, pos, direction) : 0;
    }

    static {
        OPEN = Properties.OPEN;
        SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.2f, 1f);
    }
}
