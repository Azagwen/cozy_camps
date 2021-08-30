package net.kings_of_devs.cozy_camping.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.mixin.object.builder.VillagerProfessionAccessor;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
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

import java.util.Random;

public class Camper {

    public static final VillagerProfession CAMPER = VillagerProfessionAccessor.create("camper", CamperPOI.CAMPER, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK);

    public static void init(){
        Registry.register(Registry.VILLAGER_PROFESSION, "camper", CAMPER);

        //****EXAMPLE TRADES****
        //This one creates a simple trade (4 emeralds -> 1 Marshmallow). (10 emeralds -> 1 Walking Stick).
        CamperTrade.SimpleTrade(1, Items.EMERALD, 5, Items.APPLE, 5, 12, 1, 0.2f);
        CamperTrade.SimpleTrade(1, Items.EMERALD, 6, ItemRegistry.MARSHMALLOW, 4, 12, 1, 0.2f);

        CamperTrade.SimpleTrade(2, Items.STICK, 32, Items.EMERALD, 1, 16, 1, 0.2f);
        CamperTrade.SimpleTrade(2, Items.EMERALD, 8, ItemRegistry.WALKING_STICK, 1, 4, 4, 0.2f);

        CamperTrade.SimpleTrade(3, Items.EMERALD, 7, Items.FLINT_AND_STEEL, 1, 8, 2, 0.2f);
        CamperTrade.SimpleTrade(3, Items.EMERALD, 12, BlockRegistry.WHITE_TENT.asItem(), 1, 4, 4, 0.2f);

        CamperTrade.SimpleTrade(4, Items.EMERALD, 12, Items.CAMPFIRE, 1, 8, 4, 0.2f);
        CamperTrade.SimpleTrade(4, Items.EMERALD, 18, BlockRegistry.BEAR_TRAP.asItem(), 1, 4, 4, 0.2f);

        CamperTrade.SimpleTrade(5, Items.EMERALD, 22, BlockRegistry.FANCY_TENT.asItem(), 1, 4, 4, 0.2f);

        //This one creates a complex trade (3 emeralds + 1 stick -> 1 Marshmallow on a Stick).
        //CamperTrade.ComplexTrade(2, new ItemStack(Items.EMERALD, 3), new ItemStack(Items.STICK, 1), ItemRegistry.MARSHMALLOW_ON_A_STICK, 1, 12, 2, 0.2f);
        //**********************

        CozyCampingMain.LOGGER.info("Camper Profession Registered!");
    }

}
