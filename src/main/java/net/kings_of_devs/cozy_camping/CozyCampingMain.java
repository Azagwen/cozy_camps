package net.kings_of_devs.cozy_camping;

import com.google.common.collect.Sets;
import net.fabricmc.api.ModInitializer;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.group.CozyCampingItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.Set;

public class CozyCampingMain implements ModInitializer {
	public static final String MOD_ID = "cozy_camping";

	public static ItemGroup GROUP;
	public static Set<Item> BLOCKS_TAB = Sets.newHashSet();
	public static Set<Item> MISC_TAB = Sets.newHashSet();

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		GROUP = new CozyCampingItemGroup(CozyCampingMain.id(MOD_ID));

		BlockRegistry.init();

		System.out.println("Hello Fabric world!");
	}
}
