package net.kings_of_devs.cozy_camping.item;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public class ItemRegistry {

    public static final Item MARSHMALLOW = new MarshmallowItem(0, new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(6f).snack().build()));
    public static final Item ROASTED_MARSHMALLOW = new MarshmallowItem(1, new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(7f).snack().build()));
    public static final Item MARSHMALLOW_ON_A_STICK = new MarshmallowItem(2, new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(7f).snack().build()).maxCount(1));
    public static final Item ROASTED_MARSHMALLOW_ON_A_STICK = new MarshmallowItem(3, new Item.Settings().food(new FoodComponent.Builder().hunger(10).saturationModifier(8f).snack().build()).maxCount(1));
    public static final Item BURNED_STICK = new Item(new Item.Settings().maxCount(64));

    public static void init(){
        ItemRegistryUtil.registerItem(CozyCampingMain.MISC_TAB, "marshmallow", MARSHMALLOW);
        ItemRegistryUtil.registerItem(CozyCampingMain.MISC_TAB, "roasted_marshmallow", ROASTED_MARSHMALLOW);
        ItemRegistryUtil.registerItem(CozyCampingMain.MISC_TAB, "marshmallow_on_a_stick", MARSHMALLOW_ON_A_STICK);
        ItemRegistryUtil.registerItem(CozyCampingMain.MISC_TAB, "roasted_marshmallow_on_a_stick", ROASTED_MARSHMALLOW_ON_A_STICK);
        ItemRegistryUtil.registerItem(CozyCampingMain.MISC_TAB, "burned_stick", BURNED_STICK);

        CozyCampingMain.LOGGER.info("Cozy Camping Blocks Registered!");
    }
}
