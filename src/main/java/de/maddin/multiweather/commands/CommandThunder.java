package de.maddin.multiweather.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static de.maddin.multiweather.Constants.ALL_ARG;
import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.Constants.WeatherPresets.THUNDER;
import static de.maddin.multiweather.utils.StringUtils.getMessage;
import static de.maddin.multiweather.utils.WeatherUtils.isWeatherLockedInWorld;
import static de.maddin.multiweather.utils.WorldUtils.getAllWorlds;
import static de.maddin.multiweather.utils.WorldUtils.getWorldOfSender;

/**
 * This command makes it rain and thunder (for the dafult amount of time).
 */
public class CommandThunder implements Command {

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
                sender.sendMessage(getMessage("error_weather_is_locked", world.getName()));
            } else {
                world.setStorm(true);
                world.setThundering(true);
                world.setWeatherDuration(THUNDER.getDuration());
                sender.sendMessage(getMessage("weather_thunder_success", world.getName()));
            }
        });
        return true;
    }

    @Override
    public String getHelp() {
        return getMessage("weather_thunder_help", COMMAND);
    }
}
