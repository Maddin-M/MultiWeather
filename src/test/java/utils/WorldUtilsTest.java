package utils;

import de.maddin.multiweather.utils.WorldUtils;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorldUtilsTest {

    private static List<World> worldMocks;
    private static CommandSender senderMock;

    @BeforeAll
    static void setup() {

        World world1 = mock(World.class);
        when(world1.getName()).thenReturn("mo");
        World world2 = mock(World.class);
        when(world2.getName()).thenReturn("mo_nether");
        World world3 = mock(World.class);
        when(world3.getName()).thenReturn("lobby");
        worldMocks = List.of(world1, world2, world3);

        senderMock = mock(CommandSender.class);
        Server serverMock = mock(Server.class);
        when(senderMock.getServer()).thenReturn(serverMock);
        when(serverMock.getWorlds()).thenReturn(worldMocks);
    }

    @Test
    void validSenderShouldReturnItsWorld() {

        Player playerMock = mock(Player.class);
        when(playerMock.getWorld()).thenReturn(worldMocks.get(0));

        World result = WorldUtils.getWorldOfSender(playerMock);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("mo");
    }

    @Test
    void invalidSenderShouldReturnNull() {

        ConsoleCommandSender consoleMock = mock(ConsoleCommandSender.class);
        World result = WorldUtils.getWorldOfSender(consoleMock);
        assertThat(result).isNull();
    }

    @Test
    void allWorldShouldBeReturned() {

        List<World> result = WorldUtils.getAllWorlds(senderMock);

        assertThat(result)
                .isNotEmpty()
                .hasSize(3);
        assertThat(result.get(0).getName()).isEqualTo("mo");
        assertThat(result.get(1).getName()).isEqualTo("mo_nether");
        assertThat(result.get(2).getName()).isEqualTo("lobby");
    }

    @Test
    void emptyArgShouldReturnEveryPossibleCommand() {

        List<String> result = WorldUtils.getAllWorldsAsStringsStartingWith(senderMock, "");
        assertThat(result)
                .hasSize(worldMocks.size() + 1) // "all" is also displayed
                .containsAll(List.of("all", "mo", "mo_nether", "lobby"));
    }

    @Test
    void partialArgShouldReturnPossibleCommands() {

        List<String> result1 = WorldUtils.getAllWorldsAsStringsStartingWith(senderMock, "m");
        List<String> result2 = WorldUtils.getAllWorldsAsStringsStartingWith(senderMock, "LOb");
        assertThat(result1)
                .hasSize(2)
                .containsAll(List.of("mo", "mo_nether"));
        assertThat(result2)
                .hasSize(1)
                .contains("lobby");
    }

    @Test
    void fullArgShouldReturnItsCommand() {

        List<String> result = WorldUtils.getAllWorldsAsStringsStartingWith(
                senderMock, "MO_nether");
        assertThat(result)
                .hasSize(1)
                .contains("mo_nether");
    }
}
