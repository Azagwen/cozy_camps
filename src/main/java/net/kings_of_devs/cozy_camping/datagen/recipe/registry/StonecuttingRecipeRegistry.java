package net.kings_of_devs.cozy_camping.datagen.recipe.registry;

import com.google.common.collect.Maps;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.datagen.RecipeDatagen;
import net.kings_of_devs.cozy_camping.datagen.recipe.util.RecipeData;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.registry.Registry;

public class StonecuttingRecipeRegistry {

    public static Recipe<?> registerStonecuttingRecipe(RecipeData recipeData, Ingredient input) {
        var recipe = (Recipe<?>) null;
        var suffix = recipeData.suffix();
        var category = recipeData.category();
        var group = recipeData.group();
        var result = recipeData.result();
        var count = recipeData.count();

        var resultId = Registry.ITEM.getId(result.asItem());
        var recipeId = CozyCampingMain.id(resultId.getPath() + (suffix.equals("") ? "" : ("_" + suffix)));
        recipe = RecipeDatagen.stonecuttingRecipe(recipeId, group, input, result, count);
        RecipeDatagen.registerRecipe(recipe, category);
        return recipe;
    }

    public static Recipe<?> registerStonecuttingRecipe(RecipeData recipeData, ItemConvertible input) {
        return registerStonecuttingRecipe(recipeData, Ingredient.ofItems(input));
    }

    private static RecipeData suffixOnlyData(String suffix, ItemConvertible result, int count) {
        return new RecipeData(suffix, "", "", result, count);
    }

    public static void init() {
        registerStairsRecipes();
        registerSlabRecipes();
        registerColumnRecipes();
        registerMiscRecipes();
    }

    private static void registerStairsRecipes() {
        //Map<RecipeData, Input>
        var count = 1;
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();
        for (var entry : map.entrySet()) {
            var recipeData = entry.getKey();
            registerStonecuttingRecipe(recipeData, entry.getValue());
        }
    }

    private static void registerSlabRecipes() {
        //Map<RecipeData, Input>
        var count = 2;
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();
        for (var entry : map.entrySet()) {
            var recipeData = entry.getKey();
            registerStonecuttingRecipe(recipeData, entry.getValue());
        }
    }

    private static void registerColumnRecipes() {
        //Map<RecipeData, Input>
        var count = 1;
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();
        for (var entry : map.entrySet()) {
            var recipeData = entry.getKey();
            registerStonecuttingRecipe(recipeData, entry.getValue());
        }
    }

    private static void registerWallRecipes() {
        //Map<RecipeData, Input>
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();
        for (var entry : map.entrySet()) {
            var recipeData = entry.getKey();
            registerStonecuttingRecipe(recipeData, entry.getValue());
        }
    }

    private static void registerMiscRecipes() {
        //Map<RecipeData, Input>
        var map = Maps.<RecipeData, ItemConvertible>newHashMap();
        for (var entry : map.entrySet()) {
            var recipeData = entry.getKey();
            registerStonecuttingRecipe(recipeData, entry.getValue());
        }
    }
}
