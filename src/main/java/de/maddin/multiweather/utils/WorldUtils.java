package de.maddin.multiweather.utils;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Stream;

import static de.maddin.multiweather.Constants.ALL_ARG;

/**
 * This utils class handles all world related tasks.
 */
public final class WorldUtils {

    private WorldUtils() {
    }

    public static World getWorldOfSender(final CommandSender sender) {
        if (sender instanceof Player player) {
            return player.getWorld();
        }
        return null;
    }

    public static List<World> getAllWorlds(final CommandSender sender) {
        return sender.getServer().getWorlds();
    }

    public static List<String> getAllWorldsAsStringsStartingWith(
            final CommandSender sender, final String arg) {
        Stream<String> all = Stream.of(ALL_ARG);
        Stream<String> worlds = getAllWorlds(sender)
                .stream()
                .map(World::getName);
        return Stream.concat(all, worlds)
                .filter(name -> name.regionMatches(true, 0, arg, 0, arg.length()))
                .toList();
    }
}
