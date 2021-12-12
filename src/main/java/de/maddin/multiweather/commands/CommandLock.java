package de.maddin.multiweather.commands;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static de.maddin.multiweather.Constants.ALL_ARG;
import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.utils.StringUtils.getMessage;
import static de.maddin.multiweather.utils.WeatherUtils.isWeatherLockedInWorld;
import static de.maddin.multiweather.utils.WorldUtils.getAllWorlds;
import static de.maddin.multiweather.utils.WorldUtils.getWorldOfSender;

/**
 * Locks weather in a specified world or all worlds.
 */
public class CommandLock implements Command {

    @Override
    public boolean run(@NotNull final CommandSender sender, @NotNull final String[] args) {
        if (args.length >= 3) {
            sender.sendMessage(getMessage("error_too_many_parameters"));
            return false;
        }

        List<World> affectedWorlds;
        if (args.length == 1) {
            affectedWorlds = List.of(getWorldOfSender(sender));

        } else {
            String worldParameter = args[1];
            if (worldParameter.equals(ALL_ARG)) {
                affectedWorlds = getAllWorlds(sender);

            } else {
                var world = sender.getServer().getWorld(worldParameter);
                if (world == null) {
                    sender.sendMessage(getMessage("error_invalid_world", worldParameter));
                    return false;
                }
                affectedWorlds = List.of(world);
            }
        }

        affectedWorlds.forEach(world -> {
            if (isWeatherLockedInWorld(world)) {
                sender.sendMessage(getMessage("weather_lock_already_locked", world.getName()));
            } else {
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                sender.sendMessage(getMessage("weather_lock_success", world.getName()));
            }
        });
        return true;
    }

    @Override
    public String getHelp() {
        return getMessage("weather_lock_help", COMMAND);
    }
}
