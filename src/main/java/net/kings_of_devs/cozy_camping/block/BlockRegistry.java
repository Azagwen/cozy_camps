package net.kings_of_devs.cozy_camping.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BlockRegistry {
    public static final Block OAK_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block SPRUCE_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block BIRCH_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block JUNGLE_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block ACACIA_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block DARK_OAK_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block CRIMSON_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block WARPED_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block CHARCOAL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK).requiresTool().breakByTool(FabricToolTags.PICKAXES));

    public static void init() {
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "oak_stump", OAK_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "spruce_stump", SPRUCE_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "birch_stump", BIRCH_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "jungle_stump", JUNGLE_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "acacia_stump", ACACIA_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "dark_oak_stump", DARK_OAK_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "crimson_stump", CRIMSON_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "warped_stump", WARPED_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "charcoal_block", CHARCOAL_BLOCK);

        CozyCampingMain.LOGGER.info("Cozy Camping Blocks Registered!");
    }
}
