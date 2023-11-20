package com.neomechanical.neoperformance.performance.modules.smartNotifier;

import com.neomechanical.neoperformance.performance.modules.smartNotifier.managers.LagData;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public abstract class DataGetter {
    public abstract CompletableFuture<LagData> get(Player player);
}
