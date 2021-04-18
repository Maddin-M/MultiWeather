package commands;

import de.maddin.multiweather.commands.Thunder;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static java.lang.String.format;
import static main.TestUtils.TEST_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ThunderTest {

    private Thunder thunderTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        thunderTest = new Thunder();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn(TEST_NAME);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_set_thunder_in_current_world() {

        String[] args = new String[]{"thunder"};
        boolean result = thunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getName();
        ArgumentCaptor<Integer> weatherLengthCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(worldMock).setStorm(true);
        verify(worldMock).setThundering(true);
        verify(worldMock).setWeatherDuration(weatherLengthCaptor.capture());
        assertThat(weatherLengthCaptor.getValue()).isBetween(12000, 24000);
        verify(playerMock).sendMessage(format("Set weather in \u00A7b%s\u00A7f to \u00A7b%s\u00A7f.",
                TEST_NAME, "thunder"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void world_parameter_should_set_thunder_in_its_weather() {

        String[] args = new String[]{"thunder", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);
        boolean result = thunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock).getName();
        ArgumentCaptor<Integer> weatherLengthCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(worldMock).setStorm(true);
        verify(worldMock).setThundering(true);
        verify(worldMock).setWeatherDuration(weatherLengthCaptor.capture());
        assertThat(weatherLengthCaptor.getValue()).isBetween(12000, 24000);
        verify(playerMock).sendMessage(format("Set weather in \u00A7b%s\u00A7f to \u00A7b%s\u00A7f.",
                TEST_NAME, "thunder"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_parameter_should_print_error() {

        String[] args = new String[]{"thunder", "faxe kondi"};
        boolean result = thunderTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("faxe kondi");
        verify(playerMock).sendMessage(format("\u00A7cWorld '%s' doesn't exist!", "faxe kondi"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_set_thunder_in_all_worlds() {

        String[] args = new String[]{"thunder", "all"};
        boolean result = thunderTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).setStorm(true);
        verify(worldMock, times(2)).setThundering(true);
        verify(worldMock, times(2)).setWeatherDuration(anyInt());
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2)).sendMessage(
                format("Set weather in \u00A7b%s\u00A7f to \u00A7b%s\u00A7f.", TEST_NAME, "thunder"));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
