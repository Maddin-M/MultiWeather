package de.maddin.multiweather;

import java.util.Random;

/**
 * Class containing all relevant values, that never change.
 */
public class Constants {

    private Constants() {
    }

    public static final String COMMAND = "weather";
    public static final int BSTATS_PLUGIN_ID = 11077;
    public static final int SPIGOT_PLUGIN_ID = 91452;
    public static final String SPIGOT_API_LINK = "https://api.spigotmc.org/legacy/update.php?resource=";
    public static final String ALL_ARG = "all";

    /**
     * Enum representing the default lengths of certain weather.
     */
    public enum WeatherPresets {

        CLEAR(12000, 180000), RAIN(12000, 24000), THUNDER(12000, 24000);

        private final int min;
        private final int max;

        WeatherPresets(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getDuration() {
            return new Random()
                    .ints(min, max)
                    .findFirst()
                    .orElse(0);
        }

        public String getCommand() {
            return name().toLowerCase();
        }
    }
}
