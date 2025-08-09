package com.flarelabsmc.sbfc.mixin;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import net.mehvahdjukaar.amendments.common.ProjectileStats;
import net.mehvahdjukaar.amendments.common.entity.FireballExplosion;
import net.mehvahdjukaar.amendments.reg.ModRegistry;
import net.mehvahdjukaar.moonlight.api.entity.ParticleTrailEmitter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagicFireball.class)
public class MagicFireballMixin {
    @Unique
    private final ParticleTrailEmitter sbfc$trailEmitter = ProjectileStats.makeFireballTrialEmitter();

    @Inject(method = "trailParticles", at = @At("HEAD"), cancellable = true, remap = false)
    private void sbfc$amendmentsTrailParticles(CallbackInfo ci) {
        MagicFireball fireball = (MagicFireball) (Object) this;
        if (fireball.level().isClientSide) {
            this.sbfc$trailEmitter.tick(fireball, (p, v) -> {
                if (!fireball.isInWater()) {
                    fireball.level().addParticle((ParticleOptions) ModRegistry.FIREBALL_TRAIL_PARTICLE.get(), p.x, p.y, p.z, (double)fireball.getBbWidth(), (double)0.0F, (double)0.0F);
                }
            });
        }
        ci.cancel();
    }

    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Explosion;explode()V"), remap = false)
    private void sbfc$amendmentsOnHit0(Explosion instance) {
        MagicFireball fireball = (MagicFireball) (Object) this;
        boolean bl = ServerConfigs.SPELL_GREIFING.get();
        Level.ExplosionInteraction interaction = bl ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE;
        FireballExplosion.ExtraSettings settings = new FireballExplosion.ExtraSettings();
        settings.hasKnockback = false;
        settings.soundVolume = ProjectileStats.PLAYER_FIREBALL.soundVolume();
        settings.onFireTicks = ProjectileStats.PLAYER_FIREBALL.indirectHitFireTicks();
        settings.maxDamage = ProjectileStats.PLAYER_FIREBALL.normalExplosionRadius() + 1.0F;
        FireballExplosion.explodeServer(fireball.level(), fireball, ((AbstractSpell) SpellRegistry.FIREBALL_SPELL.get()).getDamageSource(fireball, fireball.getOwner()), (ExplosionDamageCalculator)null, fireball.getX(), fireball.getY(), fireball.getZ(), fireball.getExplosionRadius() / 2.0F, bl, interaction, settings);
    }

    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeConfigSpec$ConfigValue;get()Ljava/lang/Object;"), remap = false)
    private Object sbfc$amendmentsOnHit1(ForgeConfigSpec.ConfigValue<Boolean> instance) {
        return true;
    }

    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Explosion;finalizeExplosion(Z)V"), remap = false)
    private void sbfc$amendmentsOnHit2(Explosion instance, boolean blockentity) {}
}
