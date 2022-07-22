package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers;

import lombok.Data;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Data
public class LagData {
    private final @NotNull
    Player player;
    private final @NotNull
    String dataName;
    private final @NotNull
    TextComponent.Builder messageData;
}
