package de.maddin.multiweather.commands;

import de.maddin.multiweather.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Displays all possible commands.
 */
public class CommandHelp implements Command {

    @Override
    public boolean run(@NotNull final CommandSender sender, @NotNull final String[] args) {

        for (Commands cmd : Commands.values()) {
            String helpMessage = cmd.getHelp();
            if (helpMessage != null) {
                sender.sendMessage(helpMessage);
            }
        }
        return true;
    }
}
