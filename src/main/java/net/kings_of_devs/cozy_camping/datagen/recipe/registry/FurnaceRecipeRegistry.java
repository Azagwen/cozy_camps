package net.kings_of_devs.cozy_camping.datagen.recipe.registry;

import com.google.common.collect.Maps;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.datagen.RecipeDatagen;
import net.kings_of_devs.cozy_camping.datagen.recipe.util.FurnaceRecipeData;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.registry.Registry;

public class FurnaceRecipeRegistry {

    public static Recipe<?> registerSmeltingRecipe(FurnaceRecipeData recipeData, Ingredient input) {
        var recipe = (Recipe<?>) null;
        var suffix = recipeData.suffix();
        var category = recipeData.category();
        var group = recipeData.group();
        var result = recipeData.result();
        var exp = recipeData.exp();
        var cookTime = recipeData.cookTime();

        var resultId = Registry.ITEM.getId(result.asItem());
        var recipeId = CozyCampingMain.id(resultId.getPath() + (suffix.equals("") ? "" : ("_" + suffix)));
        recipe = RecipeDatagen.smeltingRecipe(recipeId, group, input, result, exp, cookTime);
        RecipeDatagen.registerRecipe(recipe, category);
        return recipe;
    }

    public static Recipe<?> registerSmeltingRecipe(FurnaceRecipeData recipeData, ItemConvertible input) {
        return registerSmeltingRecipe(recipeData, Ingredient.ofItems(input));
    }

    private static FurnaceRecipeData suffixOnlyData(String suffix, ItemConvertible result, float exp, int cookTime) {
        return new FurnaceRecipeData(suffix, "", "", result, exp, cookTime);
    }

    public static void init() {
        //Map<FurnaceRecipeData, Input>
        var map = Maps.<FurnaceRecipeData, ItemConvertible>newHashMap();

        for (var entry : map.entrySet()) {
            var recipeData = entry.getKey();
            registerSmeltingRecipe(recipeData, entry.getValue());
        }
    }
}