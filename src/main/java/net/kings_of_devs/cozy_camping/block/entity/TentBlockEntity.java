package net.kings_of_devs.cozy_camping.block.entity;

import net.kings_of_devs.cozy_camping.block.TentBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class TentBlockEntity extends BlockEntity {
    private DyeColor color;

    public TentBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TENT, pos, state);
        this.color = ((TentBlock)state.getBlock()).getColor();
    }

    public TentBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(BlockEntityRegistry.TENT, pos, state);
        this.color = color;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, BlockEntityUpdateS2CPacket.BED, this.toInitialChunkDataNbt());
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }
}
