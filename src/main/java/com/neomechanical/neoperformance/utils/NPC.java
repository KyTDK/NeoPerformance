package com.neomechanical.neoperformance.utils;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class NPC {
    public static boolean isNpc(Entity b) {
        List<MetadataValue> metaDataValues = b.getMetadata("NPC");
        for (MetadataValue value : metaDataValues) {
            return value.asBoolean();
        }
        return false;
    }
}
