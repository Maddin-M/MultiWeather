package commands;

import de.maddin.multiweather.commands.Unlock;
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
class UnlockTest {

    private Unlock unlockTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        unlockTest = new Unlock();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn(TEST_NAME);
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void no_parameter_should_unlock_current_world() {

        String[] args = new String[]{"unlock"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);

        boolean result = unlockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).setGameRule(GameRule.DO_WEATHER_CYCLE, true);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(format("Unlocked weather in world \u00A7b%s\u00A7f.", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void valid_parameter_should_unlock_its_world() {

        String[] args = new String[]{"unlock", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);

        boolean result = unlockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).setGameRule(GameRule.DO_WEATHER_CYCLE, true);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(format("Unlocked weather in world \u00A7b%s\u00A7f.", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalid_world_should_return_error_message() {

        String[] args = new String[]{"unlock", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(null);

        boolean result = unlockTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(playerMock).sendMessage(format("\u00A7cWorld '%s' doesn't exist!", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void already_unlocked_world_should_return_info_message() {

        String[] args = new String[]{"unlock", TEST_NAME};
        when(serverMock.getWorld(TEST_NAME)).thenReturn(worldMock);
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(true);

        boolean result = unlockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld(TEST_NAME);
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(format("Weather in world \u00A7b%s\u00A7f is already unlocked.", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void all_parameter_should_unlock_all_worlds() {

        String[] args = new String[]{"unlock", "all"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);

        boolean result = unlockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock, times(2)).setGameRule(GameRule.DO_WEATHER_CYCLE, true);
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2)).sendMessage(
                format("Unlocked weather in world \u00A7b%s\u00A7f.", TEST_NAME));
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
