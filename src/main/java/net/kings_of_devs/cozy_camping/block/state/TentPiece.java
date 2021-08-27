package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.util.StringIdentifiable;

public enum TentPiece implements StringIdentifiable {
    CENTER("center"),
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west"),
    NORTH_LOWEST("north_lowest"),
    SOUTH_LOWEST("south_lowest"),
    EAST_LOWEST("east_lowest"),
    WEST_LOWEST("west_lowest"),
    NORTH_EAST("north_east"),
    NORTH_WEST("north_west"),
    SOUTH_EAST("south_east"),
    SOUTH_WEST("south_west"),
    NORTH_EAST_LOWEST("north_east_lowest"),
    NORTH_WEST_LOWEST("north_west_lowest"),
    SOUTH_EAST_LOWEST("south_east_lowest"),
    SOUTH_WEST_LOWEST("south_west_lowest");

    public final String name;
    TentPiece(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
