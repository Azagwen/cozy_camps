package net.kings_of_devs.cozy_camping.datagen.recipe.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
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
        //Generic patterns
        registerStairPatterns();
        registerSlabPatterns();
        registerBricksPatterns();
        registerWallPatterns();
        registerDyingPatterns();
        registerStickPatterns();

        //Unique patterns
        registerMisc();

        //Shapeless

        //Other Types
        StonecuttingRecipeRegistry.init();
        FurnaceRecipeRegistry.init();

        LOGGER.info("ATBYW Recipes Inintiliazed");
    }

    private static void registerStairPatterns() {
        //Map<RecipeData, Input
        var count = 4;
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();

        for (var entry : map.entrySet()) {
            var pattern = patterns.stairsPattern(entry.getValue());
            var recipeData = entry.getKey();
            registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
        }
    }

    private static void registerSlabPatterns() {
        //Map<RecipeData, Input>
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();

        for (var entry : map.entrySet()) {
            var pattern = patterns.slabPattern(entry.getValue());
            var recipeData = entry.getKey();
            registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
        }
    }

    private static void registerBricksPatterns() {
        //Map<RecipeData, Input>
        var brickCount = 4;
        var glassCount = 1;
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();

        for (var entry : map.entrySet()) {
            var pattern = patterns.bricksPattern(entry.getValue());
            var recipeData = entry.getKey();
            registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
        }
    }

    private static void registerWallPatterns() {
        //Map<RecipeData, Input>
        var count = 6;
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();

        for (var entry : map.entrySet()) {
            var pattern = patterns.bricksPattern(entry.getValue());
            var recipeData = entry.getKey();
            registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
        }
    }

    private static RecipeData dyeingRecipeData(String group, ItemConvertible result, int count) {
        return new RecipeData("dyeing", "dyeing", group, result, count);
    }

    private static void registerDyingPatterns() {
        //Map<RecipeData, Input>
        var count = 8;
        var dyeingMap = Maps.<RecipeData, ItemConvertible>newHashMap();

        //Map<Pair<Input outer ring, Input middle>, Output>
        var map = Maps.<RecipeData, Pair<ItemConvertible, ItemConvertible>>newHashMap();

        for (var color : DyeColor.values()) {
            for (var entry : dyeingMap.entrySet()) {
                var recipeData = entry.getKey();
                if (Registry.ITEM.getId(recipeData.result().asItem()).getPath().contains(color.asString())) {
                    var pattern = patterns.dyingRingPattern(entry.getValue(), DyeItem.byColor(color));
                    registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
                }
            }
        }

        for (var entry : map.entrySet()) {
            var pattern = patterns.dyingRingPattern(entry.getValue().getLeft(), entry.getValue().getRight());
            var recipeData = entry.getKey();
            registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
        }
    }

    private static void registerStickPatterns() {
        //Map<RecipeData, Input>
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();

        for (var entry : map.entrySet()) {
            var pattern = patterns.stickPattern(entry.getValue());
            var recipeData = entry.getKey();
            registerShapedRecipe(recipeData, pattern.getLeft(), pattern.getRight());
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