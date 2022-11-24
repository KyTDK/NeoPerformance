package com.neomechanical.neoperformance.performance.smart.smartSchedule.guis;

import com.neomechanical.neoconfig.neoutils.inventory.InventoryUtil;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryGUI;
import com.neomechanical.neoconfig.neoutils.inventory.managers.data.InventoryItem;
import com.neomechanical.neoconfig.neoutils.items.ItemUtil;
import com.neomechanical.neoperformance.performance.smart.smartSchedule.data.TimeDateData;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Collections;

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
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, 1, "Hour",
                        new ArrayList<>(Collections.singletonList("Hours: " + timeDateData.getHours()))))
                .setAction((event) -> {
                    if (timeDateData.getHours()>=12) {
                        timeDateData.setHours(1);
                        return;
                    }
                    timeDateData.setHours(timeDateData.getHours()+1);
                    inventoryGUI.update();
                })
                .build());
        inventoryGUI.setItem(5, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, 1, "Minute",
                        new ArrayList<>(Collections.singletonList("Minutes: " + timeDateData.getMinutes()))))
                .setAction((event) -> {
                    if (timeDateData.getMinutes()>=60){
                        timeDateData.setMinutes(0);
                        return;
                    }
                    timeDateData.setMinutes(timeDateData.getMinutes()+1);
                    inventoryGUI.update();
                })
                .build());
        inventoryGUI.setItem(6, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.IRON_BLOCK, 1, "Second",
                        new ArrayList<>(Collections.singletonList("Seconds: " + timeDateData.getSeconds()))))
                .setAction((event) -> {
                    if (timeDateData.getSeconds()>=60) {
                        timeDateData.setSeconds(0);
                        return;
                    }
                    timeDateData.setSeconds(timeDateData.getSeconds()+1);
                    inventoryGUI.update();
                })
                .build());
        inventoryGUI.setItem(8, new InventoryItem.InventoryItemBuilder(
                ()-> ItemUtil.createItem(Material.GLOWSTONE_DUST, 1, "AM/PM",
                            new ArrayList<>(Collections.singletonList(timeDateData.isPM() ? "PM" : "AM"))))
                .setAction((event) -> {
                    timeDateData.setPM(!timeDateData.isPM());
                    inventoryGUI.update();
                })
                .build());
        return inventoryGUI;
    }
}
