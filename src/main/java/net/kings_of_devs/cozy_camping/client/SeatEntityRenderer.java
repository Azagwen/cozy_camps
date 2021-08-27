package net.kings_of_devs.cozy_camping.client;

import com.google.common.collect.ImmutableList;
import net.kings_of_devs.cozy_camping.entity.SeatEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;

public class SeatEntityRenderer extends MobEntityRenderer<SeatEntity, AnimalModel<SeatEntity>> {
    private static final Identifier TEXTURE = new Identifier("minecraft:textures/block/stone.png");

    public SeatEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SeatModel(), 0);
    }

    @Override
    public Identifier getTexture(SeatEntity entity) {
        return TEXTURE;
    }

    public static class SeatModel extends AnimalModel<SeatEntity> {
        @Override
        protected Iterable<ModelPart> getHeadParts() {
            return ImmutableList.of();
        }

        @Override
        protected Iterable<ModelPart> getBodyParts() {
            return ImmutableList.of();
        }
        @Override
        public void setAngles(SeatEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        }
    }
}