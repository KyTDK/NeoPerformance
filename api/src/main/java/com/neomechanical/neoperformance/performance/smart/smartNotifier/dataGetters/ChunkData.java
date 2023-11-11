package com.neomechanical.neoperformance.performance.smart.smartNotifier.dataGetters;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performance.smart.chunks.ChunksScanner;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.managers.LagData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChunkData extends DataGetter {
    private final NeoPerformance plugin;

    public ChunkData(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public CompletableFuture<LagData> get(Player player) {
        CompletableFuture<List<Chunk>> chunksFuture = new CompletableFuture<>();

        World[] worlds = Bukkit.getWorlds().toArray(new World[0]);

        new ChunksScanner(plugin).getChunksWithMostEntities(1, chunksFuture::complete, worlds);

        return chunksFuture.thenCompose(chunks -> {
            CompletableFuture<LagData> lagDataFuture = new CompletableFuture<>();

            if (chunks.isEmpty() || chunks.get(0).getEntities().length < plugin.getDataManager().getLagNotifierData().getEntitiesInChunkNotify()) {
                lagDataFuture.completeExceptionally(new RuntimeException("No data to report"));
            } else {
                TextComponent.Builder builder = ChunksNotifier.getChatData(chunks, null, player);
                if (builder.children().isEmpty()) {
                    lagDataFuture.completeExceptionally(new RuntimeException("No data to report"));
                } else {
                    LagData lagData = new LagData(player, "Chunks", builder);
                    lagDataFuture.complete(lagData);
                }
            }

            return lagDataFuture;
        });
    }
}
