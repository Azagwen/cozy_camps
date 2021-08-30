package net.kings_of_devs.cozy_camping.util;

import net.minecraft.util.math.BlockPos;

public interface PlayerEntityDuck {
    boolean isSleepingInBag();
    void setIsSleepingInBag(boolean value);
    void sleepFromTent(BlockPos pos, boolean isLeft);
    void sleepFromBag(BlockPos pos);
}
