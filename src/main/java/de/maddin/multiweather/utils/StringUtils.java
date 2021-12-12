package de.maddin.multiweather.utils;

import de.maddin.multiweather.Commands;
import org.bukkit.World;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static de.maddin.multiweather.utils.WeatherUtils.isWeatherLockedInWorld;

/**
 * This utils class handles all String related tasks.
 */
public final class StringUtils {

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("messages");

    private StringUtils() {
    }

    public static String getWeatherLockedMessage(final World world) {
        if (isWeatherLockedInWorld(world)) {
            return getMessage("weather_get_locked");
        }
        return "";
    }

    public static List<String> getAllCommandsAsStringsStartingWith(final String arg) {
        return Arrays.stream(Commands.values())
                .map(Commands::getCommand)
                .filter(cmd -> cmd.startsWith(arg.toLowerCase()))
                .toList();
    }

    public static String getMessage(final String s, final Object... args) {
        var msg = RESOURCE.getString(s);
        return args == null ? msg : MessageFormat.format(msg, args);
    }
}
