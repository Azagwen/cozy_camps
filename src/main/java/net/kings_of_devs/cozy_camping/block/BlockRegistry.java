package net.kings_of_devs.cozy_camping.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;

public class BlockRegistry {
    public static final Block CHARCOAL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK).requiresTool().breakByTool(FabricToolTags.PICKAXES));

    public static void init() {
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "charcoal_block", CHARCOAL_BLOCK);
        FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK, 16000); //this makes the charcoal block be a fuel, with the same duration as a normal coal block.
    }
}
