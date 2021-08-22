package net.kings_of_devs.cozy_camping;

import net.fabricmc.api.ModInitializer;
import net.kings_of_devs.cozy_camping.block.CozyCampingBlocks;
import net.minecraft.util.Identifier;

public class CozyCampingMain implements ModInitializer {
	public static final String MOD_ID = "cozy_camping";

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		CozyCampingBlocks.init();

		System.out.println("Hello Fabric world!");
	}
}
