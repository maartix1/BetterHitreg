package you.jass.betterhitreg.mixin;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import you.jass.betterhitreg.settings.Toggle;

import static you.jass.betterhitreg.hitreg.Hitreg.client;


//@Mixin(WorldRenderer.class)
//public class ParticleMixin {
//    @Inject(method = "spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
//    private void spawnParticle(ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
//        if (Toggle.HIDE_ALL_PARTICLES.toggled()) cir.cancel();
//        if (Toggle.HIDE_OTHER_PARTICLES.toggled() && parameters.getType() != ParticleTypes.CRIT && parameters.getType() != ParticleTypes.SWEEP_ATTACK) cir.cancel();
//        if (Toggle.HIDE_OTHER_FIGHTS.toggled() && client.player != null && client.player.squaredDistanceTo(x, y, z) > 30) cir.cancel();
//    }
//}

@Mixin(ParticleManager.class)
public class ParticleMixin {
    @Inject(method = "createParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onCreateParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        if (parameters == null) return;
        if (Toggle.HIDE_ALL_PARTICLES.toggled()) cir.setReturnValue(null);
        else if (Toggle.HIDE_OTHER_PARTICLES.toggled() && parameters.getType() != ParticleTypes.CRIT && parameters.getType() != ParticleTypes.SWEEP_ATTACK) cir.setReturnValue(null);
        else if (Toggle.HIDE_OTHER_FIGHTS.toggled() && client.player != null && client.player.squaredDistanceTo(x, y, z) > 30) cir.setReturnValue(null);
    }
}