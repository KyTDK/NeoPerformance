package com.neomechanical.neoperformance.performanceOptimiser.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.*;

public class Chunks {
    public static List<Chunk> getChunksWithMostEntities(World world, int totalChunksReturn) {
        List<Entity> entities = world.getEntities();
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
        return topChunks;
    }

    public static List<Chunk> getChunksWithMostEntities(int totalChunksReturn) {
        List<Entity> entities = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            entities.addAll(world.getEntities());
        }
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
        return topChunks;
    }
}
