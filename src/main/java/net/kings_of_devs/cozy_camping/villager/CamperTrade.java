package net.kings_of_devs.cozy_camping.villager;

import net.fabricmc.fabric.impl.object.builder.TradeOfferInternals;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.util.Random;

public class CamperTrade {
    private static final VillagerProfession DEFAULT_PROFESSION = Camper.CAMPER;
    private static final int DEFAULT_MAX_USES = 12;

    public CamperTrade(){    }

    //Only needs 1 Item
    public static void SimpleTrade(int level, Item buyItem, int price, Item sellItem, int count, int maxUses, int merchantExperience, float priceMultiplier){
        TradeOfferInternals.registerVillagerOffers(DEFAULT_PROFESSION, level, factories -> factories.add(new TradeFactory(new ItemStack(buyItem, price), Items.AIR.getDefaultStack(), sellItem, count, maxUses, merchantExperience, priceMultiplier)));
    }

    //Gets 2 Items to buy (as ItemStacks)
    public static void ComplexTrade(int level, ItemStack firstBuyItem, ItemStack secondBuyItem, Item sellItem, int count, int maxUses, int merchantExperience, float priceMultiplier){
        TradeOfferInternals.registerVillagerOffers(DEFAULT_PROFESSION, level, factories -> factories.add(new TradeFactory(firstBuyItem, secondBuyItem, sellItem, count, maxUses, merchantExperience, priceMultiplier)));
    }

    static class TradeFactory implements TradeOffers.Factory {
        private final ItemStack firstBuy;
        private final ItemStack secondBuy;
        private final Item sell;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public TradeFactory(ItemStack _firstBuy, ItemStack _secondBuy, Item _sell, int _count, int _maxUses, int _experience, float _multiplier){
            this.firstBuy = _firstBuy;
            this.secondBuy = _secondBuy;
            this.sell = _sell;
            this.count = _count;
            this.maxUses = _maxUses;
            this.experience = _experience;
            this.multiplier = _multiplier;
        }

        public TradeOffer create(Entity entity, Random random) {
            //return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
            //ItemStack firstBuyItem, ItemStack secondBuyItem, ItemStack sellItem, int uses, int maxUses, int merchantExperience, float priceMultiplier
            return new TradeOffer(this.firstBuy, this.secondBuy, new ItemStack(this.sell, this.count), 0, this.maxUses, this.experience, this.multiplier);
        }
    }
}
