package de.maddin.multiweather;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.maddin.multiweather.Utils.colorString;

/**
 * Listens to users logging in, if update is available. Notifies OPs in chat to update the plugin.
 */
public class LoginListener implements Listener {

    private final MultiWeather plugin;

    public LoginListener(MultiWeather plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.isUpdateAvailable() && player.isOp()) {
            player.sendMessage(colorString("&eA new version of MultiWeather is available!"));
        }
    }
}
