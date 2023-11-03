package com.neomechanical.neoperformance.commands.chunks;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ChunkTeleportCommand extends Command {
    public ChunkTeleportCommand() {
        super.tabCompleteName(false);
    }

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public String getPermission() {
        return "neoperformance.chunks";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {
        if (args.length == 7) {
            World world = Bukkit.getWorld(args[2]);
            if (world == null) {
                return;
            }
            try {
                double x = Double.parseDouble(args[3]);
                double y = Double.parseDouble(args[4]);
                double z = Double.parseDouble(args[5]);
                Player player = Bukkit.getPlayer(args[6]);
                if (player == null) {
                    return;
                }
                Location location = new Location(world, x, y, z);
                player.teleport(location);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    @Override
    public List<String> tabSuggestions() {
        return null;
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
