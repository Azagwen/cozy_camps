package net.kings_of_devs.cozy_camping.block.state;

import net.minecraft.util.StringIdentifiable;

public enum StumpSide implements StringIdentifiable {
    CENTERED("centered"),
    CENTERED_LARGE("centered_large"),
    POSITIVE("positive"),
    NEGATIVE("negative");

    private final String name;
    StumpSide(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
