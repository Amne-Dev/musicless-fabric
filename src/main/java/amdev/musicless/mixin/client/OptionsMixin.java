package amdev.musicless.mixin.client;

import amdev.musicless.MusiclessConfig;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Options.class)
public abstract class OptionsMixin {
	@Inject(method = "getSoundSourceVolume", at = @At("RETURN"), cancellable = true)
	private void musicless$muteRecordsVolumeWhenDisabled(SoundSource source, CallbackInfoReturnable<Float> cir) {
		if (!MusiclessConfig.isMusicEnabled() && source == SoundSource.RECORDS) {
			cir.setReturnValue(0.0F);
		}
	}

	@Inject(method = "getFinalSoundSourceVolume", at = @At("RETURN"), cancellable = true)
	private void musicless$muteFinalRecordsVolumeWhenDisabled(SoundSource source, CallbackInfoReturnable<Float> cir) {
		if (!MusiclessConfig.isMusicEnabled() && source == SoundSource.RECORDS) {
			cir.setReturnValue(0.0F);
		}
	}
}
