package net.kings_of_devs.cozy_camping.item;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public class ItemRegistryUtil {

    /**
     * Registers a {@link Item}.
     *
     * @param path      A String representing the name of the Item, the "path".
     * @param item      The {@link Item} object you want to register.
     */
    protected static void registerItem(Set<Item> itemGroupTab, String path, Item item) {
        var id = CozyCampingMain.id(path);
        Registry.register(Registry.ITEM, id, item);
        itemGroupTab.add(item);
    }

}
