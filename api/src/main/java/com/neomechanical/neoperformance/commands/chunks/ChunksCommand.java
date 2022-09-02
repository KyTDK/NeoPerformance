package com.neomechanical.neoperformance.commands.chunks;

import com.neomechanical.neoconfig.neoutils.commands.Command;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performance.smart.chunks.ChunksScanner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class ChunksCommand extends Command {
    private final NeoPerformance plugin;
    @Override
    public String getName() {
        return "chunks";
    }

    @Override
    public String getDescription() {
        return "Get the chunks with the most entities, good for finding lag";
    }

    @Override
    public String getSyntax() {
        return "/np chunks <world>";
    }

    @Override
    public String getPermission() {
        return "neoperformance.chunks";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    public ChunksCommand(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public void perform(CommandSender player, String[] args) {
        World world = null;
        if (args.length == 2) {
            world = Bukkit.getWorld(args[1]);
            if (world == null) {
                MessageUtil.sendMM(player, getLanguageManager().getString("commandGeneric.errorWorldNotFound", null));
                return;
            }
        } else {
            //Command to replace execute for clickable text
            ChunkTeleport.teleportToChunk(args);
        }
        if (world == null) {
            World[] worlds = Bukkit.getWorlds().toArray(new World[0]);
            new ChunksScanner(plugin).getChunksWithMostEntities(10, result -> ChunksNotifier.sendChatData(result, null, player), worlds);
        } else {
            World finalWorld = world;
            new ChunksScanner(plugin).getChunksWithMostEntities(10, result -> ChunksNotifier.sendChatData(result, finalWorld, player), world);
        }
    }


    @Override
    public List<String> tabSuggestions() {
        List<String> worlds = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worlds.add(world.getName());
        }
        return worlds;
    }

    @Override
    public Map<String, List<String>> mapSuggestions() {
        return null;
    }
}
