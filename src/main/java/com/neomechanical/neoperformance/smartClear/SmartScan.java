package com.neomechanical.neoperformance.smartClear;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.managers.data.CommandData;
import com.neomechanical.neoperformance.utils.NPC;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class SmartScan implements PerformanceConfigurationSettings {

    public static List<List<Entity>> scan(int totalChunksReturn, int clusterSize, CommandData commandData, World... worlds) {
        //Create clusters
        List<Entity> entities = new ArrayList<>();
        for (World world : worlds) {
            entities.addAll(world.getEntities());
        }
        Iterator<Entity> iterator = entities.iterator();
        Map<List<Entity>, Integer> cluster = new HashMap<>();
        List<Entity> previousClusters = new ArrayList<>();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof Player) {
                iterator.remove();
                continue;
            }
            if (entity.getCustomName() != null) {
                iterator.remove();
                continue;
            }
            if (NPC.isNpc(entity)) {
                iterator.remove();
                continue;
            }
            List<Entity> newCluster = entity.getNearbyEntities(4, 2, 4);
            if (clusterSize == 0) {
                clusterSize = commandData.getDefaultClusterSize();
            }
            if (newCluster.size() >= clusterSize) {
                boolean isNewCluster = true;
                for (Entity e : newCluster) {
                    if (previousClusters.contains(e)) {
                        isNewCluster = false;
                        break;
                    }
                }
                if (isNewCluster) {
                    cluster.put(newCluster, newCluster.size());
                    previousClusters.addAll(newCluster);
                }
            }
        }
        List<List<Entity>> topClusters = new ArrayList<>();
        for (int i = 0; i < totalChunksReturn; i++) {
            if (cluster.isEmpty()) {
                break;
            }
            int max = Collections.max(cluster.values());
            List<Entity> cluster1 = cluster.entrySet().stream()
                    .filter(entry -> entry.getValue() == max)
                    .map(Map.Entry::getKey).toList().get(0);
            cluster.remove(cluster1);
            topClusters.add(cluster1);
        }
        return topClusters;
    }
}
