package net.kings_of_devs.cozy_camping.mixin;

import net.kings_of_devs.cozy_camping.util.PlayerEntityDuck;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "method_18773(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("HEAD"), cancellable = true)
    private static void wakeSleepingPlayersLambda(ServerPlayerEntity player, CallbackInfo ci) {
        var playerDuck = (PlayerEntityDuck) player;
        if (playerDuck.isSleepingInBag()) {
            player.sendMessage(new LiteralText("sleeping in a bag"), true);
            ci.cancel();
        }
    }
}
