package net.kings_of_devs.cozy_camping.group;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.function.Function;

public class TabbedItemGroup extends AbstractTabbedItemGroup {
    private ItemConvertible iconItem;

    public TabbedItemGroup(Identifier id, Function<TabbedItemGroup, ItemGroup> builder) {
        super(id);
        builder.apply(this);
    }

    public void addTab(ItemConvertible iconItem, String name, Set<Item> itemSet) {
        this.registerTab(iconItem, name, itemSet);
    }

    public void setIcon(ItemConvertible iconItem) {
        this.iconItem = iconItem;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(iconItem);
    }
}