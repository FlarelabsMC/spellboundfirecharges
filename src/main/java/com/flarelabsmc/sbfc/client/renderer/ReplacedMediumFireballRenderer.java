package com.flarelabsmc.sbfc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.entity.spells.fireball.FireballRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.projectile.Projectile;

public class ReplacedMediumFireballRenderer extends FireballRenderer {
    public ReplacedMediumFireballRenderer(EntityRendererProvider.Context context) {
        super(context, 0.5f);
    }

    public void render(Projectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }
}
