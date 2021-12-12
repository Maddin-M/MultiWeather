package commands;

import de.maddin.multiweather.commands.CommandGet;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandGetTest {

    private CommandGet commandGetTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        commandGetTest = new CommandGet();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn("test_world");
        when(worldMock.isClearWeather()).thenReturn(true);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void noParameterShouldGetCurrentWorldWeather() {

        String[] args = new String[]{"get"};
        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getName();
        verify(worldMock).isClearWeather();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock).sendMessage(
                "The weather in \u00A7btest_world \u00A7fis \u00A7bclear\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void lockedWorldShouldDisplayIt() {

        String[] args = new String[]{"get"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);
        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).getName();
        verify(worldMock).isClearWeather();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock).sendMessage(
                "The weather in \u00A7btest_world \u00A7fis \u00A7bclear\u00A7f. "
                        + "\u00A7eWeather is locked in this world.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void worldParameterShouldGetItsWeather() {

        String[] args = new String[]{"get", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);
        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).getName();
        verify(worldMock).isClearWeather();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock).sendMessage(
                "The weather in \u00A7btest_world \u00A7fis \u00A7bclear\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalidWorldParameterShouldPrintError() {

        String[] args = new String[]{"get", "gami"};
        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("gami");
        verify(playerMock).sendMessage("\u00A7cWorld \u00A74gami \u00A7cdoesn't exist.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void allParameterShouldGetAllWorlds() {

        String[] args = new String[]{"get", "all"};
        boolean result = commandGetTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).isClearWeather();
        verify(worldMock, times(2)).getName();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock, times(2)).sendMessage(
                "The weather in \u00A7btest_world \u00A7fis \u00A7bclear\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
