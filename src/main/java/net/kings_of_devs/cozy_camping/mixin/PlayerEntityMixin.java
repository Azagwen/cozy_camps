package net.kings_of_devs.cozy_camping.mixin;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private final PlayerEntity self = (PlayerEntity) (Object) this;

    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if (self.getMainHandStack().isOf(Items.STICK)) {
            cir.setReturnValue((float) (self.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 3));
        }
        cir.setReturnValue((float) self.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
    }
}
