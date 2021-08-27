package net.kings_of_devs.cozy_camping.block.block_entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class BlockEntityRegistry {
    public static BlockEntityType<TrapBlockEntity> TRAP_BLOCK_ENTITY;

    public static void init(){
        TRAP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "trap_block_entity", FabricBlockEntityTypeBuilder.create(TrapBlockEntity::new, BlockRegistry.BEAR_TRAP).build(null));
    }

}
