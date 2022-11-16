package com.neomechanical.neoperformance.performance.smart.smartSchedule.guis;

import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import com.neomechanical.neoperformance.performance.smart.smartSchedule.data.TimeDateData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.time.LocalDateTime;

public class TimeDateSchedule {
    private InventoryGUI inventoryGUI;
    public InventoryGUI getInventory() {
        TimeDateData timeDateData = new TimeDateData();
        inventoryGUI = InventoryUtil.createInventoryGUI(null, 27, "Date/Time");
        inventoryGUI.setItem(0, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.ANVIL, "Custom Time"))
                .setAction((event) -> {})
                .build());
        inventoryGUI.setItem(4, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, "Hour"))
                .setAction((event) -> timeDateData.setHours(scroll(event)))
                .build());
        inventoryGUI.setItem(5, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, "Minute"))
                .setAction((event) -> timeDateData.setHours(scroll(event)))
                .build());
        inventoryGUI.setItem(6, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, "Second"))
                .setAction((event) -> timeDateData.setHours(scroll(event)))
                .build());
        inventoryGUI.setItem(8, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.GLOWSTONE_DUST, "AM"))
                .setAction((event) -> timeDateData.setHours(scroll(event)))
                .build());
        return inventoryGUI;
    }
    private int scroll(InventoryClickEvent event) {
        int currentValue = 0;

        inventoryGUI.update();
        return currentValue;
    }
}
