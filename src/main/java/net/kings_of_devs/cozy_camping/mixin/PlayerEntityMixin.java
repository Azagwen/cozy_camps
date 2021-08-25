package net.kings_of_devs.cozy_camping.mixin;

import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    private final PlayerEntity self = (PlayerEntity) (Object) this;

    @ModifyArg(method = "tickMovement", at =
    @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setMovementSpeed(F)V", args = {"log=true"}), index = 0)
    private float modifyMovementSpeed(float in) {
        return this.getSpeed(in);
    }

    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(this.getSpeed());
    }

    private float getSpeed(float input) {
        return self.getMainHandStack().isOf(ItemRegistry.WALKING_STICK) ? (input * 3) : input;
    }

    private float getSpeed() {
        return this.getSpeed((float) self.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
    }
}
