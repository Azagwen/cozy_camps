package net.kings_of_devs.cozy_camping.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BlockUtils {

    public static void breakBlockSilent(World world, BlockPos pos, boolean drop, @Nullable Entity breakingEntity) {
        var blockState = world.getBlockState(pos);
        var fluidState = world.getFluidState(pos);
        var hasBroken = world.setBlockState(pos, fluidState.getBlockState(), Block.NOTIFY_ALL, 512);
        world.addBlockBreakParticles(pos, blockState);

        if (drop) {
            Block.dropStacks(blockState, world, pos, null, breakingEntity, ItemStack.EMPTY);
        }
        if (hasBroken) {
            world.emitGameEvent(breakingEntity, GameEvent.BLOCK_DESTROY, pos);
        }
    }
}
