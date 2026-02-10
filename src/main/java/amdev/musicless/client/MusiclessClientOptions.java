package amdev.musicless.client;

import amdev.musicless.MusiclessConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.sounds.SoundSource;

public final class MusiclessClientOptions {
	private static final OptionInstance<Boolean> MUSIC_ENABLED_OPTION = OptionInstance.createBoolean(
		"option.musicless.music_enabled",
		MusiclessConfig.isMusicEnabled(),
		MusiclessClientOptions::onMusicEnabledChanged
	);

	private static Boolean lastAppliedEnabled;

	private MusiclessClientOptions() {
	}

	public static OptionInstance<Boolean> getMusicEnabledOption() {
		MUSIC_ENABLED_OPTION.set(MusiclessConfig.isMusicEnabled());
		return MUSIC_ENABLED_OPTION;
	}

	public static void applyCurrentAudioPolicy() {
		boolean enabled = MusiclessConfig.isMusicEnabled();
		applyAudioPolicy(enabled);
		lastAppliedEnabled = enabled;
	}

	public static void syncAudioPolicy() {
		boolean enabled = MusiclessConfig.isMusicEnabled();
		if (lastAppliedEnabled == null || lastAppliedEnabled != enabled) {
			applyAudioPolicy(enabled);
			lastAppliedEnabled = enabled;
		}
	}

	private static void onMusicEnabledChanged(boolean enabled) {
		MusiclessConfig.setMusicEnabled(enabled);
		applyAudioPolicy(enabled);
		lastAppliedEnabled = enabled;
	}

	private static void applyAudioPolicy(boolean enabled) {
		Minecraft client = Minecraft.getInstance();

		if (!enabled) {
			client.getMusicManager().stopPlaying();
			client.getSoundManager().stop(null, SoundSource.MUSIC);
		}

		float musicVolume = client.options.getFinalSoundSourceVolume(SoundSource.MUSIC);
		float recordsVolume = client.options.getFinalSoundSourceVolume(SoundSource.RECORDS);
		client.getSoundManager().updateCategoryVolume(SoundSource.MUSIC, musicVolume);
		client.getSoundManager().updateCategoryVolume(SoundSource.RECORDS, recordsVolume);
	}
}
