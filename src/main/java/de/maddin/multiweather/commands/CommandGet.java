package de.maddin.multiweather.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static de.maddin.multiweather.Constants.ALL_ARG;
import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.utils.StringUtils.getMessage;
import static de.maddin.multiweather.utils.StringUtils.getWeatherLockedMessage;
import static de.maddin.multiweather.utils.WeatherUtils.getCurrentWeather;
import static de.maddin.multiweather.utils.WorldUtils.getAllWorlds;
import static de.maddin.multiweather.utils.WorldUtils.getWorldOfSender;

/**
 * Command to get the weather from a certain world or all worlds.
 */
public class CommandGet implements Command {

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

        affectedWorlds.forEach(world -> sender.sendMessage(getMessage("weather_get_success",
                world.getName(), getCurrentWeather(world), getWeatherLockedMessage(world))));
        return true;
    }

    @Override
    public String getHelp() {
        return getMessage("weather_get_help", COMMAND);
    }
}
