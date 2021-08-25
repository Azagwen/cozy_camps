package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.util.StringIdentifiable;

public enum StumpAxis implements StringIdentifiable {
    VERTICAL(0, 0, "vertical"),
    X(5, 5, "x"),
    Z(6, 6, "z");

    private final String name;
    private final int id;
    private final int idOpposite;
    StumpAxis(int id, int idOpposite, String name) {
        this.name = name;
        this.id = id;
        this.idOpposite = idOpposite;
    }

    public StumpAxis getOpposite() {
        return byId(this.idOpposite);
    }

    private static StumpAxis byId(int id) {
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
