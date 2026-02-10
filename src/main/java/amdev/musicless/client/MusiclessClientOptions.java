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

	private MusiclessClientOptions() {
	}

	public static OptionInstance<Boolean> getMusicEnabledOption() {
		MUSIC_ENABLED_OPTION.set(MusiclessConfig.isMusicEnabled());
		return MUSIC_ENABLED_OPTION;
	}

	private static void onMusicEnabledChanged(boolean enabled) {
		MusiclessConfig.setMusicEnabled(enabled);
		if (enabled) {
			return;
		}

		Minecraft client = Minecraft.getInstance();
		client.getMusicManager().stopPlaying();
		client.getSoundManager().stop(null, SoundSource.MUSIC);
		client.getSoundManager().stop(null, SoundSource.RECORDS);
	}
}
