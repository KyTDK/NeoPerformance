package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.data.CommandData;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksScanner;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear.SmartScan;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LagNotifier {
    public static void chunkChecker(Player player) {
        List<Chunk> chunks = new ArrayList<>();
        ChunksScanner.getChunksWithMostEntities(1, chunks::addAll);
        ChunksNotifier.sendChatData(chunks, null, player);
    }

    public static void entityChecker(Player player, CommandData commandData, NeoPerformance plugin, boolean all) {
        List<World> world = new ArrayList<>();
        SmartScan.clusterLogic(0, world, commandData, plugin, player, all);
    }
}
