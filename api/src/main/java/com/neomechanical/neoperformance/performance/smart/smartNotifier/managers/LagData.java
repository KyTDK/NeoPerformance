package com.neomechanical.neoperformance.performance.smart.smartNotifier.managers;

import com.neomechanical.neoutils.kyori.adventure.text.TextComponent;
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
