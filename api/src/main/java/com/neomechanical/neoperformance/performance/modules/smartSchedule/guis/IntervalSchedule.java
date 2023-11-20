package com.neomechanical.neoperformance.performance.modules.smartSchedule.guis;

import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import org.bukkit.Material;

public class IntervalSchedule {
    public InventoryGUI getInventory() {
        InventoryGUI inventoryGUI = InventoryUtil.createInventoryGUI(null, 27, "Interval");
        inventoryGUI.setItem(0, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.ANVIL, "Custom Time"))
                .build());
        inventoryGUI.setItem(5, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.EMERALD_BLOCK, "Second"))
                .build());
        inventoryGUI.setItem(6, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.EMERALD_BLOCK, "Minute"))
                .build());
        inventoryGUI.setItem(7, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.EMERALD_BLOCK, "Hour"))
                .build());
        inventoryGUI.setItem(8, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.EMERALD_BLOCK, "Day"))
                .build());
        return inventoryGUI;
    }
}
