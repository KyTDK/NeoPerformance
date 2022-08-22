package com.neomechanical.neoperformance.performanceOptimiser.smart.chunks;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.FindOneCallback;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class ChunksScanner {
    private final NeoPerformance plugin;

    public ChunksScanner(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public void getChunksWithMostEntities(int totalChunksReturn, final FindOneCallback callback, World... worlds) {
        // Run outside the tick loop
        List<Entity> entities = new ArrayList<>();

        for (World world : worlds) {
            entities.addAll(world.getEntities());
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            //arrange entities in descending order of chunk count
            LinkedHashMap<Chunk, Integer> chunkCounts = new LinkedHashMap<>();
            for (Entity entity : entities) {
                Chunk chunk = entity.getLocation().getChunk();
                if (chunkCounts.containsKey(chunk)) {
                    chunkCounts.put(chunk, chunkCounts.get(chunk) + 1);
                } else {
                    chunkCounts.put(chunk, 1);
                }
            }
            List<Chunk> topChunks = new ArrayList<>();
            for (int i = 0; i < totalChunksReturn; i++) {
                if (chunkCounts.isEmpty()) {
                    break;
                }
                int max = Collections.max(chunkCounts.values());
                Chunk chunk = chunkCounts.entrySet().stream()
                        .filter(entry -> entry.getValue() == max)
                        .map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
                chunkCounts.remove(chunk);
                topChunks.add(chunk);
            }
            // go back to the tick loop
            Bukkit.getScheduler().runTask(NeoPerformance.getInstance(), () -> {
                // call the callback with the result
                callback.onChunkScanDone(topChunks);
            });
        });
    }
}
