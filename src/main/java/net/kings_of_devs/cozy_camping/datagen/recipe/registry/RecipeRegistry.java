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
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class RecipeRegistry {
    public static final Logger LOGGER = LogManager.getLogger("Atbyw Recipes");
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
        registerMisc();

        //Other Types
        StonecuttingRecipeRegistry.init();
        FurnaceRecipeRegistry.init();

        LOGGER.info("ATBYW Recipes Inintiliazed");
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
        var recipeData = (RecipeData) null;

        //Example of recipe
        //keys.clear();
        //keys.put('G', Ingredient.ofItems(Items.GOLD_INGOT));
        //keys.put('R', Ingredient.ofItems(Items.REDSTONE));
        //keys.put('#', Ingredient.ofItems(Items.REPEATER));
        //recipeData = new RecipeData("redstone", "timer_repeater", AtbywBlocks.TIMER_REPEATER, 1);
        //registerShapedRecipe(recipeData, new String[] {"GRG", "R#R", "GRG"}, keys);

    }
}