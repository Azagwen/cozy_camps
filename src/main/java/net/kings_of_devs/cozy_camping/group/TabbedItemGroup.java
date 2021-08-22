package net.kings_of_devs.cozy_camping.group;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Set;

public abstract class TabbedItemGroup extends ItemGroup {
    private int selectedTab = 0;
    private final List<ItemGroupTab> tabs = Lists.newArrayList();
    private boolean hasInitialized = false;

    protected TabbedItemGroup(Identifier id) {
        super(createTabIndex(), String.format("%s.%s", id.getNamespace(), id.getPath()));
    }

    public void initialize() {
        hasInitialized = true;
    }

    protected ItemGroupTab registerTab(ItemConvertible item, String name, Set<Item> itemSet) {
        var tab = new ItemGroupTab(new ItemStack(item), name, itemSet);
        this.tabs.add(tab);
        return tab;
    };

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        for (var item : Registry.ITEM) {
            if (getSelectedTab().matches(item)) {
                stacks.add(new ItemStack(item));
            }
        }
    }

    public ItemGroupTab getSelectedTab() {
        return tabs.get(selectedTab);
    }

    public List<ItemGroupTab> getTabs() {
        return tabs;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public int getSelectedTabIndex() {
        return selectedTab;
    }

    public boolean hasInitialized() {
        return hasInitialized;
    }

    private static int createTabIndex() {
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }
}