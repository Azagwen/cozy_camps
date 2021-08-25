package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.util.StringIdentifiable;

public enum StumpFacing implements StringIdentifiable {
    VERTICAL(0, 0, "vertical"),
    NORTH(1, 2, "north"),
    SOUTH(2, 1, "south"),
    EAST(3, 4, "east"),
    WEST(4, 3, "west"),
    X(5, 5, "x"),
    Z(6, 6, "z");

    private final String name;
    private final int id;
    private final int idOpposite;
    StumpFacing(int id, int idOpposite, String name) {
        this.name = name;
        this.id = id;
        this.idOpposite = idOpposite;
    }

    public StumpFacing getOpposite() {
        return byId(this.idOpposite);
    }

    private static StumpFacing byId(int id) {
        for (var constant : values()) {
            if (constant.id == id) {
                return constant;
            }
        }
        return VERTICAL;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
