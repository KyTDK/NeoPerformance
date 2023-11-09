package com.neomechanical.neoperformance.performance.smart.smartClear;

import com.neomechanical.neoperformance.performance.managers.data.CommandData;
import com.neomechanical.neoperformance.utils.NPC;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class SmartScanner {
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

        if (clusterSize < 1) {
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
        for (int i = 0; i < (totalClustersReturn < 0 ? cluster.size() : totalClustersReturn); i++) {
            if (cluster.isEmpty()) {
                break;
            }
            int max = Collections.max(cluster.values());
            List<Entity> cluster1 = cluster.entrySet().stream()
                    .filter(entry -> entry.getValue() == max)
                    .map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
            cluster.remove(cluster1);
            topClusters.add(cluster1);
        }
        return topClusters;
    }

    public static SmartScan scan(int clusterSize, List<World> world, CommandData commandData, CommandSender player, boolean all) {
        //Message logic and construct entity list

        //If clusterSize is less than 1, use default size
        List<List<Entity>> clusters;
        if (world.isEmpty()) {
            //Scan for all world
            World[] worlds = Bukkit.getWorlds().toArray(new World[0]);
            clusters = SmartScanner.scan(10, clusterSize, commandData, worlds);

        } else {
            World[] worldsList = world.toArray(new World[0]);
            //Scan for individual world
            clusters = SmartScanner.scan(10, clusterSize, commandData, worldsList);
        }
        //One removes the largest cluster only
        int toClear = 1;
        if (all) {
            toClear = clusters.size();
        }

        return new SmartScan(clusters, toClear);
    }
}
