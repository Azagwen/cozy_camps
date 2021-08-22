package net.kings_of_devs.cozy_camping.group;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

public record ItemGroupTab(ItemStack icon, String name, Set<Item> itemTag) {

    public ItemStack getIcon() {
        return icon;
    }

    public boolean matches(Item item) {
        return itemTag == null || itemTag.contains(item);
    }

    public boolean matches(ItemStack stack) {
        return matches(stack.getItem());
    }

    public String getTranslationKey() {
        return "itemGroup.subTab." + CozyCampingMain.MOD_ID + "." + name;
    }
}