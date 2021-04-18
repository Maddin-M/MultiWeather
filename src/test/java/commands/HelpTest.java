package commands;

import de.maddin.multiweather.Commands;
import de.maddin.multiweather.commands.Help;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelpTest {

    private Help helpTest;
    private Player playerMock;

    @BeforeEach
    public void setup() {
        helpTest = new Help();
        playerMock = mock(Player.class);
    }

    @Test
    void should_display_help() {

        String[] args = new String[]{"help"};

        boolean result = helpTest.run(playerMock, args);
        assertThat(result).isTrue();

        // -1 because help itself doesn't provide a help message
        verify(playerMock, times(Commands.values().length - 1)).sendMessage(anyString());
        verifyNoMoreInteractions(playerMock);
    }
}
