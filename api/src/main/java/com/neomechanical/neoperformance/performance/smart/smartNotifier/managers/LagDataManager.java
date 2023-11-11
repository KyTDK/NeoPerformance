package com.neomechanical.neoperformance.performance.smart.smartNotifier.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.dataGetters.ChunkData;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.dataGetters.EntityClusterData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
            futureList.add(individualFuture);
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
