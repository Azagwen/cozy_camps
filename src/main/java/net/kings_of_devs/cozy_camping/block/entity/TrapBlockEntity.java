package net.kings_of_devs.cozy_camping.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class TrapBlockEntity extends BlockEntity {
    private static final TargetPredicate TRAPPED;
    private boolean isHeldClosed;

    public TrapBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BEAR_TRAP, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.isHeldClosed = nbt.getBoolean("IsHeldClosed");
    }

    public void setHeldClosed(boolean heldClosed) {
        this.isHeldClosed = heldClosed;
    }

    public boolean isHeldClosed() {
        return this.isHeldClosed;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("IsHeldClosed", this.isHeldClosed);
        return nbt;
    }

    public static void tick(World world, BlockPos pos, BlockState state, TrapBlockEntity blockEntity) {
        var closest = world.getClosestEntity(LivingEntity.class, TRAPPED, null, pos.getX(), pos.getY(), pos.getZ(), new Box(pos));
        if (closest == null) {
            blockEntity.setHeldClosed(false);
        }
    }

    static {
        TRAPPED = TargetPredicate.createNonAttackable().setBaseMaxDistance(1.0D);
    }
}
