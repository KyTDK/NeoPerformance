package com.neomechanical.neoperformance.performanceOptimiser.smart.smartClear;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.managers.data.CommandData;
import com.neomechanical.neoperformance.utils.NPC;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class SmartScan implements PerformanceConfigurationSettings {

    public static List<List<Entity>> scan(int totalClustersReturn, int clusterSize, CommandData commandData, World... worlds) {
        //Create clusters
        List<Entity> entities = new ArrayList<>();
        for (World world : worlds) {
            entities.addAll(world.getEntities());
        }
        Iterator<Entity> iterator = entities.iterator();
        Map<List<Entity>, Integer> cluster = new HashMap<>();
        List<Entity> previousClusters = new ArrayList<>();
        List<Entity> toRemove = new ArrayList<>();

        if (clusterSize == 0) {
            clusterSize = commandData.getDefaultClusterSize();
        }

        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            List<Entity> newCluster = entity.getNearbyEntities(4, 2, 4);
            newCluster.add(entity);
            if (newCluster.size() >= clusterSize) {
                boolean isNewCluster = true;
                //Deal with cluster, checks, etc.
                for (Entity e : newCluster) {
                    if (previousClusters.contains(e)) {
                        isNewCluster = false;
                        break;
                    }
                    if (e instanceof Player) {
                        toRemove.add(e);
                        continue;
                    }
                    if (e.getCustomName() != null) {
                        toRemove.add(e);
                        continue;
                    }
                    if (NPC.isNpc(e)) {
                        toRemove.add(e);
                    }
                    for (String s : commandData.getSmartClearExcludeEntities()) {
                        if (e.getType().name().replaceAll("_", " ").equalsIgnoreCase(s.replaceAll("_", " "))) {
                            toRemove.add(e);
                            break;
                        }
                    }
                }
                //Remove entities that are not part of the cluster
                for (Entity e : toRemove) {
                    newCluster.remove(e);
                }
                //Add cluster to map
                if (isNewCluster && newCluster.size() >= clusterSize) {
                    cluster.put(newCluster, newCluster.size());
                    previousClusters.addAll(newCluster);
                    //Reset booleans, arrays, etc. for next cluster
                    toRemove.clear();
                }
            }
        }
        //Sort clusters by size
        List<List<Entity>> topClusters = new ArrayList<>();
        for (int i = 0; i < totalClustersReturn; i++) {
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
