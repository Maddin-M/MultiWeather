package de.maddin.multiweather;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.utils.StringUtils.getAllCommandsAsStringsStartingWith;
import static de.maddin.multiweather.utils.StringUtils.getMessage;
import static de.maddin.multiweather.utils.WorldUtils.getAllWorldsAsStringsStartingWith;

/**
 * This class handles showing available commands while writing them,
 * making it easier to use the plugin.
 */
public class TabCompleteManager implements TabCompleter {

    public TabCompleteManager() {
        setup();
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull final CommandSender sender,
            @NotNull final Command command,
            @NotNull final String alias,
            @NotNull final String[] args) {

        if (args.length == 1) {
            return getAllCommandsAsStringsStartingWith(args[0]);
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                return List.of();
            }
            return getAllWorldsAsStringsStartingWith(sender, args[1]);
        }

        return List.of();
    }

    private void setup() {
        var pluginCommand = MultiWeather.getInstance().getCommand(COMMAND);
        if (pluginCommand != null) {
            pluginCommand.setTabCompleter(this);
        } else {
            Bukkit.getLogger().warning(getMessage("console_command_null"));
        }
    }
}
