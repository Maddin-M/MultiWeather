package de.maddin.multiweather.utils;

import org.bukkit.GameRule;
import org.bukkit.World;

/**
 * This utils class handles all time related tasks.
 */
public final class WeatherUtils {

    private WeatherUtils() {
    }

    public static String getCurrentWeather(final World world) {
        if (world.isClearWeather()) {
            return "clear";
        }
        if (world.isThundering()) {
            return "thunder";
        }
        return "rain";
    }

    public static boolean isWeatherLockedInWorld(final World world) {
        return Boolean.FALSE.equals(world.getGameRuleValue(GameRule.DO_WEATHER_CYCLE));
    }
}
