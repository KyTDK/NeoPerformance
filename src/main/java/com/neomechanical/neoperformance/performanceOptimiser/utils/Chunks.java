package com.neomechanical.neoperformance.performanceOptimiser.utils;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.FindOneCallback;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.*;

public class Chunks {
    public static void getChunksWithMostEntities(World world, int totalChunksReturn, final FindOneCallback callback) {
        // Run outside of the tick loop
        List<Entity> entities = world.getEntities();
        Bukkit.getScheduler().runTaskAsynchronously(NeoPerformance.getInstance(), () -> {
            //arrange entities in descending order of chunk count
            Map<Chunk, Integer> chunkCounts = new HashMap<>();
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
                        .map(Map.Entry::getKey).toList().get(0);
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

    public static void getChunksWithMostEntities(int totalChunksReturn, final FindOneCallback callback) {
        // Run outside of the tick loop
        List<Entity> entities = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            entities.addAll(world.getEntities());
        }
        Bukkit.getScheduler().runTaskAsynchronously(NeoPerformance.getInstance(), () -> {
            //arrange entities in descending order of chunk count
            Map<Chunk, Integer> chunkCounts = new HashMap<>();
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
                        .map(Map.Entry::getKey).toList().get(0);
                chunkCounts.remove(chunk);
                topChunks.add(chunk);
            }
            Bukkit.getScheduler().runTask(NeoPerformance.getInstance(), () -> {
                // call the callback with the result
                callback.onChunkScanDone(topChunks);
            });
        });
    }
}
