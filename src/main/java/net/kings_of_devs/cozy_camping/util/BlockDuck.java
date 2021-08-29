package net.kings_of_devs.cozy_camping.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockDuck {
    void onWalkedUpon(World world, BlockPos pos, BlockState state, Entity entity);
}
