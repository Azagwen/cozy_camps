package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.entity.BlockEntityRegistry;
import net.kings_of_devs.cozy_camping.block.entity.TrapBlockEntity;
import net.kings_of_devs.cozy_camping.util.BlockDuck;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class TrapBlock extends BlockWithEntity implements BlockDuck {
    public static final BooleanProperty OPEN;
    public static final VoxelShape SHAPE;

    public TrapBlock(Settings settings) {
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
        var blockEntity = (TrapBlockEntity) world.getBlockEntity(pos);
        if (blockEntity != null) {
            if (world.getBlockState(pos).get(OPEN)) {
                world.setBlockState(pos, state.with(OPEN, false));
                world.playSound(null, pos, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1, 0);
                if (entity instanceof ItemEntity item) {
                    item.kill();
                }
                blockEntity.setHeldClosed(true);
            } else {
                if (entity instanceof LivingEntity trappedEntity) {
                    if (blockEntity.isHeldClosed()) {
                        trappedEntity.addStatusEffect(new StatusEffectInstance(CozyCampingMain.TRAPPED, 60));
                    }
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(OPEN)) {
            var blockEntity = (TrapBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null && !blockEntity.isHeldClosed()) {
                world.setBlockState(pos, state.with(OPEN, true));
                world.playSound(null, pos, SoundEvents.BLOCK_COPPER_BREAK, SoundCategory.BLOCKS, 1, 1);
                return ActionResult.SUCCESS;
            }
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
        return world.getBlockState(pos).get(OPEN) && Direction.UP != direction ? 15 : 0;
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return direction == Direction.DOWN ? state.getWeakRedstonePower(world, pos, direction) : 0;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TrapBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntityRegistry.BEAR_TRAP, TrapBlockEntity::tick);
    }

    static {
        OPEN = Properties.OPEN;
        SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.2f, 1f);
    }
}
