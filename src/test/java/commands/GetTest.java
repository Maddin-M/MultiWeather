package commands;

import de.maddin.multiweather.commands.Get;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static java.lang.String.format;
import static main.TestUtils.TEST_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetTest {

    private Get getTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        getTest = new Get();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn(TEST_NAME);
        when(worldMock.isClearWeather()).thenReturn(true);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_get_current_world_weather() {

        String[] args = new String[]{"get"};
        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getName();
        verify(worldMock).isClearWeather();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock).sendMessage(format("The weather in \u00A7b%s\u00A7f is \u00A7b%s\u00A7f.",
                TEST_NAME, "clear"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void locked_world_should_display_it() {

        String[] args = new String[]{"get"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);
        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).getName();
        verify(worldMock).isClearWeather();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock).sendMessage(format("The weather in \u00A7b%s\u00A7f is \u00A7b%s\u00A7f. " +
                        "\u00A7eWeather is locked in this world.", TEST_NAME, "clear"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void world_parameter_should_get_its_weather() {

        String[] args = new String[]{"get", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);
        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock).getName();
        verify(worldMock).isClearWeather();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock).sendMessage(format("The weather in \u00A7b%s\u00A7f is \u00A7b%s\u00A7f.",
                TEST_NAME, "clear"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_parameter_should_print_error() {

        String[] args = new String[]{"get", "gami"};
        boolean result = getTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("gami");
        verify(playerMock).sendMessage(format("\u00A7cWorld '%s' doesn't exist!", "gami"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_get_all_worlds() {

        String[] args = new String[]{"get", "all"};
        boolean result = getTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).isClearWeather();
        verify(worldMock, times(2)).getName();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock, times(2)).sendMessage(
                format("The weather in \u00A7b%s\u00A7f is \u00A7b%s\u00A7f.", TEST_NAME, "clear"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
