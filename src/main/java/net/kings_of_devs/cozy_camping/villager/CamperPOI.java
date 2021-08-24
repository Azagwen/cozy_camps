package net.kings_of_devs.cozy_camping.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.mixin.object.builder.PointOfInterestTypeAccessor;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.poi.PointOfInterestType;

public class CamperPOI {
    public static final PointOfInterestType CAMPER = PointOfInterestTypeAccessor.callCreate("camper", ImmutableSet.copyOf(Blocks.CAMPFIRE.getStateManager().getStates()), 1,1);

    public static void init(){
        PointOfInterestTypeAccessor.callSetup(CAMPER);
        Registry.register(Registry.POINT_OF_INTEREST_TYPE, "camper", CAMPER);

        CozyCampingMain.LOGGER.info("Camper Point of Interest Registered!");
    }
}
