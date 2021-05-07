package de.maddin.multiweather;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.utils.StringUtils.getMessage;

/**
 * This class sends the command with its args to the correct executor class.
 */
public class CommandManager implements CommandExecutor {

    private final MultiWeather plugin;

    public CommandManager(MultiWeather plugin) {
        this.plugin = plugin;
        setup();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase(COMMAND)) {

            if (args.length == 0) {
                sender.sendMessage(plugin.getName() + " " + plugin.getDescription().getVersion());
                return false;
            }
            return Commands.execute(sender, args);
        }
        return false;
    }

    private void setup() {
        var pluginCommand = plugin.getCommand(COMMAND);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        } else {
            Bukkit.getLogger().warning(getMessage("console_command_null"));
        }
    }
}
