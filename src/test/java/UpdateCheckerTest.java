import de.maddin.multiweather.MultiWeather;
import de.maddin.multiweather.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

import java.util.Scanner;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateCheckerTest {

    private static final String MESSAGE_NEW_UPDATE =
            "[MultiWeather] There is a new update available!";
    private static final String MESSAGE_VERSION_TOO_NEW =
            "[MultiWeather] You are running a newer version than available? "
                    + "Are you from the future?";

    private MultiWeather multiWeatherMock;
    private BukkitScheduler bukkitSchedulerMock;
    private Logger loggerMock;
    private PluginDescriptionFile descriptionMock;

    @BeforeEach
    private void setup() {
        bukkitSchedulerMock = mock(BukkitScheduler.class);
        descriptionMock = mock(PluginDescriptionFile.class);
        loggerMock = mock(Logger.class);
        multiWeatherMock = mock(MultiWeather.class);

        when(multiWeatherMock.getDescription()).thenReturn(descriptionMock);
        when(bukkitSchedulerMock.runTaskAsynchronously(any(), any(Runnable.class)))
                .thenAnswer(
                        (Answer<String>) invocation -> {
                            Runnable arg = (Runnable) invocation.getArguments()[1];
                            arg.run();
                            return null;
                        });
    }

    @ParameterizedTest
    @CsvSource({
            "1.2.3, 2.2.3, " + MESSAGE_NEW_UPDATE,
            "1.2.3, 1.3.3, " + MESSAGE_NEW_UPDATE,
            "1.2.3, 1.2.4, " + MESSAGE_NEW_UPDATE,
            "1.2.3, 1.2.3,",
            "1.2.3, 0.2.3, " + MESSAGE_VERSION_TOO_NEW,
            "1.2.3, 1.1.3, " + MESSAGE_VERSION_TOO_NEW,
            "1.2.3, 1.2.2, " + MESSAGE_VERSION_TOO_NEW,
    })
    void differentVersionShouldSendMessage(final String installedVersion,
                                           final String latestVersion,
                                           final String expectedMessage) {
        try (MockedStatic<Bukkit> bukkitMockedStatic = mockStatic(Bukkit.class);
             MockedStatic<MultiWeather> multiWeatherMockedStatic = mockStatic(MultiWeather.class);
             MockedConstruction<Scanner> ignored = mockConstruction(Scanner.class,
                     (mock, context) -> {
                         when(mock.hasNext()).thenReturn(true);
                         when(mock.next()).thenReturn(latestVersion);
                     })
        ) {
            // given
            multiWeatherMockedStatic.when(MultiWeather::getInstance).thenReturn(multiWeatherMock);
            bukkitMockedStatic.when(Bukkit::getScheduler).thenReturn(bukkitSchedulerMock);
            bukkitMockedStatic.when(Bukkit::getLogger).thenReturn(loggerMock);
            when(descriptionMock.getVersion()).thenReturn(installedVersion);

            // when
            new UpdateChecker().checkForUpdate();

            // then
            if (expectedMessage != null) {
                verify(loggerMock).warning(expectedMessage);
                if (expectedMessage.equals(MESSAGE_NEW_UPDATE)) {
                    verify(multiWeatherMock).setUpdateAvailable(true);
                }
            }
        }
    }
}
