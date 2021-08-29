package net.kings_of_devs.cozy_camping.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

import java.util.Set;

public class BlockRegistry {
    public static final Set<Block> TENTS = Sets.newHashSet();

    private static Block createStump(Block copiedBlock) {
        var material = copiedBlock.getDefaultState().getMaterial();
        return new StumpBlock(FabricBlockSettings.of(material, copiedBlock.getDefaultMapColor()).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.WOOD));
    }

    private static Block createTent(DyeColor color) {
        var block = new TentBlock(color, FabricBlockSettings.of(Material.WOOL, color.getMapColor()).sounds(BlockSoundGroup.WOOD).nonOpaque());
        TENTS.add(block);
        return block;
    }

    public static final Block CHARCOAL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK).requiresTool().breakByTool(FabricToolTags.PICKAXES));

    public static final Block OAK_STUMP = createStump(Blocks.OAK_PLANKS);
    public static final Block SPRUCE_STUMP = createStump(Blocks.SPRUCE_PLANKS);
    public static final Block BIRCH_STUMP = createStump(Blocks.BIRCH_PLANKS);
    public static final Block JUNGLE_STUMP = createStump(Blocks.JUNGLE_PLANKS);
    public static final Block ACACIA_STUMP = createStump(Blocks.ACACIA_PLANKS);
    public static final Block DARK_OAK_STUMP = createStump(Blocks.DARK_OAK_PLANKS);
    public static final Block CRIMSON_STUMP = createStump(Blocks.CRIMSON_PLANKS);
    public static final Block WARPED_STUMP = createStump(Blocks.WARPED_PLANKS);

    public static final Block WHITE_TENT = createTent(DyeColor.WHITE);
    public static final Block ORANGE_TENT = createTent(DyeColor.ORANGE);
    public static final Block MAGENTA_TENT = createTent(DyeColor.MAGENTA);
    public static final Block LIGHT_BLUE_TENT = createTent(DyeColor.LIGHT_BLUE);
    public static final Block YELLOW_TENT = createTent(DyeColor.YELLOW);
    public static final Block LIME_TENT = createTent(DyeColor.LIME);
    public static final Block PINK_TENT = createTent(DyeColor.PINK);
    public static final Block GRAY_TENT = createTent(DyeColor.GRAY);
    public static final Block LIGHT_GRAY_TENT = createTent(DyeColor.LIGHT_GRAY);
    public static final Block CYAN_TENT = createTent(DyeColor.CYAN);
    public static final Block PURPLE_TENT = createTent(DyeColor.PURPLE);
    public static final Block BLUE_TENT = createTent(DyeColor.BLUE);
    public static final Block BROWN_TENT = createTent(DyeColor.BROWN);
    public static final Block GREEN_TENT = createTent(DyeColor.GREEN);
    public static final Block RED_TENT = createTent(DyeColor.RED);
    public static final Block BLACK_TENT = createTent(DyeColor.BLACK);

    public static final Block WHITE_RIPPED_TENT = createTent(DyeColor.WHITE);
    public static final Block ORANGE_RIPPED_TENT = createTent(DyeColor.ORANGE);
    public static final Block MAGENTA_RIPPED_TENT = createTent(DyeColor.MAGENTA);
    public static final Block LIGHT_BLUE_RIPPED_TENT = createTent(DyeColor.LIGHT_BLUE);
    public static final Block YELLOW_RIPPED_TENT = createTent(DyeColor.YELLOW);
    public static final Block LIME_RIPPED_TENT = createTent(DyeColor.LIME);
    public static final Block PINK_RIPPED_TENT = createTent(DyeColor.PINK);
    public static final Block GRAY_RIPPED_TENT = createTent(DyeColor.GRAY);
    public static final Block LIGHT_GRAY_RIPPED_TENT = createTent(DyeColor.LIGHT_GRAY);
    public static final Block CYAN_RIPPED_TENT = createTent(DyeColor.CYAN);
    public static final Block PURPLE_RIPPED_TENT = createTent(DyeColor.PURPLE);
    public static final Block BLUE_RIPPED_TENT = createTent(DyeColor.BLUE);
    public static final Block BROWN_RIPPED_TENT = createTent(DyeColor.BROWN);
    public static final Block GREEN_RIPPED_TENT = createTent(DyeColor.GREEN);
    public static final Block RED_RIPPED_TENT = createTent(DyeColor.RED);
    public static final Block BLACK_RIPPED_TENT = createTent(DyeColor.BLACK);

    public static final Block FANCY_TENT = new TentBlock(DyeColor.WHITE, FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).nonOpaque());

    public static final Block SLEEPING_BAG = new SleepingBagBlock(FabricBlockSettings.copyOf(OAK_STUMP));

    public static final Block BEAR_TRAP = new TrapBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());

    public static final Block BOULDER = new BoulderBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());
    public static final Block GRANITE_BOULDER = new BoulderBlock(FabricBlockSettings.copyOf(Blocks.GRANITE).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());
    public static final Block DIORITE_BOULDER = new BoulderBlock(FabricBlockSettings.copyOf(Blocks.DIORITE).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());
    public static final Block ANDESITE_BOULDER = new BoulderBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());
    public static final Block COBBLESTONE_BOULDER = new BoulderBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());
    public static final Block MOSSY_BOULDER = new BoulderBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_COBBLESTONE).requiresTool().breakByTool(FabricToolTags.PICKAXES).nonOpaque());

    public static final Block HEATHER = new HeatherBlock(FabricBlockSettings.copyOf(Blocks.GRASS));

    public static void init() {
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "charcoal_block", CHARCOAL_BLOCK);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "oak_stump", OAK_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "spruce_stump", SPRUCE_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "birch_stump", BIRCH_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "jungle_stump", JUNGLE_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "acacia_stump", ACACIA_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "dark_oak_stump", DARK_OAK_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "crimson_stump", CRIMSON_STUMP);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "warped_stump", WARPED_STUMP);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "white_tent", WHITE_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "orange_tent", ORANGE_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "magenta_tent", MAGENTA_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "light_blue_tent", LIGHT_BLUE_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "yellow_tent", YELLOW_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "lime_tent", LIME_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "pink_tent", PINK_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "gray_tent", GRAY_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "light_gray_tent", LIGHT_GRAY_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "cyan_tent", CYAN_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "purple_tent", PURPLE_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "blue_tent", BLUE_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "brown_tent", BROWN_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "green_tent", GREEN_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "red_tent", RED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "black_tent", BLACK_TENT);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "white_ripped_tent", WHITE_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "orange_ripped_tent", ORANGE_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "magenta_ripped_tent", MAGENTA_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "light_blue_ripped_tent", LIGHT_BLUE_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "yellow_ripped_tent", YELLOW_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "lime_ripped_tent", LIME_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "pink_ripped_tent", PINK_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "gray_ripped_tent", GRAY_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "light_gray_ripped_tent", LIGHT_GRAY_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "cyan_ripped_tent", CYAN_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "purple_ripped_tent", PURPLE_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "blue_ripped_tent", BLUE_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "brown_ripped_tent", BROWN_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "green_ripped_tent", GREEN_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "red_ripped_tent", RED_RIPPED_TENT);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "black_ripped_tent", BLACK_RIPPED_TENT);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "fancy_tent", FANCY_TENT);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "sleeping_bag", SLEEPING_BAG);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "bear_trap", BEAR_TRAP);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "stone_boulder", BOULDER);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "granite_boulder", GRANITE_BOULDER);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "diorite_boulder", DIORITE_BOULDER);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "andesite_boulder", ANDESITE_BOULDER);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "cobblestone_boulder", COBBLESTONE_BOULDER);
        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "mossy_cobblestone_boulder", MOSSY_BOULDER);

        BlockRegistryUtil.registerBlock(CozyCampingMain.BLOCKS_TAB, "heather", HEATHER);

        CozyCampingMain.LOGGER.info("Cozy Camping Blocks Registered!");
    }
}
