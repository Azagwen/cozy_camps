package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.state.property.EnumProperty;

public class Properties {
    public static final EnumProperty<StumpFacing> STUMP_FACING;

    static {
        STUMP_FACING = EnumProperty.of("facing", StumpFacing.class);
    }
}
