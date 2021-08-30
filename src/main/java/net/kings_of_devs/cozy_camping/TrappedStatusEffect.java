package net.kings_of_devs.cozy_camping;

import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.mixin.accessor.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class TrappedStatusEffect extends StatusEffect {

    public TrappedStatusEffect() {
        super(StatusEffectType.HARMFUL,0x572623);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "c46a844e-bd99-4e83-8bed-b0f837af4e1d", -1.0D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.MAGIC, 2);
        ((LivingEntityAccessor) entity).setJumpingCooldown(60);
    }
}