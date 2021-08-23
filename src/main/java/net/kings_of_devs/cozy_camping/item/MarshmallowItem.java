package net.kings_of_devs.cozy_camping.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class MarshmallowItem extends Item {

    int type; //0: marshmallow, 1: roasted, 2:stick, 3:roasted stick

    public MarshmallowItem(Settings settings, int _type) {
        super(settings);
        type = _type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity player = user.getEntityWorld().getClosestPlayer(user, 1);
        if(type==2 && !player.getAbilities().creativeMode) {
            user.setStackInHand(user.getActiveHand(), Items.STICK.getDefaultStack());
        }
        if(type==3 && !player.getAbilities().creativeMode) {
            user.setStackInHand(user.getActiveHand(), ItemRegistry.BURNED_STICK.getDefaultStack());
        }
        return user.eatFood(world, stack);
    }

}
