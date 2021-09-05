package net.kings_of_devs.cozy_camping.mixin;

import net.kings_of_devs.cozy_camping.block.SleepingBagBlock;
import net.kings_of_devs.cozy_camping.block.TentBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    private final LivingEntity self = (LivingEntity) (Object) this;

    @Inject(method = "isSleepingInBed", at = @At("HEAD"), cancellable = true)
    private void isSleepingInBag(CallbackInfoReturnable<Boolean> cir) {
        if (self.getSleepingPosition().map((pos) -> {
            var block = self.world.getBlockState(pos).getBlock();
            return block instanceof TentBlock || block instanceof SleepingBagBlock;
        }).orElse(false)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getSleepingDirection", at = @At("HEAD"), cancellable = true)
    public void getSleepingDirection(CallbackInfoReturnable<Direction> cir) {
        var sleepPos = self.getSleepingPosition().orElse(null);
        if (self.world.getBlockState(sleepPos).getBlock() instanceof TentBlock) {
            var direction = sleepPos != null ? TentBlock.getDirection(self.world, sleepPos) : null;
            cir.setReturnValue(direction);
        }
        if (self.world.getBlockState(sleepPos).getBlock() instanceof SleepingBagBlock) {
            var direction = sleepPos != null ? SleepingBagBlock.getDirection(self.world, sleepPos) : null;
            cir.setReturnValue(direction);
        }
    }
}
