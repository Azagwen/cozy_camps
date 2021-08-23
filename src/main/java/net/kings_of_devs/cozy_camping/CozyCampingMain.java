package net.kings_of_devs.cozy_camping;

import com.google.common.collect.Sets;
import net.fabricmc.api.ModInitializer;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.group.CozyCampingItemGroup;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Set;

public class CozyCampingMain implements ModInitializer {
	public static final String MOD_ID = "cozy_camping";

	public static CozyCampingItemGroup GROUP;
	public static Set<Item> BLOCKS_TAB = Sets.newHashSet();
	public static Set<Item> MISC_TAB = Sets.newHashSet();

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		GROUP = new CozyCampingItemGroup(id(MOD_ID), (itemGroup) -> {
			itemGroup.setIcon(BlockRegistry.CHARCOAL_BLOCK);
			itemGroup.addTab(Blocks.BEDROCK, "blocks", BLOCKS_TAB);
			itemGroup.addTab(Blocks.BEDROCK, "misc", MISC_TAB);
			return itemGroup;
		});

		BlockRegistry.init();
		ItemRegistry.init();

		System.out.println("Hello Fabric world!");
	}
}
