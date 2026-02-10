package amdev.musicless;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class MusiclessConfig {
	private static final String KEY_MUSIC_ENABLED = "musicEnabled";
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("musicless.properties");

	private static boolean musicEnabled = false;

	private MusiclessConfig() {
	}

	public static synchronized void load() {
		// Always start disabled by default when the game boots.
		musicEnabled = false;
		save();
	}

	public static synchronized boolean isMusicEnabled() {
		return musicEnabled;
	}

	public static synchronized void setMusicEnabled(boolean enabled) {
		musicEnabled = enabled;
		save();
	}

	private static synchronized void save() {
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			Properties properties = new Properties();
			properties.setProperty(KEY_MUSIC_ENABLED, Boolean.toString(musicEnabled));
			try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
				properties.store(out, "Musicless config");
			}
		} catch (IOException e) {
			Musicless.LOGGER.warn("Failed to save config at {}", CONFIG_PATH, e);
		}
	}
}
