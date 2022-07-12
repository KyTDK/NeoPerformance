package com.neomechanical.neoperformance.smartClear;

import org.bukkit.entity.Entity;

import java.util.List;

public interface SmartClearCallback {
    void onSmartScanDone(List<Entity> result);
}
