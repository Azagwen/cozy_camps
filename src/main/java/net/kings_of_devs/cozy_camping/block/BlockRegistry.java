package net.kings_of_devs.cozy_camping.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BlockRegistry {

    public static final Block TEST_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE));

    public static void init() {
        BlockRegistryUtil.registerBlock("test", TEST_BLOCK);
    }
}
