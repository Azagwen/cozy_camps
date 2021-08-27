package net.kings_of_devs.cozy_camping.block.block_entity;

import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class TrapBlockEntity extends BlockEntity {
    private UUID entityUUID = null;

    public TrapBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TRAP_BLOCK_ENTITY, pos, state);
    }

    // Serialize the BlockEntity
    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        // Save the current value of the number to the tag
        tag.putUuid("trapped_entity", entityUUID);

        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        entityUUID = tag.getUuid("trapped_entity");
    }

    public void setTrappedEntity(UUID uuid) {
        entityUUID = uuid;
    }

    public UUID getTrappedEntity(){
        return entityUUID;
    }

}
