package com.neomechanical.neoperformance.performance.insight;

import com.neomechanical.neoperformance.performance.insight.elements.bukkit.yml.*;
import com.neomechanical.neoperformance.performance.insight.elements.server.properties.NetworkCompressionThreshold;
import com.neomechanical.neoperformance.performance.insight.elements.server.properties.RenderDistance;
import com.neomechanical.neoperformance.performance.insight.elements.server.properties.Snooper;
import com.neomechanical.neoperformance.performance.insight.elements.spigot.yml.*;

import java.util.HashMap;

public class InsightManager {
    private final HashMap<String, HashMap<String, InsightElement<?>>> categories = new HashMap<>();

    public InsightManager() {
        //Server.properties
        addInsight("server.properties", "view-distance", new RenderDistance());
        addInsight("server.properties", "snooper-enabled", new Snooper());
        addInsight("server.properties", "network-compression-threshold", new NetworkCompressionThreshold());
        //Spigot.yml
        addInsight("spigot.yml", "merge-radius-exp", new MergeRadiusExp());
        addInsight("spigot.yml", "Merge-radius-item", new MergeRadiusItem());
        addInsight("spigot.yml", "item-respawn-rate", new ItemDespawnRate());
        addInsight("spigot.yml", "entity-activation-range-animals", new EntityActivationRangeAnimals());
        addInsight("spigot.yml", "entity-activation-range-monsters", new EntityActivationRangeMonsters());
        addInsight("spigot.yml", "entity-activation-range-misc", new EntityActivationRangeMisc());
        addInsight("spigot.yml", "ticks-per-hopper-check", new TicksPerHopperCheck());
        addInsight("spigot.yml", "ticks-per-hopper-transfer", new TicksPerHopperTransfer());
        addInsight("spigot.yml", "max-tnt-per-tick", new MaxTntPerTick());
        addInsight("spigot.yml", "max-tick-time-entity", new MaxTickTimeEntity());
        addInsight("spigot.yml", "max-tick-time-tile", new MaxTickTimeTile());
        //Bukkit.yml
        addInsight("bukkit.yml", "spawn-limits-monsters", new SpawnLimitsMonsters());
        addInsight("bukkit.yml", "spawn-limits-animals", new SpawnLimitsAnimals());
        addInsight("bukkit.yml", "spawn-limits-water-animals", new SpawnLimitsWaterAnimals());
        addInsight("bukkit.yml", "spawn-limits-ambient", new SpawnLimitsAmbient());
        addInsight("bukkit.yml", "chunk-gc-period-in-ticks", new ChunkGCPeriodInTicks());
        addInsight("bukkit.yml", "chunk-gc-period-load-threshold", new ChunkGCPeriodLoadThreshold());
        addInsight("bukkit.yml", "ticks-per-animal-spawns", new TicksPerAnimalSpawns());
        addInsight("bukkit.yml", "ticks-per-animal-monster-spawns", new TicksPerMonsterSpawns());
        addInsight("bukkit.yml", "ticks-per-animal-autosave", new TicksPerAutosave());
    }

    public void addInsight(String category, String insightName, InsightElement<?> insightElement) {
        if (insightElement.isInsightApplicableOrAlreadyPresent()) {
            categories.computeIfAbsent(category, k -> new HashMap<>()).put(insightName, insightElement);
        }
    }

    public InsightElement getInsight(String category, String insight) {
        return categories.getOrDefault(category, new HashMap<>()).get(insight);
    }

    public HashMap<String, HashMap<String, InsightElement<?>>> getInsightsMap() {
        return categories;
    }
}