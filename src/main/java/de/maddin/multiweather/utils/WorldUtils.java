package de.maddin.multiweather.utils;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This utils class handles all world related tasks.
 */
public class WorldUtils {

    private WorldUtils() {
    }

    public static World getWorldOfSender(CommandSender sender) {
        if (sender instanceof Player) {
            var player = (Player) sender;
            return player.getWorld();
        }
        return null;
    }

    public static List<World> getAllWorlds(CommandSender sender) {
        return sender.getServer().getWorlds();
    }

    public static List<String> getAllWorldsAsStringsStartingWith(CommandSender sender, String arg) {
        return getAllWorlds(sender)
                .stream()
                .map(World::getName)
                .filter(name -> name.regionMatches(true, 0, arg, 0, arg.length()))
                .collect(Collectors.toList());
    }
}
