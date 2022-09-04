package com.neomechanical.neoperformance.commands.chunks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChunkTeleport {
    public static void teleportToChunk(String[] args) {
        if (args.length == 6) {
            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                return;
            }
            try {
                double x = Double.parseDouble(args[2]);
                double y = Double.parseDouble(args[3]);
                double z = Double.parseDouble(args[4]);
                Player player = Bukkit.getPlayer(args[5]);
                if (player == null) {
                    return;
                }
                Location location = new Location(world, x, y, z);
                player.teleport(location);
            } catch (NumberFormatException ignored) {
            }
        }
    }
}
