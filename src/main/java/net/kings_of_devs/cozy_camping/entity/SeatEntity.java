package net.kings_of_devs.cozy_camping.entity;

import net.kings_of_devs.cozy_camping.block.StumpBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SeatEntity extends MobEntity {

    public SeatEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        if (!this.hasPassengers())
            this.discard();
        else if (this.world.getBlockState(this.getBlockPos()).getBlock() instanceof StumpBlock)
            super.tick();
        else {
            this.removeAllPassengers();
            this.discard();
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.setVelocity(Vec3d.ZERO);
    }

    @Override
    public boolean isAlive() {
        return !this.isRemoved();
    }

    public static DefaultAttributeContainer.Builder getAttributeContainer() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 0);

    }
}
