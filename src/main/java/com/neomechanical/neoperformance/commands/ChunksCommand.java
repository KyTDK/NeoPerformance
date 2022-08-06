package com.neomechanical.neoperformance.commands;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksScanner;
import com.neomechanical.neoutils.commandManager.SubCommand;
import com.neomechanical.neoutils.messages.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChunksCommand extends SubCommand {
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
        return true;
    }

    private final NeoPerformance plugin = NeoPerformance.getInstance();
    private final MessageUtil messageUtil = new MessageUtil(NeoPerformance.adventure());

    @Override
    public void perform(CommandSender player, String[] args) {
        Player playerAsPlayer = (Player) player;
        World world = null;
        if (args.length == 2) {
            world = Bukkit.getWorld(args[1]);
            if (world == null) {
                messageUtil.sendMM(player, plugin.getLanguageManager().getString("commandGeneric.errorWorldNotFound", null));
                return;
            }
        }
        if (world == null) {
            World[] worlds = Bukkit.getWorlds().toArray(World[]::new);
            ChunksScanner.getChunksWithMostEntities(10, result -> ChunksNotifier.sendChatData(result, null, playerAsPlayer), worlds);
        } else {
            World finalWorld = world;
            ChunksScanner.getChunksWithMostEntities(10, result -> ChunksNotifier.sendChatData(result, finalWorld, playerAsPlayer), world);
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
