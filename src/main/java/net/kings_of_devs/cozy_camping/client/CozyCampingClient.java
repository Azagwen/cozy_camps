package net.kings_of_devs.cozy_camping.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.entity.EntityRegistry;
import net.minecraft.client.render.RenderLayer;

public class CozyCampingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(),
                BlockRegistry.WHITE_TENT,
                BlockRegistry.ORANGE_TENT,
                BlockRegistry.MAGENTA_TENT,
                BlockRegistry.LIGHT_BLUE_TENT,
                BlockRegistry.YELLOW_TENT,
                BlockRegistry.LIME_TENT,
                BlockRegistry.PINK_TENT,
                BlockRegistry.GRAY_TENT,
                BlockRegistry.LIGHT_GRAY_TENT,
                BlockRegistry.CYAN_TENT,
                BlockRegistry.PURPLE_TENT,
                BlockRegistry.BLUE_TENT,
                BlockRegistry.BROWN_TENT,
                BlockRegistry.GREEN_TENT,
                BlockRegistry.RED_TENT,
                BlockRegistry.BLACK_TENT,
                BlockRegistry.WHITE_RIPPED_TENT,
                BlockRegistry.ORANGE_RIPPED_TENT,
                BlockRegistry.MAGENTA_RIPPED_TENT,
                BlockRegistry.LIGHT_BLUE_RIPPED_TENT,
                BlockRegistry.YELLOW_RIPPED_TENT,
                BlockRegistry.LIME_RIPPED_TENT,
                BlockRegistry.PINK_RIPPED_TENT,
                BlockRegistry.GRAY_RIPPED_TENT,
                BlockRegistry.LIGHT_GRAY_RIPPED_TENT,
                BlockRegistry.CYAN_RIPPED_TENT,
                BlockRegistry.PURPLE_RIPPED_TENT,
                BlockRegistry.BLUE_RIPPED_TENT,
                BlockRegistry.BROWN_RIPPED_TENT,
                BlockRegistry.GREEN_RIPPED_TENT,
                BlockRegistry.RED_RIPPED_TENT,
                BlockRegistry.BLACK_RIPPED_TENT,
                BlockRegistry.FANCY_TENT,
                BlockRegistry.HEATHER,
                BlockRegistry.BEAR_TRAP
        );

        EntityRendererRegistry.INSTANCE.register(EntityRegistry.SEAT, InvisibleEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.TRAP, InvisibleEntityRenderer::new);
    }
}
