package com.neomechanical.neoperformance.performance.modules.smartNotifier.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.dataGetters.ChunkData;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.dataGetters.EntityClusterData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LagDataManager {
    List<DataGetter> dataGetters = new ArrayList<>();

    public LagDataManager(NeoPerformance plugin) {
        dataGetters.add(new ChunkData(plugin));
        dataGetters.add(new EntityClusterData(plugin.getDataManager()));
    }

    public CompletableFuture<List<LagData>> getAllLagData(Player player) {
        // Create a list to store individual CompletableFuture instances
        List<CompletableFuture<LagData>> futureList = new ArrayList<>();

        // Iterate through the dataGetters and add each CompletableFuture to the list
        for (DataGetter dataGetter : dataGetters) {
            CompletableFuture<LagData> individualFuture = dataGetter.get(player);
            if (individualFuture != null) {
                futureList.add(individualFuture);
            }
        }

        // Check if the list is empty
        if (futureList.isEmpty()) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        // Use CompletableFuture.allOf to combine all individual CompletableFutures into a single CompletableFuture<Void>
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[0])
        );

        // Use thenApply to get the results of all individual CompletableFutures
        return allOfFuture.thenApply(
                v -> futureList.stream()
                        .map(CompletableFuture::join) // Get the result of each CompletableFuture
                        .collect(Collectors.toList())
        );
    }


}
