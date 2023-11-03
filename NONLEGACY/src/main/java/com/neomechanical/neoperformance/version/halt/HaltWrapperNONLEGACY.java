package com.neomechanical.neoperformance.version.halt;

import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HaltWrapperNONLEGACY implements IHaltWrapper {
    @Override
    public void blockPhysics(BlockPhysicsEvent e) {
        BlockData blockData = e.getBlock().getBlockData();
        if (blockData instanceof AnaloguePowerable || blockData instanceof Powerable) {
            return;
        }
        e.setCancelled(true);
    }

    @Override
    public void onProjectile(ProjectileHitEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onPlayerInteraction(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;
        e.setCancelled(true);
    }

    @Override
    public void onServerCommand(ServerCommandEvent e) {
        if (!e.getSender().getName().equals("CONSOLE")) {
            e.setCancelled(true);
        }
    }
}
