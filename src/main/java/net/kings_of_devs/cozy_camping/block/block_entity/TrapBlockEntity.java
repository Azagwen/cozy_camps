package net.kings_of_devs.cozy_camping.block.block_entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class TrapBlockEntity extends BlockEntity {
    private static final String TRAPPED_ENTITY = "trapped_entity";
    private UUID entityUUID = null;

    public TrapBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TRAP_BLOCK_ENTITY, pos, state);
    }

    // Serialize the BlockEntity
    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putUuid(TRAPPED_ENTITY, this.entityUUID); // Save the current value of the number to the tag
        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.entityUUID = tag.getUuid(TRAPPED_ENTITY);
    }

    public void setTrappedEntity(UUID uuid) {
        this.entityUUID = uuid;
    }

    public UUID getTrappedEntity(){
        return this.entityUUID;
    }

}
