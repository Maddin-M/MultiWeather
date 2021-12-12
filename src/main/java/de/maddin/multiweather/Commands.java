package de.maddin.multiweather;

import de.maddin.multiweather.commands.Command;
import de.maddin.multiweather.commands.CommandClear;
import de.maddin.multiweather.commands.CommandGet;
import de.maddin.multiweather.commands.CommandHelp;
import de.maddin.multiweather.commands.CommandLock;
import de.maddin.multiweather.commands.CommandRain;
import de.maddin.multiweather.commands.CommandThunder;
import de.maddin.multiweather.commands.CommandUnlock;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multiweather.utils.StringUtils.getMessage;

/**
 * Enum containing all command executors.
 */
public enum Commands {
    GET(new CommandGet()),
    CLEAR(new CommandClear()),
    RAIN(new CommandRain()),
    THUNDER(new CommandThunder()),
    LOCK(new CommandLock()),
    UNLOCK(new CommandUnlock()),
    HELP(new CommandHelp());

    private final Command executor;

    Commands(final Command executor) {
        this.executor = executor;
    }

    public static boolean execute(
            @NotNull final CommandSender sender,
            @NotNull final String[] args) {

        for (Commands cmd : Commands.values()) {
            if (cmd.getCommand().equals(args[0].toLowerCase())) {
                return cmd.run(sender, args);
            }
        }
        sender.sendMessage(getMessage("error_invalid_command", args[0]));
        return false;
    }

    public boolean run(@NotNull final CommandSender sender,
                       @NotNull final String[] args) {
        return executor.run(sender, args);
    }

    public String getHelp() {
        return executor.getHelp();
    }

    public String getCommand() {
        return name().toLowerCase();
    }
}
