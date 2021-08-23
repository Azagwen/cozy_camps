package net.kings_of_devs.cozy_camping.item;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public class ItemRegistryUtil {

    /**
     * Registers a {@link Item}.
     *
     * @param path      A String representing the name of the block, the "path".
     * @param item      The {@link Item} Object corresponding to the Block you want to register.
     */
    protected static <I extends Item> void registerItem(Set<Item> itemGroupTab, String path, I item) {
        var id = CozyCampingMain.id(path);
        Registry.register(Registry.ITEM, id, item);
        itemGroupTab.add(item);
    }

}
