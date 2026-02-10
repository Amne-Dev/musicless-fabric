package amdev.musicless.mixin.client;

import amdev.musicless.client.MusiclessClientOptions;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundOptionsScreenMixin {
	@Inject(method = "addOptions", at = @At("TAIL"))
	private void musicless$addMusicToggle(CallbackInfo ci) {
		OptionsList optionsList = ((OptionsSubScreenAccessor) this).musicless$getList();
		if (optionsList != null) {
			optionsList.addBig(MusiclessClientOptions.getMusicEnabledOption());
		}
	}
}
