package com.neomechanical.neoperformance.performance.insight;

import com.neomechanical.neoperformance.performance.insight.elements.RenderDistance;
import com.neomechanical.neoperformance.performance.insight.elements.Snooper;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class InsightManager {
    private final HashMap<String, HashMap<String, InsightElement>> categories = new HashMap<>();

    public InsightManager() {
        addInsight("Server-Properties", "View-Distance", new RenderDistance());
        addInsight("Server-Properties", "Snooper-Enabled", new Snooper());
    }

    public void addInsight(String category, String insightName, InsightElement insightElement) {
        categories.computeIfAbsent(category, k -> new HashMap<>()).put(insightName, insightElement);
    }

    public HashMap<String, InsightElement> getInsightsMap(String category) {
        return categories.getOrDefault(category, new HashMap<>());
    }

    public InsightElement getInsight(String category, String insight) {
        return categories.getOrDefault(category, new HashMap<>()).get(insight);
    }

    public HashMap<String, HashMap<String, InsightElement>> getInsightsMap() {
        return categories;
    }
}