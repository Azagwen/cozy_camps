package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.state.property.EnumProperty;

public class CozyCampProperties {
    public static final EnumProperty<StumpShape> STUMP_SIDE;

    static {
        STUMP_SIDE = EnumProperty.of("shape", StumpShape.class);
    }
}
