package amdev.musicless.mixin.client;

import amdev.musicless.MusiclessConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin {
	@Inject(method = "play", at = @At("HEAD"), cancellable = true)
	private void musicless$cancelMusic(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
		if (MusiclessConfig.isMusicEnabled()) {
			return;
		}

		SoundSource category = sound.getSource();
		if (category == SoundSource.MUSIC) {
			cir.setReturnValue(SoundEngine.PlayResult.NOT_STARTED);
		}
	}

	@Inject(method = "reload", at = @At("TAIL"))
	private void musicless$syncRecordsVolumeAfterReload(CallbackInfo ci) {
		Minecraft client = Minecraft.getInstance();
		float recordsVolume = MusiclessConfig.isMusicEnabled()
			? client.options.getSoundSourceVolume(SoundSource.RECORDS)
			: 0.0F;
		((SoundManager) (Object) this).updateCategoryVolume(SoundSource.RECORDS, recordsVolume);
	}
}

