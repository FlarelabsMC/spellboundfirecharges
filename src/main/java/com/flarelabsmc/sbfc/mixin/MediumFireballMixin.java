package com.flarelabsmc.sbfc.mixin;

import io.redspace.ironsspellbooks.network.spell.ClientboundFieryExplosionParticles;
import io.redspace.ironsspellbooks.setup.Messages;
import net.mehvahdjukaar.amendments.common.entity.MediumFireball;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MediumFireball.class)
public class MediumFireballMixin {
    @Inject(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/mehvahdjukaar/amendments/common/entity/FireballExplosion;explodeServer(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;Lnet/mehvahdjukaar/amendments/common/entity/FireballExplosion$ExtraSettings;)Lnet/mehvahdjukaar/amendments/common/entity/FireballExplosion;",
                    shift = At.Shift.AFTER,
                    remap = false
            )
    )
    private void sbfc$amendmentsMediumFireballOnHit(HitResult result, CallbackInfo ci) {
        MediumFireball fireball = (MediumFireball) (Object) this;
        Messages.sendToPlayersTrackingEntity(new ClientboundFieryExplosionParticles(new Vec3(fireball.getX(), fireball.getY() + (double)0.15F, fireball.getZ()), 1.0f), fireball);
    }
}
