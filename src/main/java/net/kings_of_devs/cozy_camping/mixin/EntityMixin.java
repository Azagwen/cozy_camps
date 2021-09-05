package net.kings_of_devs.cozy_camping.mixin;

import net.kings_of_devs.cozy_camping.entity.BearTrapEntity;
import net.kings_of_devs.cozy_camping.util.BlockDuck;
import net.kings_of_devs.cozy_camping.util.EntityDuck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuck {
    private final Entity self = (Entity) (Object) this;
    @Shadow public World world;
    @Shadow protected boolean onGround;
    @Shadow public boolean noClip;
    @Shadow public abstract boolean isRemoved();
    @Shadow public abstract BlockPos getLandingPos();
    @Shadow public abstract boolean hasVehicle();
    @Shadow public abstract void stopRiding();
    @Shadow public abstract void setPose(EntityPose pose);

    @Inject(method = "move", at = @At("HEAD"))
    public void move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        if (!this.noClip) {
            var blockPos = this.getLandingPos();
            var blockState = this.world.getBlockState(blockPos);
            if (this.isRemoved()) {
                this.world.getProfiler().pop();
            } else {
                var block = (BlockDuck) blockState.getBlock();
                if (this.onGround) {
                    block.onWalkedUpon(this.world, blockPos, blockState, self);
                }
            }
        }
    }

    @Override
    public boolean setTrapped(BearTrapEntity trapEntity) {
        if (this.hasVehicle()) {
            this.stopRiding();
        }

        this.setPose(EntityPose.STANDING);
        trapEntity.addTrappedEntity(self);
        return true;
    }
}
