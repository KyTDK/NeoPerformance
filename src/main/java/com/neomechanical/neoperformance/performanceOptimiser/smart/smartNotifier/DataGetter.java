package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers.LagData;
import org.bukkit.entity.Player;

public abstract class DataGetter {
    public abstract void generate();

    public abstract LagData get(Player player);

    public abstract Integer getNotifySize();
}
