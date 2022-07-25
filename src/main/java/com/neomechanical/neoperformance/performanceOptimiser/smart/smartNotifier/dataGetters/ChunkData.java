package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.dataGetters;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksNotifier;
import com.neomechanical.neoperformance.performanceOptimiser.smart.chunks.ChunksScanner;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers.LagData;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChunkData extends DataGetter implements PerformanceConfigurationSettings {
    private static List<Chunk> chunks = new ArrayList<>();

    @Override
    public void generate() {
        World[] worlds = Bukkit.getWorlds().toArray(World[]::new);
        ChunksScanner.getChunksWithMostEntities(1, result -> chunks = result, worlds);
    }

    @Override
    public LagData get(Player player) {
        if (chunks.isEmpty() || chunks.get(0).getEntities().length < getLagNotifierData().getEntitiesInChunkNotify()) {
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
        return getLagNotifierData().getEntitiesInChunkNotify();
    }
}
