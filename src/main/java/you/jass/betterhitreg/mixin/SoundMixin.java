package you.jass.betterhitreg.mixin;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import you.jass.betterhitreg.hitreg.Hitreg;
import you.jass.betterhitreg.settings.Toggle;

@Mixin(SoundSystem.class)
public class SoundMixin {
    //version 1.21.7-
//    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/Channel$SourceManager;run(Ljava/util/function/Consumer;)V", ordinal = 0, shift = At.Shift.AFTER))
//    private void before(SoundInstance sound, CallbackInfo ci) {
//        if (sound == null || sound.getId() == null) return;
//        if (Hitreg.muffleAmount != 0 || Hitreg.sharpenAmount != 0) filter(sound);
//    }

    //version 1.21.7-
//    @Inject (method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
//    private void play(SoundInstance sound, CallbackInfo ci) {
//        if (sound == null || sound.getId() == null) return;
//        if (sound.getCategory() != SoundCategory.PLAYERS || sound.getId().getPath().startsWith("entity.player.attack") || sound.getId().getPath().startsWith("entity.player.hurt")) return;
//        if (Toggle.SILENCE_NON_HITS.toggled()) ci.cancel();
//    }

    //version 1.21.8+
    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)Lnet/minecraft/client/sound/SoundSystem$PlayResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/Channel$SourceManager;run(Ljava/util/function/Consumer;)V", ordinal = 0, shift = At.Shift.AFTER))
    private void before(SoundInstance sound, CallbackInfoReturnable<?> cir) {
        if (sound == null || sound.getId() == null) return;
        if (Hitreg.muffleAmount != 0 || Hitreg.sharpenAmount != 0) filter(sound);
    }

    //version 1.21.8+
    @Inject (method = "play(Lnet/minecraft/client/sound/SoundInstance;)Lnet/minecraft/client/sound/SoundSystem$PlayResult;", at = @At("HEAD"), cancellable = true)
    private void play(SoundInstance sound, CallbackInfoReturnable<SoundSystem.PlayResult> cir) {
        if (sound == null || sound.getId() == null) return;
        if (sound.getCategory() != SoundCategory.PLAYERS || sound.getId().getPath().startsWith("entity.player.attack") || sound.getId().getPath().startsWith("entity.player.hurt")) return;
        if (Toggle.SILENCE_NON_HITS.toggled()) cir.cancel();
    }

    @Unique
    private void filter(SoundInstance sound) {
        if (!sound.getId().getPath().startsWith("entity.player.attack") && !sound.getId().getPath().contains("hurt")) return;
        if (Hitreg.muffleAmount != 0 || Hitreg.sharpenAmount != 0) Hitreg.shouldFilter++;
    }
}