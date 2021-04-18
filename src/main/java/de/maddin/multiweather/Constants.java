package de.maddin.multiweather;

import static de.maddin.multiweather.Utils.getRandomNumberInRange;

/**
 * Class containing all relevant values, that never change.
 */
public class Constants {

    private Constants() {}

    public static final String COMMAND = "weather";
    public static final int BSTATS_PLUGIN_ID = 11077;
    public static final int SPIGOT_PLUGIN_ID = 91452;
    public static final String SPIGOT_API_LINK = "https://api.spigotmc.org/legacy/update.php?resource=";

    /**
     * Enum representing the default lengths of certain weather.
     */
    public enum WeatherLength {

        CLEAR(12000, 180000), RAIN(12000, 24000), THUNDER(12000, 24000);

        private final int min;
        private final int max;

        WeatherLength(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getDuration() {
            return getRandomNumberInRange(min, max);
        }
    }
}
