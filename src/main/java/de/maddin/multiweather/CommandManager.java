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

    public CommandManager() {
        setup();
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender,
                             @NotNull final Command command,
                             @NotNull final String label,
                             @NotNull final String[] args) {

        if (command.getName().equalsIgnoreCase(COMMAND)) {

            if (args.length == 0) {
                return false;
            }
            return Commands.execute(sender, args);
        }
        return false;
    }

    private void setup() {
        var pluginCommand = MultiWeather.getInstance().getCommand(COMMAND);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        } else {
            Bukkit.getLogger().warning(getMessage("console_command_null"));
        }
    }
}
