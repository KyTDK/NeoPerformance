package com.neomechanical.neoperformance.version.halt;

import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class HaltWrapperLEGACY implements IHaltWrapper {
    @Override
    public void blockPhysics(BlockPhysicsEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onProjectile(ProjectileHitEvent e) {
        e.getEntity().remove();
    }

    @Override
    public void onPlayerInteraction(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onServerCommand(ServerCommandEvent e) {
        if (!e.getSender().getName().equals("CONSOLE")) {
            e.setCommand("");
        }
    }
}
