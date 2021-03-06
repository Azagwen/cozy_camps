package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Set;

public class BlockRegistryUtil {

    /**
     * Registers a {@link Block}.
     *
     * @param path      A String representing the name of the block, the "path".
     * @param block     The {@link Block} object you want to register.
     * @param item      The {@link Item} Object corresponding to the Block you want to register.
     * @param <B>       The Type of the said Block (implicit most of the time).
     * @param <I>       The Type of the said Block's Item (implicit most of the time).
     */
    protected static <B extends Block, I extends Item> void registerBlock(Set<Item> itemGroupTab, String path, B block, I item) {
        var id = CozyCampingMain.id(path);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, item);
        itemGroupTab.add(item);
    }

    /**
     *
     * @param path      A String representing the name of the block, the "path".
     * @param block     The {@link Block} object you want to register
     * @param <B>       The Type of the said Block (implicit most of the time).
     */
    protected static <B extends Block> void registerBlockNoItem(String path, B block) {
        var id = CozyCampingMain.id(path);
        Registry.register(Registry.BLOCK, id, block);
    }

    /**
     * Registers a {@link Block} with a regular {@link BlockItem}.
     *
     * @param path      A String representing the name of the block, the "path".
     * @param block     The {@link Block} object you want to register.
     * @param <B>       The Type of the said Block (implicit most of the time).
     */
    protected static <B extends Block> void registerBlock(Set<Item> itemGroupTab, String path, B block) {
        var item = new BlockItem(block, new Item.Settings());
        registerBlock(itemGroupTab, path, block, item);
    }

    protected static <B extends Block> void registerColoredBlocks(Set<Item> itemGroupTab, String name, Map<DyeColor, B> blockMap) {
        for (var entry : blockMap.entrySet()) {
            registerBlock(itemGroupTab, entry.getKey().getName()+"_"+name, entry.getValue());
        }
    }
}
