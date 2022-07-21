package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.performanceOptimiser.managers.data.CommandData;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksScanner;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear.SmartScan;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear.SmartScanNotifier;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LagNotifier {
    public static TextComponent.Builder getChunkData(Player player) {
        List<Chunk> chunks = new ArrayList<>();
        ChunksScanner.getChunksWithMostEntities(1, chunks::addAll);
        return ChunksNotifier.getChatData(chunks, null, player);
    }

    public static TextComponent.Builder getClusterData(CommandData commandData, int clusterSize, Player player) {
        //Message logic and construct entity list
        List<List<Entity>> clusters;
        //Scan for all world
        World[] worlds = Bukkit.getWorlds().toArray(World[]::new);
        clusters = SmartScan.scan(10, clusterSize, commandData, worlds);
        if (clusters.isEmpty() && worlds.length == 0) {
            //Nothing to report
            return null;
        }
        return SmartScanNotifier.getChatData(player, 1, clusters);
    }
}
