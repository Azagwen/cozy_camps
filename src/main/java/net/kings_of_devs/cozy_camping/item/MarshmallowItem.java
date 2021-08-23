package net.kings_of_devs.cozy_camping.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class MarshmallowItem extends Item {
    private final int type; //0: normal, 1: normal roasted, 2:stick, 3: stick roasted

    public MarshmallowItem(int type, Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            if (!player.getAbilities().creativeMode) {
                var newStack = switch (type) {
                    default -> ItemStack.EMPTY;
                    case 2 -> Items.STICK.getDefaultStack();
                    case 3 -> ItemRegistry.BURNED_STICK.getDefaultStack();
                };
                user.setStackInHand(user.getActiveHand(), newStack);
            }
            return user.eatFood(world, stack);
        }
        return stack;
    }
}
