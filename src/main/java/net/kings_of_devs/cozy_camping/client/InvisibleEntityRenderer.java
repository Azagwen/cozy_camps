package net.kings_of_devs.cozy_camping.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class InvisibleEntityRenderer<E extends MobEntity> extends MobEntityRenderer<E, AnimalModel<E>> {
    private static final Identifier TEXTURE = new Identifier("minecraft:textures/block/stone.png");

    public InvisibleEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InvisibleModel<>(), 0);
    }

    @Override
    public Identifier getTexture(E entity) {
        return TEXTURE;
    }

    public static class InvisibleModel<F extends MobEntity> extends AnimalModel<F> {
        @Override
        protected Iterable<ModelPart> getHeadParts() {
            return ImmutableList.of();
        }

        @Override
        protected Iterable<ModelPart> getBodyParts() {
            return ImmutableList.of();
        }
        
        @Override
        public void setAngles(F entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        }
    }
}