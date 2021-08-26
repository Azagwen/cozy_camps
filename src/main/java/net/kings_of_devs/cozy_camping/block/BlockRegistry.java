package net.kings_of_devs.cozy_camping.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;

public class BlockRegistry {

    public static final Block TENT = new TentBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));
    public static final Block RIPPED_TENT = new TentBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));
    public static final Block FANCY_TENT = new TentBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));
    public static final Block OAK_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block SPRUCE_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block BIRCH_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block JUNGLE_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block ACACIA_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block DARK_OAK_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block CRIMSON_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block WARPED_STUMP = new StumpBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS).breakByTool(FabricToolTags.AXES));
    public static final Block CHARCOAL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK).requiresTool().breakByTool(FabricToolTags.PICKAXES));
    public static final Block OPEN_TRAP = new TrapBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool().breakByTool(FabricToolTags.PICKAXES));
    public static final Block HEATHER = new HeatherBlock(FabricBlockSettings.copyOf(Blocks.GRASS));

    public static void init() {
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "tent", TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "ripped_tent", RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "fancy_tent", FANCY_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "oak_stump", OAK_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "spruce_stump", SPRUCE_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "birch_stump", BIRCH_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "jungle_stump", JUNGLE_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "acacia_stump", ACACIA_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "dark_oak_stump", DARK_OAK_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "crimson_stump", CRIMSON_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "warped_stump", WARPED_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "charcoal_block", CHARCOAL_BLOCK);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "bear_trap", OPEN_TRAP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "heather", HEATHER);

        CozyCampingMain.LOGGER.info("Cozy Camping Blocks Registered!");
    }
}
