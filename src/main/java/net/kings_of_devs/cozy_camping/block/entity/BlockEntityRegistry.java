package net.kings_of_devs.cozy_camping.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistry {
    public static BlockEntityType<TentBlockEntity> TENT;

    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity> BlockEntityType<E> registerBlockEntity(String name, FabricBlockEntityTypeBuilder.Factory<? extends E> factory, Block... blocks) {
        var builder = FabricBlockEntityTypeBuilder.create(factory, blocks).build(null);
        return (BlockEntityType<E>) Registry.register(Registry.BLOCK_ENTITY_TYPE, name, builder);
    }

    public static void init(){
        TENT = registerBlockEntity("tent", TentBlockEntity::new, BlockRegistry.TENTS.toArray(Block[]::new));
    }
}
