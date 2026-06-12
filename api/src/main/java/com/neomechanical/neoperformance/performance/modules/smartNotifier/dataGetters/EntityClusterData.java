package com.neomechanical.neoperformance.performance.modules.smartNotifier.dataGetters;

import com.neomechanical.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.modules.smartClear.SmartScanNotifier;
import com.neomechanical.neoperformance.performance.modules.smartClear.SmartScanner;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.managers.LagData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EntityClusterData extends DataGetter {
    private final DataManager dataManager;
    public EntityClusterData(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public CompletableFuture<LagData> get(Player player) {
        CompletableFuture<LagData> future = new CompletableFuture<>();
        List<List<Entity>> clusters;
        int clusterMin = dataManager.lagNotifier().getClusterSizeNotify();
        clusters = SmartScanner.scan(10, clusterMin, dataManager, Bukkit.getWorlds().toArray(new World[0]));
        if (clusters.isEmpty() || clusters.get(0).size() < clusterMin) {
            return null;
        }
        TextComponent.Builder builder = SmartScanNotifier.getChatData(player, 1, clusters);
        if (builder.children().isEmpty()) {
            return null;
        }
        future.complete(new LagData(player, "Entity Clusters", builder));
        return future;

    }
}
