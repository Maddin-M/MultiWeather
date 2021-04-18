package de.maddin.multiweather.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Blueprint for all command classes.
 */
public interface Command {

    boolean run(@NotNull CommandSender sender, @NotNull String[] args);

    default String getHelp() {
        return null;
    }
}
