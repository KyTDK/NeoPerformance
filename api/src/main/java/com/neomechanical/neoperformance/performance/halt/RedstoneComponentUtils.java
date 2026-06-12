package com.neomechanical.neoperformance.performance.halt;

import org.bukkit.Material;
import org.bukkit.block.Block;

public final class RedstoneComponentUtils {
    private RedstoneComponentUtils() {
    }

    public static boolean isRedstoneComponent(Block block) {
        if (block == null || block.isEmpty()) {
            return false;
        }
        return isRedstoneMaterial(block.getType());
    }

    public static boolean isRedstoneMaterial(Material material) {
        if (material == null || material == Material.AIR) {
            return false;
        }
        String materialName = material.name();
        return materialName.contains("REDSTONE")
                || materialName.contains("REPEATER")
                || materialName.contains("COMPARATOR")
                || materialName.contains("OBSERVER")
                || materialName.contains("PISTON")
                || materialName.contains("PRESSURE_PLATE")
                || materialName.contains("BUTTON")
                || materialName.contains("LEVER")
                || materialName.contains("TRIPWIRE")
                || materialName.contains("DAYLIGHT_DETECTOR")
                || materialName.contains("SCULK_SENSOR")
                || materialName.contains("TARGET")
                || materialName.contains("DOOR")
                || materialName.contains("TRAPDOOR")
                || materialName.contains("RAIL")
                || materialName.contains("NOTE_BLOCK")
                || materialName.contains("LECTERN");
    }
}
