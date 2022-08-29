package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers;

import com.neomechanical.kyori.adventure.text.TextComponent;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class LagData {
    private final
    Player player;
    private final
    String dataName;
    private final
    TextComponent.Builder messageData;
}