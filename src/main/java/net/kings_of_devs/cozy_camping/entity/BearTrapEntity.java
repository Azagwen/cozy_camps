package net.kings_of_devs.cozy_camping.entity;

import com.google.common.collect.Lists;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BearTrapBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * This is not working as intended, please help T-T
 */
public class BearTrapEntity extends MobEntity {
    private List<Entity> victims;

    public BearTrapEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.victims = Lists.newArrayList();
    }

    @Override
    public void tick() {
        var state = this.world.getBlockState(this.getBlockPos());

        if (!this.hasVictims()) {
            this.discard();
        }
        else if (state.getBlock() instanceof BearTrapBlock && !state.get(BearTrapBlock.OPEN)) {
            super.tick();
            for (var entity : this.victims) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(CozyCampingMain.TRAPPED, 40));
                }
                this.updateTrappedEntityPosition(entity);
            }
            System.out.println("trap tick tick");
        }
    }

    public void updateTrappedEntityPosition(Entity entity) {
        entity.setPosition(this.getX(), this.getY(), this.getZ());
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

    public boolean hasVictims() {
        return !this.victims.isEmpty();
    }

    public void addTrappedEntity(Entity entity) {
        this.victims.add(entity);
    }

    public void freeTrappedEntities() {
        this.victims.clear();
        this.discard();
    }

    public static DefaultAttributeContainer.Builder getAttributeContainer() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 0);

    }
}
