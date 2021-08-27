package net.kings_of_devs.cozy_camping;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.entity.SeatEntity;
import net.kings_of_devs.cozy_camping.group.TabbedItemGroup;
import net.kings_of_devs.cozy_camping.item.ItemRegistry;
import net.kings_of_devs.cozy_camping.villager.Camper;
import net.kings_of_devs.cozy_camping.villager.CamperPOI;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

public class CozyCampingMain implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Cozy Camping");
	public static final String MOD_ID = "cozy_camping";

	public static TabbedItemGroup GROUP;
	public static Set<Item> BLOCKS_TAB = Sets.newHashSet();
	public static Set<Item> MISC_TAB = Sets.newHashSet();

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static final EntityType<SeatEntity> SEAT = FabricEntityTypeBuilder.create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).fireImmune().disableSummon().build();

	@Override
	public void onInitialize() {
		BlockRegistry.init();
		ItemRegistry.init();
		CamperPOI.init();
		Camper.init();

		//Registers the Stump Seat entity.
		Registry.register(Registry.ENTITY_TYPE, id("seat"), SEAT);
		FabricDefaultAttributeRegistry.register(SEAT, SeatEntity.getAttributeContainer());

		//This makes the charcoal block be a fuel, with the same duration as a normal coal block.
		FuelRegistry.INSTANCE.add(BlockRegistry.CHARCOAL_BLOCK, 16000);

		//Register the group last, for safety reasons.
		GROUP = new TabbedItemGroup(id(MOD_ID), (itemGroup) -> {
			itemGroup.setIcon(ItemRegistry.MARSHMALLOW_ON_A_STICK);
			itemGroup.addTab(BlockRegistry.CHARCOAL_BLOCK, "blocks", BLOCKS_TAB);
			itemGroup.addTab(ItemRegistry.MARSHMALLOW_ON_A_STICK, "misc", MISC_TAB);
			return itemGroup;
		});

		LOGGER.info("Cozy Camping Registered!");
	}
}
