package net.kings_of_devs.cozy_camping.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kings_of_devs.cozy_camping.block.entity.BlockEntityPackets;
import net.kings_of_devs.cozy_camping.block.entity.TentBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onBlockEntityUpdate", at = @At(value = "TAIL"))
    private void onBlockEntityUpdate(BlockEntityUpdateS2CPacket packet, CallbackInfo ci) {
        var blockPos = packet.getPos();
        var blockEntity = this.client.world.getBlockEntity(blockPos);
        var type = packet.getBlockEntityType();
        if (type == BlockEntityPackets.TENT && blockEntity instanceof TentBlockEntity) {
            blockEntity.readNbt(packet.getNbt());
        }
    }
}
