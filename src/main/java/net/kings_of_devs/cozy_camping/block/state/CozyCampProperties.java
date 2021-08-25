package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.state.property.EnumProperty;

public class CozyCampProperties {
    public static final EnumProperty<StumpAxis> STUMP_AXIS;
    public static final EnumProperty<StumpSide> STUMP_SIDE;

    static {
        STUMP_AXIS = EnumProperty.of("axis", StumpAxis.class);
        STUMP_SIDE = EnumProperty.of("side", StumpSide.class);
    }
}
