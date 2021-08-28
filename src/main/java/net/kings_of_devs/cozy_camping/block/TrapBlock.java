package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.block.block_entity.TrapBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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

public class TrapBlock extends Block implements BlockEntityProvider {
    public static final BooleanProperty OPEN;
    public static final VoxelShape SHAPE;

    public TrapBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(OPEN, true));
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
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        var blockEntity = (TrapBlockEntity) world.getBlockEntity(pos);
        if (entity.getBlockY() >= pos.getY() && entity.getBlockX() == pos.getX() && entity.getBlockZ() == pos.getZ()) {
            if (world.getBlockState(pos).get(OPEN)) {
                world.setBlockState(pos, state.with(OPEN, false));
                world.playSound(null, pos, SoundEvents.BLOCK_COPPER_BREAK, SoundCategory.BLOCKS, 1, 1);

                if (entity instanceof ItemEntity item) {
                    item.kill();
                } else if (entity instanceof LivingEntity livingEntity) {
                    blockEntity.setTrappedEntity(livingEntity.getUuid());
                }
            } else {
                if (entity instanceof LivingEntity trappedEntity && entity.getUuid() == blockEntity.getTrappedEntity()){
                    trappedEntity.damage(DamageSource.MAGIC, 1);
                    //TODO: add a clean way to "trap" entities
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(OPEN)) {
            var blockEntity = (TrapBlockEntity) world.getBlockEntity(pos);
            blockEntity.setTrappedEntity(null);
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
        return world.getBlockState(pos).get(OPEN) && Direction.UP != direction ? 15 : 0;
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return direction == Direction.DOWN ? state.getWeakRedstonePower(world, pos, direction) : 0;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TrapBlockEntity(pos, state);
    }

    static {
        OPEN = Properties.OPEN;
        SHAPE = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.2f, 1f);
    }
}
