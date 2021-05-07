package de.maddin.multiweather.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static de.maddin.multiweather.Constants.ALL_ARG;
import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.Constants.WeatherPresets.RAIN;
import static de.maddin.multiweather.utils.StringUtils.getMessage;
import static de.maddin.multiweather.utils.WeatherUtils.isWeatherLockedInWorld;
import static de.maddin.multiweather.utils.WorldUtils.getAllWorlds;
import static de.maddin.multiweather.utils.WorldUtils.getWorldOfSender;

/**
 * This command starts rain (for the default amount of time).
 */
public class CommandRain implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
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
                sender.sendMessage(getMessage("error_weather_is_locked"));
            } else {
                world.setStorm(true);
                world.setThundering(false);
                world.setWeatherDuration(RAIN.getDuration());
                sender.sendMessage(getMessage("weather_rain_success", world.getName()));
            }
        });
        return true;
    }

    @Override
    public String getHelp() {
        return getMessage("weather_rain_help", COMMAND);
    }
}
