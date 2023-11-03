package com.neomechanical.neoperformance.performance.smart.smartNotifier;

import com.neomechanical.neoperformance.performance.smart.smartNotifier.managers.LagData;
import org.bukkit.entity.Player;

public abstract class DataGetter {
    public abstract void generate();

    public abstract LagData get(Player player);

    public abstract Integer getNotifySize();
}
