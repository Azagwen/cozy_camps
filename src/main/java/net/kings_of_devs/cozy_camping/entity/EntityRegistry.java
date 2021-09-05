package net.kings_of_devs.cozy_camping.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static final EntityType<SeatEntity> SEAT = FabricEntityTypeBuilder.create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().disableSummon().build();
    public static final EntityType<BearTrapEntity> TRAP = FabricEntityTypeBuilder.create(SpawnGroup.MISC, BearTrapEntity::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().disableSummon().build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, CozyCampingMain.id("seat"), SEAT);
        Registry.register(Registry.ENTITY_TYPE, CozyCampingMain.id("bear_trap"), TRAP);

        initAttributes();
    }

    public static void initAttributes() {
        FabricDefaultAttributeRegistry.register(SEAT, SeatEntity.getAttributeContainer());
        FabricDefaultAttributeRegistry.register(TRAP, BearTrapEntity.getAttributeContainer());
    }
}
