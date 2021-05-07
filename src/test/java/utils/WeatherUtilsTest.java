package utils;

import de.maddin.multiweather.utils.WeatherUtils;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherUtilsTest {

    @Test
    void should_return_correct_weather_state() {

        World clearWorld = mock(World.class);
        when(clearWorld.isClearWeather()).thenReturn(true);
        when(clearWorld.isThundering()).thenReturn(false);

        World rainingWorld = mock(World.class);
        when(rainingWorld.isClearWeather()).thenReturn(false);
        when(rainingWorld.isThundering()).thenReturn(false);

        World thunderingWorld = mock(World.class);
        when(thunderingWorld.isClearWeather()).thenReturn(false);
        when(thunderingWorld.isThundering()).thenReturn(true);

        String result1 = WeatherUtils.getCurrentWeather(clearWorld);
        String result2 = WeatherUtils.getCurrentWeather(rainingWorld);
        String result3 = WeatherUtils.getCurrentWeather(thunderingWorld);

        assertThat(result1).isEqualTo("clear");
        assertThat(result2).isEqualTo("rain");
        assertThat(result3).isEqualTo("thunder");
    }

    @Test
    void should_return_correct_time_game_rule() {

        World lockedWorld = mock(World.class);
        when(lockedWorld.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);
        World unlockedWorld = mock(World.class);
        when(unlockedWorld.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(true);
        World buggyWorld = mock(World.class);
        when(buggyWorld.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(null);

        boolean result1 = WeatherUtils.isWeatherLockedInWorld(lockedWorld);
        boolean result2 = WeatherUtils.isWeatherLockedInWorld(unlockedWorld);
        boolean result3 = WeatherUtils.isWeatherLockedInWorld(buggyWorld);

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
        assertThat(result3).isFalse();
    }
}
