package commands;

import de.maddin.multiweather.commands.CommandLock;
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
class CommandLockTest {

    private CommandLock commandLockTest;
    private Player playerMock;
    private World worldMock;
    private Server serverMock;

    @BeforeEach
    public void setup() {
        commandLockTest = new CommandLock();
        playerMock = mock(Player.class);
        worldMock = mock(World.class);
        serverMock = mock(Server.class);
        when(playerMock.getServer()).thenReturn(serverMock);
        when(playerMock.getWorld()).thenReturn(worldMock);
        when(worldMock.getName()).thenReturn("test_world");
        when(serverMock.getWorlds()).thenReturn(List.of(worldMock, worldMock));
    }

    @Test
    void noParameterShouldUnlockCurrentWorld() {

        String[] args = new String[]{"lock"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(true);

        boolean result = commandLockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getWorld();
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        verify(worldMock).getName();
        verify(playerMock).sendMessage("Locked weather in world \u00A7btest_world\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void validParameterShouldUnlockItsWorld() {

        String[] args = new String[]{"lock", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(true);

        boolean result = commandLockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        verify(worldMock).getName();
        verify(playerMock).sendMessage("Locked weather in world \u00A7btest_world\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void invalidWorldShouldReturnErrorMessage() {

        String[] args = new String[]{"lock", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(null);

        boolean result = commandLockTest.run(playerMock, args);
        assertThat(result).isFalse();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(playerMock).sendMessage("\u00A7cWorld \u00A74test_world \u00A7cdoesn't exist.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void alreadyUnlockedWorldShouldReturnInfoMessage() {

        String[] args = new String[]{"lock", "test_world"};
        when(serverMock.getWorld("test_world")).thenReturn(worldMock);
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(false);

        boolean result = commandLockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorld("test_world");
        verify(worldMock).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock).getName();
        verify(playerMock).sendMessage(
                "Weather in world \u00A7btest_world \u00A7fis already locked.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }

    @Test
    void allParameterShouldUnlockAllWorlds() {

        String[] args = new String[]{"lock", "all"};
        when(worldMock.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)).thenReturn(true);

        boolean result = commandLockTest.run(playerMock, args);
        assertThat(result).isTrue();

        verify(playerMock).getServer();
        verify(serverMock).getWorlds();
        verify(worldMock, times(2)).getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
        verify(worldMock, times(2)).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        verify(worldMock, times(2)).getName();
        verify(playerMock, times(2)).sendMessage(
                "Locked weather in world \u00A7btest_world\u00A7f.");
        verifyNoMoreInteractions(playerMock, worldMock, serverMock);
    }
}
