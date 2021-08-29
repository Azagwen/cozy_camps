package net.kings_of_devs.cozy_camping.mixin.accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor
    int getJumpingCooldown();

    @Accessor("jumpingCooldown")
    void setJumpingCooldown(int jumpCD);

    @Accessor
    void setMovementSpeed(float speed);

}
