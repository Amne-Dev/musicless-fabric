package amdev.musicless.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MusiclessClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientLifecycleEvents.CLIENT_STARTED.register(client -> MusiclessClientOptions.applyCurrentAudioPolicy());
		ClientTickEvents.END_CLIENT_TICK.register(client -> MusiclessClientOptions.syncAudioPolicy());
	}
}
