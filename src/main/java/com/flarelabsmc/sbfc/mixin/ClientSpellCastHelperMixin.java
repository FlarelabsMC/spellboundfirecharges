package com.flarelabsmc.sbfc.mixin;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.mehvahdjukaar.amendments.reg.ModRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Partially reuses code from ClientSpellCastHelper from Iron's Spells, to be used in modifying the code itself.
@Mixin(ClientSpellCastHelper.class)
public class ClientSpellCastHelperMixin {
    @Inject(method = "handleClientboundFieryExplosion", at = @At("HEAD"), cancellable = true, remap = false)
    private static void sbfc$handleClientboundFieryExplosion(Vec3 pos, float radius, CallbackInfo ci) {
        MinecraftInstanceHelper.ifPlayerPresent((player) -> {
            Level level = player.level();
            double x = pos.x;
            double y = pos.y;
            double z = pos.z;
            level.addParticle(new BlastwaveParticleOptions(new Vector3f(1.0f, 0.6f, 0.3f), radius + 2.0f), x, y, z, 0.0d, 0.0d, 0.0d);
            level.addParticle(ModRegistry.FIREBALL_EMITTER_PARTICLE.get(), x, y, z, radius, 0.0d, 0.0d);

            int count = (int) (6.28 * (double) radius) * 3;
            float step = 360.0f / (float) count * ((float) Math.PI / 180f);
            float speed = 0.06f + 0.01f * radius;

            for(int i = 0; i < count; ++i) {
                Vec3 offset = (new Vec3(Mth.cos(step * (float) i), 0.0d, Mth.sin(step * (float) i))).scale(speed);
                Vec3 randoffset = Utils.getRandomVec3(0.5d).add(offset.scale(10.0d));
                offset = offset.add(Utils.getRandomVec3(0.01));
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x + randoffset.x, y + randoffset.y, z + randoffset.z, offset.x, offset.y, offset.z);
            }

            int density = 50 + (int)(25.0F * radius);

            for(int i = 0; i < density; i += 2) {
                Vec3 offset = Utils.getRandomVec3(radius).scale(0.2d);
                Vec3 motion = offset.normalize().scale(0.6);
                motion = motion.add(Utils.getRandomVec3(0.18));
                level.addParticle(ParticleHelper.FIERY_SPARKS, x + offset.x * 0.5d, y + offset.y * 0.5d, z + offset.z * 0.5d, motion.x, motion.y, motion.z);
            }

        });
        ci.cancel();
    }
}
