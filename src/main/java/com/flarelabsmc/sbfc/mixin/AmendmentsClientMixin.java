package com.flarelabsmc.sbfc.mixin;

import com.flarelabsmc.sbfc.client.renderer.ReplacedMediumFireballRenderer;
import io.redspace.ironsspellbooks.render.ReplacedFireballRenderer;
import net.mehvahdjukaar.amendments.AmendmentsClient;
import net.mehvahdjukaar.amendments.reg.ModRegistry;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AmendmentsClient.class)
public class AmendmentsClientMixin {
    @Redirect(method = "registerEntityRenderers", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/moonlight/api/platform/ClientHelper$EntityRendererEvent;register(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V"), remap = false)
    private static <E extends Projectile> void sbfc$replaceEntityRenderers(ClientHelper.EntityRendererEvent instance, EntityType<? extends E> entityType, EntityRendererProvider<E> eEntityRendererProvider) {
        if (entityType == ModRegistry.MEDIUM_FIREBALL.get()) {
            instance.register(entityType, ReplacedMediumFireballRenderer::new);
        } else {
            instance.register(entityType, eEntityRendererProvider);
        }
    }
}
