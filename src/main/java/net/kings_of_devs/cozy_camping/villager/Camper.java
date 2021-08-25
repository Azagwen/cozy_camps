package net.kings_of_devs.cozy_camping.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.mixin.object.builder.VillagerProfessionAccessor;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerResourceMetadata;
import net.minecraft.client.render.entity.feature.VillagerResourceMetadataReader;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;

public class Camper {

    public static final VillagerProfession CAMPER = VillagerProfessionAccessor.create("camper", CamperPOI.CAMPER, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK);

    public static void init(){
        Registry.register(Registry.VILLAGER_PROFESSION, "camper", CAMPER);

        //****EXAMPLE TRADES****
        //This one creates a simple trade (4 emeralds -> 1 Marshmallow).
        CamperTrade.SimpleTrade(1, Items.EMERALD, 4, ItemRegistry.MARSHMALLOW, 1, 12, 2,0.2f);
        //This one creates a complex trade (3 emeralds + 1 stick -> 1 Marshmallow on a Stick).
        CamperTrade.ComplexTrade(2, new ItemStack(Items.EMERALD, 3), new ItemStack(Items.STICK, 1), ItemRegistry.MARSHMALLOW_ON_A_STICK, 1, 12, 2, 0.2f);
        //**********************

        CozyCampingMain.LOGGER.info("Camper Profession Registered!");
    }

}
