package net.kings_of_devs.cozy_camping.group;


import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CozyCampingItemGroup extends TabbedItemGroup {
    public final ItemGroupTab COZY_CAMPING_BLOCKS = this.registerTab(Blocks.BEDROCK, "blocks", CozyCampingMain.BLOCKS_TAB);
    public final ItemGroupTab COZY_CAMPING_MISC = this.registerTab(Blocks.BEDROCK, "misc", CozyCampingMain.MISC_TAB);

    public CozyCampingItemGroup(Identifier id) {
        super(id);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BlockRegistry.CHARCOAL_BLOCK);
    }
}