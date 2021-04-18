package de.maddin.multiweather;

import org.bstats.bukkit.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multiweather.Constants.BSTATS_PLUGIN_ID;

/**
 * @author Maddin
 * @since 18.04.2021
 */
public class MultiWeather extends JavaPlugin {

    private CommandManager commandManager;
    private boolean updateAvailable = false;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        commandManager = new CommandManager(this);
        commandManager.setup();

        new Metrics(this, BSTATS_PLUGIN_ID);
        new UpdateChecker(this).checkForUpdate();
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        return commandManager.onCommand(sender, command, label, args);
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }
}
