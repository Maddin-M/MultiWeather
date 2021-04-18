package de.maddin.multiweather;

import de.maddin.multiweather.commands.Clear;
import de.maddin.multiweather.commands.Command;
import de.maddin.multiweather.commands.Get;
import de.maddin.multiweather.commands.Help;
import de.maddin.multiweather.commands.Lock;
import de.maddin.multiweather.commands.Rain;
import de.maddin.multiweather.commands.Thunder;
import de.maddin.multiweather.commands.Unlock;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multiweather.Utils.colorString;

/**
 * Enum containing all command executors.
 */
public enum Commands {
    GET(new Get()),
    CLEAR(new Clear()),
    RAIN(new Rain()),
    THUNDER(new Thunder()),
    LOCK(new Lock()),
    UNLOCK(new Unlock()),
    HELP(new Help());

    private final Command executor;

    Commands(Command executor) {
        this.executor = executor;
    }

    public static boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        for (Commands cmd : Commands.values()) {
            if (cmd.name().equalsIgnoreCase(args[0])) {
                return cmd.run(sender, args);
            }
        }
        sender.sendMessage(colorString("&cCommand '" + args[0] + "' doesn't exist."));
        return false;
    }

    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        return executor.run(sender, args);
    }

    public String getHelp() {
        return executor.getHelp();
    }
}
