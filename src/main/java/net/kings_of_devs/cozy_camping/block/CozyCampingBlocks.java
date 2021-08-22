package net.kings_of_devs.cozy_camping.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class CozyCampingBlocks {

    /**
     * Registers a {@link Block}.
     *
     * @param path      A String representing the name of the block, the "path".
     * @param block     The {@link Block} object you want to register.
     * @param item      The {@link Item} Object corresponding to the Block you want to register.
     * @param <B>       The Type of the said Block (implicit most of the time).
     */
    private static <B extends Block, I extends Item> void registerBlock(String path, B block, I item) {
        var id = CozyCampingMain.id(path);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, item);
    }

    /**
     * Registers a {@link Block} with a regular {@link BlockItem}.
     *
     * @param path      A String representing the name of the block, the "path".
     * @param block     The {@link Block} object you want to register.
     * @param <B>       The Type of the said Block (implicit most of the time).
     */
    private static <B extends Block> void registerBlock(String path, B block) {
        var item = new BlockItem(block, new Item.Settings());
        registerBlock(path, block, item);
    }

    public static void init() {
        var block = new Block(FabricBlockSettings.copyOf(Blocks.STONE));

        registerBlock("test", block);
    }
}
