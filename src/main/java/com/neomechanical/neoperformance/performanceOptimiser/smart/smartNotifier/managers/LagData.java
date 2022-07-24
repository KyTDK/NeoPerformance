package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers;

import lombok.Data;
import net.kyori.adventure.text.TextComponent;
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
