package net.kings_of_devs.cozy_camping.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor
    int getJumpingCooldown();

    @Accessor("jumpingCooldown")
    void setJumpingCooldown(int jumpCD);

    @Accessor
    void setMovementSpeed(float speed);
}
