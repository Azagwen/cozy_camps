package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.state.property.EnumProperty;

public class CozyCampProperties {
    public static final EnumProperty<StumpShape> STUMP_SHAPE;
    public static final EnumProperty<TentPiece> TENT_PIECE;

    static {
        STUMP_SHAPE = EnumProperty.of("shape", StumpShape.class);
        TENT_PIECE = EnumProperty.of("piece", TentPiece.class);
    }
}
