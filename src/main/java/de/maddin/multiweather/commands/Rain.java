package de.maddin.multiweather.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.multiweather.Constants.COMMAND;
import static de.maddin.multiweather.Constants.WeatherLength.RAIN;
import static de.maddin.multiweather.Utils.colorString;
import static de.maddin.multiweather.Utils.getAllWorlds;
import static de.maddin.multiweather.Utils.getWorldOfSender;
import static java.lang.String.format;

/**
 * This command starts rain (for the default amount of time).
 */
public class Rain implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        List<World> worldsToClear;
        if (args.length == 1) {
            worldsToClear = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldArg = args[1];
            if (worldArg.equals("all")) {
                worldsToClear = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldArg);
                if (world != null) {
                    worldsToClear = Collections.singletonList(world);
                } else {
                    sender.sendMessage(colorString(format("&cWorld '%s' doesn't exist!", worldArg)));
                    return false;
                }
            }
        }

        for (World world : worldsToClear) {
            world.setStorm(true);
            world.setThundering(false);
            world.setWeatherDuration(RAIN.getDuration());
            sender.sendMessage(colorString(format("Set weather in &b%s&f to &b%s&f.", world.getName(), "rain")));
        }

        return true;
    }

    @Override
    public String getHelp() {
        return colorString(format("&b/%s clear &8[&eworld&8|&eall&8]", COMMAND));
    }
}
