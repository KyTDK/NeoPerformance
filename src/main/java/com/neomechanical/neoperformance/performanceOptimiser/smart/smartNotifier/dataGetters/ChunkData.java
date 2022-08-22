package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.dataGetters;

import com.neomechanical.kyori.adventure.text.TextComponent;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksScanner;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers.LagData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChunkData extends DataGetter {
    private static List<Chunk> chunks = new ArrayList<>();
    private final NeoPerformance plugin;

    public ChunkData(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    @Override
    public void generate() {
        World[] worlds = Bukkit.getWorlds().toArray(new World[0]);
        new ChunksScanner(plugin).getChunksWithMostEntities(1, result -> chunks = result, worlds);
    }

    @Override
    public LagData get(Player player) {
        if (chunks.isEmpty() || chunks.get(0).getEntities().length < plugin.getDataManager().getLagNotifierData().getEntitiesInChunkNotify()) {
            return null;
        }
        TextComponent.Builder builder = ChunksNotifier.getChatData(chunks, null, player);
        if (builder.children().isEmpty()) {
            return null;
        }
        return new LagData(player, "Chunks", builder);
    }

    @Override
    public Integer getNotifySize() {
        return plugin.getDataManager().getLagNotifierData().getEntitiesInChunkNotify();
    }
}
