package com.neomechanical.neoperformance.performance.smart.smartSchedule;

import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TimeDateSchedule {
    public InventoryGUI getInventory() {
        InventoryGUI inventoryGUI = InventoryUtil.createInventoryGUI(null, 27, "Date/Time");
        inventoryGUI.setItem(0, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.ANVIL, "Custom Time"))
                .build());
        inventoryGUI.setItem(4, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, "Hour"))
                .build());
        inventoryGUI.setItem(5, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, "Minute"))
                .build());
        inventoryGUI.setItem(6, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, "Second"))
                .build());
        inventoryGUI.setItem(8, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.GLOWSTONE_DUST, "AM"))
                .build());
        return inventoryGUI;
    }
}
