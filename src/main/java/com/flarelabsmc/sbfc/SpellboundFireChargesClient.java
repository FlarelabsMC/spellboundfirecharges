package com.flarelabsmc.sbfc;

import io.redspace.ironsspellbooks.render.ReplacedFireballRenderer;
import net.mehvahdjukaar.amendments.reg.ModRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SpellboundFireCharges.MODID, value = Dist.CLIENT)
public class SpellboundFireChargesClient {
    @SubscribeEvent
    public static void replaceRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModRegistry.MEDIUM_FIREBALL.get(), (context) -> new ReplacedFireballRenderer(context, 1.25f, 3f));
    }
}
