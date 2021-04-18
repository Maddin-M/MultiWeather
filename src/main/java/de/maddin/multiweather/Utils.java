package de.maddin.multiweather;

import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

/**
 * Utils class to hold all the ugly logic, i don't want to see in the command classes.
 */
public class Utils {

    private Utils() {}

    public static World getWorldOfSender(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return player.getWorld();
        }
        return null;
    }

    public static List<World> getAllWorlds(CommandSender sender) {
        return sender.getServer().getWorlds();
    }

    public static int getRandomNumberInRange(int min, int max) {
        return new Random()
                .ints(min, max)
                .findFirst()
                .orElse(0);
    }

    public static String getWeatherLockedMessage(World world) {
        if (isWeatherLockedInWorld(world)) {
            return " &eWeather is locked in this world.";
        }
        return "";
    }

    public static String getCurrentWeather(World world) {
        if (world.isClearWeather()) {
            return "clear";
        }
        if (world.isThundering()) {
            return "thunder";
        }
        return "rain";
    }

    public static boolean isWeatherLockedInWorld(World world) {
        return Boolean.FALSE.equals(world.getGameRuleValue(GameRule.DO_WEATHER_CYCLE));
    }

    public static String colorString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getVersion(MultiWeather plugin) {
        return plugin.getDescription().getVersion();
    }
}
