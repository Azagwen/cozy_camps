package net.kings_of_devs.cozy_camping.mixin;

import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerClothingFeatureRenderer.class)
public class VillagerTextureMixin {

    @Inject(method = "findTexture", at = @At("HEAD"), cancellable = true)
    private void camperTexture(String keyType, Identifier keyId, CallbackInfoReturnable<Identifier> cir){
        if(keyId.getPath().equalsIgnoreCase("camper")) {
            cir.setReturnValue(new Identifier("cozy_camping", "textures/entity/camper.png"));
        }
    }
}
