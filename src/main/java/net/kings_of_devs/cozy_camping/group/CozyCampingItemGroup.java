package net.kings_of_devs.cozy_camping.group;


import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class CozyCampingItemGroup extends TabbedItemGroup {

    public CozyCampingItemGroup(Identifier id) {
        super(id);
    }

    public static ItemGroupTab COZY_CAMPING_BLOCKS = new ItemGroupTab(new ItemStack(BlockRegistry.CHARCOAL_BLOCK), "blocks", CozyCampingMain.BLOCKS_TAB);
    public static ItemGroupTab COZY_CAMPING_MISC = new ItemGroupTab(new ItemStack(BlockRegistry.CHARCOAL_BLOCK), "misc", CozyCampingMain.MISC_TAB);


    @Override
    public void initTabs(List<ItemGroupTab> tabs) {
        tabs.add(COZY_CAMPING_BLOCKS);
        tabs.add(COZY_CAMPING_MISC);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BlockRegistry.CHARCOAL_BLOCK);
    }
}