package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.util.StringIdentifiable;

public enum TentPiece implements StringIdentifiable {
    CENTER("center"),
    NORTH("north"),
    SOUTH("south"),
    EAST("west"),
    WEST("east"),
    NORTH_EAST("north_west"),
    NORTH_WEST("north_east"),
    SOUTH_EAST("south_west"),
    SOUTH_WEST("south_east");

    public final String name;
    TentPiece(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
