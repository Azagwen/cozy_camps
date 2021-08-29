package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class CozyCampProperties {
    public static final EnumProperty<StumpShape> STUMP_SHAPE;
    public static final EnumProperty<TentPiece> TENT_PIECE;
    public static final BooleanProperty LEFT_OCCUPIED;
    public static final BooleanProperty RIGHT_OCCUPIED;
    public static final BooleanProperty HAS_LEFT_BAG;
    public static final BooleanProperty HAS_RIGHT_BAG;

    static {
        STUMP_SHAPE = EnumProperty.of("shape", StumpShape.class);
        TENT_PIECE = EnumProperty.of("piece", TentPiece.class);
        LEFT_OCCUPIED = BooleanProperty.of("left_occupied");
        RIGHT_OCCUPIED = BooleanProperty.of("right_occupied");
        HAS_LEFT_BAG = BooleanProperty.of("has_bag_on_left");
        HAS_RIGHT_BAG = BooleanProperty.of("has_bag_on_right");
    }
}
