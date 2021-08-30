package net.kings_of_devs.cozy_camping.datagen.recipe.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.datagen.RecipeDatagen;
import net.kings_of_devs.cozy_camping.datagen.recipe.util.RecipeData;
import net.kings_of_devs.cozy_camping.datagen.recipe.util.RecipePatterns;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RecipeRegistry {
    public static final Logger LOGGER = LogManager.getLogger("Cozy Camping Recipes");
    public static final RecipePatterns patterns = new RecipePatterns();
    public static final List<Recipe<?>> dyingRecipes = Lists.newArrayList();

    public static Recipe<?> registerShapedRecipe(RecipeData recipeData, String[] pattern, Multimap<Character, Ingredient> keys) {
        var recipe = (Recipe<?>) null;
        var suffix = recipeData.suffix();
        var category = recipeData.category();
        var group = recipeData.group();
        var result = recipeData.result();
        var count = recipeData.count();

        for (var line : pattern) {
            if (line.contains(" ")) {
                keys.put(' ', Ingredient.EMPTY);
            }
        }

        var resultId = Registry.ITEM.getId(result.asItem());
        var recipeId = CozyCampingMain.id(resultId.getPath() + (suffix.equals("") ? "" : ("_" + suffix)));
        recipe = RecipeDatagen.shapedRecipe(recipeId, group, pattern, keys, result.asItem(), count);
        RecipeDatagen.registerRecipe(recipe, category);
        return recipe;
    }

    public static void init() {
        //Unique patterns
        registerTentRecipes();
        registerStumpRecipes();
        registerMisc();

        //Other Types
        StonecuttingRecipeRegistry.init();
        FurnaceRecipeRegistry.init();

        LOGGER.info("ATBYW Recipes Inintiliazed");
    }

    private static void registerStumpRecipes() {
        var stumpMap = Maps.<RecipeData, ItemConvertible>newHashMap();
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.OAK_STUMP, 4), Blocks.OAK_LOG);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.SPRUCE_STUMP, 4), Blocks.SPRUCE_LOG);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.BIRCH_STUMP, 4), Blocks.BIRCH_LOG);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.JUNGLE_STUMP, 4), Blocks.JUNGLE_LOG);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.ACACIA_STUMP, 4), Blocks.ACACIA_LOG);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.DARK_OAK_STUMP, 4), Blocks.DARK_OAK_LOG);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.CRIMSON_STUMP, 4), Blocks.CRIMSON_STEM);
        stumpMap.put(new RecipeData("stumps", "stumps", BlockRegistry.WARPED_STUMP, 4), Blocks.WARPED_STEM);

        for (var entry : stumpMap.entrySet()) {
            var keys = HashMultimap.<Character, Ingredient>create();
            keys.put('L', Ingredient.ofItems(entry.getValue()));
            registerShapedRecipe(entry.getKey(), new String[] {"L", "L"}, keys);
        }
    }

    private static void registerTentRecipes() {
        var tentMap = Maps.<RecipeData, ItemConvertible>newHashMap();
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.WHITE_TENT, 1), Blocks.WHITE_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.ORANGE_TENT, 1), Blocks.ORANGE_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.MAGENTA_TENT, 1), Blocks.MAGENTA_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.LIGHT_BLUE_TENT, 1), Blocks.LIGHT_BLUE_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.YELLOW_TENT, 1), Blocks.YELLOW_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.LIME_TENT, 1), Blocks.LIME_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.PINK_TENT, 1), Blocks.PINK_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.GRAY_TENT, 1), Blocks.GRAY_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.LIGHT_GRAY_TENT, 1), Blocks.LIGHT_GRAY_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.CYAN_TENT, 1), Blocks.CYAN_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.PURPLE_TENT, 1), Blocks.PURPLE_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.BLUE_TENT, 1), Blocks.BLUE_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.BROWN_TENT, 1), Blocks.BROWN_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.GREEN_TENT, 1), Blocks.GREEN_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.RED_TENT, 1), Blocks.RED_WOOL);
        tentMap.put(new RecipeData("tents", "tents", BlockRegistry.BLACK_TENT, 1), Blocks.BLACK_WOOL);

        for (var entry : tentMap.entrySet()) {
            var keys = HashMultimap.<Character, Ingredient>create();
            keys.put('W', Ingredient.ofItems(entry.getValue()));
            keys.put('S', Ingredient.ofItems(Items.STICK));
            registerShapedRecipe(entry.getKey(), new String[] {"WWW", "WSW", "SSS"}, keys);
        }
    }

    private static void registerMisc() {
        var keys = HashMultimap.<Character, Ingredient>create();
        var pattern = new Pair<String[], Multimap<Character, Ingredient>>(null, null);
        var recipeData = (RecipeData) null;

        keys.clear();
        keys.put('L', Ingredient.ofItems(Items.LEATHER));
        keys.put('W', Ingredient.fromTag(ItemTags.WOOL));
        keys.put('S', Ingredient.ofItems(Items.STRING));
        recipeData = new RecipeData("", "sleeping_bag", BlockRegistry.SLEEPING_BAG, 1);
        registerShapedRecipe(recipeData, new String[] {"SLL", "WWW", "LLL"}, keys);

        keys.clear();
        keys.put('I', Ingredient.ofItems(Items.IRON_INGOT));
        recipeData = new RecipeData("", "bear_trap", BlockRegistry.BEAR_TRAP, 1);
        registerShapedRecipe(recipeData, new String[] {"I I", "III"}, keys);

        keys.clear();
        pattern = patterns.bricksPattern(Items.SUGAR);
        recipeData = new RecipeData("", "marshmallow", ItemRegistry.MARSHMALLOW, 2);
        registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());

        keys.clear();
        pattern = patterns.bricksPattern(ItemRegistry.BURNED_STICK);
        recipeData = new RecipeData("", "charcoal_from_burned_stick", Items.CHARCOAL, 1);
        registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());

        keys.clear();
        pattern = patterns.oreBlockPattern(Items.CHARCOAL);
        recipeData = new RecipeData("", "charcoal_block", BlockRegistry.CHARCOAL_BLOCK, 1);
        registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());

        keys.clear();
        pattern = patterns.torchPattern(ItemRegistry.MARSHMALLOW, Items.STICK);
        recipeData = new RecipeData("stick_marshmallows", "marshmallow_on_a_stick", ItemRegistry.MARSHMALLOW_ON_A_STICK, 1);
        registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());

        keys.clear();
        pattern = patterns.torchPattern(ItemRegistry.ROASTED_MARSHMALLOW, Items.STICK);
        recipeData = new RecipeData("stick_marshmallows", "roasted_marshmallow_on_a_stick", ItemRegistry.ROASTED_MARSHMALLOW_ON_A_STICK, 1);
        registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
    }
}