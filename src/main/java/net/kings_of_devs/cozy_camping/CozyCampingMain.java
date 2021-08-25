package net.kings_of_devs.cozy_camping;

import com.google.common.collect.Sets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.group.TabbedItemGroup;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.kings_of_devs.cozy_camping.villager.Camper;
import net.kings_of_devs.cozy_camping.villager.CamperPOI;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.logging.Logger;

public class CozyCampingMain implements ModInitializer {
	public static final Logger LOGGER = Logger.getLogger("Cozy Camping");
	public static final String MOD_ID = "cozy_camping";

	public static TabbedItemGroup GROUP;
	public static Set<Item> BLOCKS_TAB = Sets.newHashSet();
	public static Set<Item> MISC_TAB = Sets.newHashSet();

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		GROUP = new TabbedItemGroup(id(MOD_ID), (itemGroup) -> {
			itemGroup.setIcon(ItemRegistry.MARSHMALLOW_ON_A_STICK);
			itemGroup.addTab(Blocks.BEDROCK, "blocks", BLOCKS_TAB);
			itemGroup.addTab(ItemRegistry.MARSHMALLOW_ON_A_STICK, "misc", MISC_TAB);
			return itemGroup;
		});

		BlockRegistry.init();
		ItemRegistry.init();
		CamperPOI.init();
		Camper.init();

		//this makes the charcoal block be a fuel, with the same duration as a normal coal block.
		FuelRegistry.INSTANCE.add(BlockRegistry.CHARCOAL_BLOCK, 16000);

		LOGGER.info("Cozy Camping Registered!");
	}
}
