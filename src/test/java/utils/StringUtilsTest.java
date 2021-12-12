package utils;

import de.maddin.multiweather.Commands;
import de.maddin.multiweather.utils.StringUtils;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StringUtilsTest {

    @Test
    void lockedWorldShouldReturnLockedMessage() {

        World worldMock = mock(World.class);
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);

        String result = StringUtils.getWeatherLockedMessage(worldMock);

        assertThat(result).isEqualTo(" \u00A7eWeather is locked in this world.");
    }

    @Test
    void unlockedWorldShouldNotReturnLockedMessage() {

        World worldMock = mock(World.class);
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(true);

        String result = StringUtils.getWeatherLockedMessage(worldMock);

        assertThat(result).isEmpty();
    }

    @Test
    void nullWorldShouldNotReturnLockedMessage() {

        World worldMock = mock(World.class);
        when(worldMock.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)).thenReturn(null);

        String result = StringUtils.getWeatherLockedMessage(worldMock);

        assertThat(result).isEmpty();
    }

    @Test
    void emptyArgShouldReturnEveryPossibleCommand() {

        List<String> result = StringUtils.getAllCommandsAsStringsStartingWith("");

        assertThat(result.size()).isEqualTo(Commands.values().length);
        assertThat(result.containsAll(List.of(
                "clear", "get", "help", "lock", "rain", "thunder", "unlock"))).isTrue();
    }

    @Test
    void partialArgShouldReturnPossibleCommands() {

        List<String> result1 = StringUtils.getAllCommandsAsStringsStartingWith("g");
        List<String> result2 = StringUtils.getAllCommandsAsStringsStartingWith("uNl");

        assertThat(result1.size()).isEqualTo(1);
        assertThat(result2.size()).isEqualTo(1);
        assertThat(result1.contains("get")).isTrue();
        assertThat(result2.contains("unlock")).isTrue();
    }

    @Test
    void fullArgShouldReturnItsCommand() {

        List<String> result = StringUtils.getAllCommandsAsStringsStartingWith("Get");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains("get")).isTrue();
    }

    @Test
    void correctStringShouldReturnItsMessage() {

        String result1 = StringUtils.getMessage("weather_get_locked");
        String result2 = StringUtils.getMessage("error_invalid_world", "test_world");

        assertThat(result1).isEqualTo(" \u00A7eWeather is locked in this world.");
        assertThat(result2).isEqualTo("\u00A7cWorld \u00A74test_world \u00A7cdoesn't exist.");
    }
}
