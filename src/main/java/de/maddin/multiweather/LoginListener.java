package de.maddin.multiweather;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.maddin.multiweather.utils.StringUtils.getMessage;

/**
 * Listens to users logging in, if update is available. Notifies OPs in chat to update the plugin.
 */
public record LoginListener() implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        var player = event.getPlayer();
        if (MultiWeather.getInstance().isUpdateAvailable() && player.isOp()) {
            player.sendMessage(getMessage("msg_op_update_available"));
        }
    }
}
