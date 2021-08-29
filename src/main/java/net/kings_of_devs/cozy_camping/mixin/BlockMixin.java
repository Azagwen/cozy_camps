package net.kings_of_devs.cozy_camping.mixin;

import net.kings_of_devs.cozy_camping.util.BlockDuck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin implements BlockDuck {

    @Override
    public void onWalkedUpon(World world, BlockPos pos, BlockState state, Entity entity) {
    }
}
