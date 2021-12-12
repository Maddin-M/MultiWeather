package commands;

import de.maddin.multiweather.commands.CommandThunder;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandThunderTest {

    private CommandThunder commandThunderTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        commandThunderTest = new CommandThunder();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn("test_world");
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void noParameterShouldSetThunderInCurrentWorld() {

        String[] args = new String[]{"thunder"};
        boolean result = commandThunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        ArgumentCaptor<Integer> weatherLengthCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(worldMock).setStorm(true);
        verify(worldMock).setThundering(true);
        verify(worldMock).setWeatherDuration(weatherLengthCaptor.capture());
        assertThat(weatherLengthCaptor.getValue()).isBetween(12000, 24000);
        verify(playerMock).sendMessage(
                "Set weather in \u00A7btest_world \u00A7fto \u00A7bthunder\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void worldParameterShouldSetThunderInItsWeather() {

        String[] args = new String[]{"thunder", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);
        boolean result = commandThunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).getName();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        ArgumentCaptor<Integer> weatherLengthCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(worldMock).setStorm(true);
        verify(worldMock).setThundering(true);
        verify(worldMock).setWeatherDuration(weatherLengthCaptor.capture());
        assertThat(weatherLengthCaptor.getValue()).isBetween(12000, 24000);
        verify(playerMock).sendMessage(
                "Set weather in \u00A7btest_world \u00A7fto \u00A7bthunder\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalidWorldParameterShouldPrintError() {

        String[] args = new String[]{"thunder", "faxe kondi"};
        boolean result = commandThunderTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("faxe kondi");
        verify(playerMock).sendMessage("\u00A7cWorld \u00A74faxe kondi \u00A7cdoesn't exist.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void allParameterShouldSetThunderInAllWorlds() {

        String[] args = new String[]{"thunder", "all"};
        boolean result = commandThunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).setStorm(true);
        verify(worldMock, times(2)).setThundering(true);
        verify(worldMock, times(2)).setWeatherDuration(anyInt());
        verify(worldMock, times(2)).getName();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(playerMock, times(2)).sendMessage(
                "Set weather in \u00A7btest_world \u00A7fto \u00A7bthunder\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void lockedWorldShouldDisplayError() {
        String[] args = new String[]{"clear"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);
        boolean result = commandThunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(
                "\u00A7cCan't change the weather in \u00A74test_world \u00A7cif it's locked.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
