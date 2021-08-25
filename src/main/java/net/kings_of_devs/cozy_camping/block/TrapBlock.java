package net.kings_of_devs.cozy_camping.block;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TrapBlock extends Block {

    public static final BooleanProperty CLOSED = BooleanProperty.of("closed");

    public TrapBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(CLOSED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(CLOSED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        //return state.get(CLOSED) ? VoxelShapes.fullCube() : VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.5f, 1f);
        return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.2f, 1f);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!world.getBlockState(pos).get(CLOSED)){
            world.setBlockState(pos, state.with(CLOSED, true));
            entity.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1, 1);
            if(entity instanceof ItemEntity item) item.kill();
        } else {
            if(entity instanceof LivingEntity trappedEntity){
                trappedEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 255, true, false));
                trappedEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 1, true, false));
                trappedEntity.damage(DamageSource.MAGIC, 1);
            }
        }
    }

    /*@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.setBlockState(pos, state.with(CLOSED, !world.getBlockState(pos).get(CLOSED)));
        player.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1, 1);

        return ActionResult.SUCCESS;
    }*/
}
