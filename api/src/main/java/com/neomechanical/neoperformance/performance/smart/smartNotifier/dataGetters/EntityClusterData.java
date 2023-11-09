package com.neomechanical.neoperformance.performance.smart.smartNotifier.dataGetters;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.smart.smartClear.SmartScanNotifier;
import com.neomechanical.neoperformance.performance.smart.smartClear.SmartScanner;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.managers.LagData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EntityClusterData extends DataGetter {
    private final DataManager dataManager;
    private List<List<Entity>> clusters = new ArrayList<>();

    public EntityClusterData(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void generate() {
        clusters = SmartScanner.scan(10, dataManager.getLagNotifierData().getClusterSizeNotify(),
                dataManager.getCommandData(), Bukkit.getWorlds().toArray(new World[0]));
    }

    @Override
    public LagData get(Player player) {
        if (clusters.isEmpty() || clusters.get(0).size() < dataManager.getLagNotifierData().getClusterSizeNotify()) {
            return null;
        }
        TextComponent.Builder builder = SmartScanNotifier.getChatData(player, 1, clusters);
        if (builder.children().isEmpty()) {
            return null;
        }
        return new LagData(player, "Entity Clusters", builder);
    }

    @Override
    public Integer getNotifySize() {
        return dataManager.getLagNotifierData().getClusterSizeNotify();
    }
}
