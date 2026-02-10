package amdev.musicless;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
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
		musicEnabled = false;

		if (!Files.exists(CONFIG_PATH)) {
			save();
			return;
		}

		Properties properties = new Properties();
		try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
			properties.load(in);
			musicEnabled = Boolean.parseBoolean(properties.getProperty(KEY_MUSIC_ENABLED, "false"));
		} catch (IOException e) {
			Musicless.LOGGER.warn("Failed to load config at {}", CONFIG_PATH, e);
		}
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
