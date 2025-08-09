package com.flarelabsmc.sbfc.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import io.redspace.ironsspellbooks.entity.spells.fireball.FireballRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireballRenderer.class)
public abstract class FireballRendererMixin extends EntityRenderer<Projectile> {
    @Shadow public abstract ResourceLocation getFireTextureLocation(Projectile entity);

    protected FireballRendererMixin(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Redirect(
            method = "render(Lnet/minecraft/world/entity/projectile/Projectile;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", ordinal = 1),
            remap = false
    )
    private void sbfc$amendmentsRenderOutline(
            ModelPart instance,
            PoseStack pPoseStack,
            VertexConsumer pVertexConsumer,
            int pPackedLight,
            int pPackedOverlay,
            Projectile entity,
            float yaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int light
    ) {
        VertexConsumer emissive = bufferSource.getBuffer(RenderType.energySwirl(getFireTextureLocation(entity), 0.0f, 0.0f));
        instance.render(pPoseStack, emissive, pPackedLight, pPackedOverlay);
    }
}