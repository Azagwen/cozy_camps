package net.kings_of_devs.cozy_camping;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.HashSet;

public class CozyCampingTags {

    public static class BlockTags {
    }

    public static class ItemTags {
    }

    public static Tag<Item> blockItemTag(Tag<Block> blockTag) {
        var set = new HashSet<Item>();
        blockTag.values().forEach((block) -> set.add(block.asItem()));
        return Tag.of(set);
    }

    public static Tag<Block> registerBlockTag(String path) {
        return TagRegistry.block(CozyCampingMain.id(path));
    }

    public static Tag<Block> registerBlockTag(Identifier id) {
        return TagRegistry.block(id);
    }

    public static Tag<Item> registerItemTag(String path) {
        return TagRegistry.item(CozyCampingMain.id(path));
    }
}
