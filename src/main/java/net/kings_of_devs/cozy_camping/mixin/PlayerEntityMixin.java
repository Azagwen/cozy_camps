package net.kings_of_devs.cozy_camping.mixin;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.block.TrapBlock;
import net.kings_of_devs.cozy_camping.block.entity.TrapBlockEntity;
import net.kings_of_devs.cozy_camping.util.PlayerEntityDuck;
import net.kings_of_devs.cozy_camping.block.TentBlock;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityDuck {
    private final PlayerEntity self = (PlayerEntity) (Object) this;
    @Shadow private int sleepTimer;

    @ModifyArg(method = "tickMovement", at =
    @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setMovementSpeed(F)V", args = {"log=false"}), index = 0)
    private float modifyMovementSpeed(float input) {
        return this.getSpeed(input);
    }

    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(this.getSpeed((float) self.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
    }

    private float getSpeed(float input) {
        return self.getMainHandStack().isOf(ItemRegistry.WALKING_STICK) ? (input * 1.4F) : input;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        var pos = self.getBlockPos();
        var state = self.world.getBlockState(pos);
        if (!state.isOf(BlockRegistry.BEAR_TRAP)) {
            self.removeStatusEffect(CozyCampingMain.TRAPPED);
        }
    }

    @Override
    public void sleepFromTent(BlockPos pos, boolean isLeft) {
        if (self.hasVehicle()) {
           self.stopRiding();
        }

        var state = self.world.getBlockState(pos);
        var x = (pos.getX() + 0.5D);
        var y = (pos.getY() + 0.1D);
        var z = (pos.getZ() + 0.5D);
        if (state.getBlock() instanceof TentBlock) {
            self.world.setBlockState(pos, state.with(isLeft ? TentBlock.LEFT_OCCUPIED : TentBlock.RIGHT_OCCUPIED, true), Block.NOTIFY_ALL);
            switch (state.get(TentBlock.FACING)) {
                case NORTH -> {
                    z += 1;
                    x += 0.6F;
                }
                case SOUTH -> {
                    z -= 1;
                    x -= 0.6F;
                }
                case EAST -> {
                    z += 0.6F;
                    x -= 1;
                }
                case WEST -> {
                    z -= 0.6F;
                    x += 1;
                }
            }
        }

        self.setPose(EntityPose.SLEEPING);
        self.setPosition(x, y, z);
        self.setSleepingPosition(pos);
        self.setVelocity(Vec3d.ZERO);
        self.velocityDirty = true;
        this.sleepTimer = 0;
    }
}
