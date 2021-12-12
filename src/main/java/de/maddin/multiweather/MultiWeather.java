package de.maddin.multiweather;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import static de.maddin.multiweather.Constants.BSTATS_PLUGIN_ID;

/**
 * @author Maddin
 * @since 18.04.2021
 */
public class MultiWeather extends JavaPlugin {

    private boolean updateAvailable = false;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        new CommandManager();
        new TabCompleteManager();

        new Metrics(this, BSTATS_PLUGIN_ID);
        new UpdateChecker().checkForUpdate();

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
    }

    public static MultiWeather getInstance() {
        return getPlugin(MultiWeather.class);
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(final boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }
}
