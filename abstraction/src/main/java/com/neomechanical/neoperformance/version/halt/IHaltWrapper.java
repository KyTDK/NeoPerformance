package com.neomechanical.neoperformance.version.halt;

import com.neomechanical.neoconfig.neoutils.version.VersionWrapper;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;

public interface IHaltWrapper extends VersionWrapper {
    void blockPhysics(BlockPhysicsEvent e);

    void onProjectile(ProjectileHitEvent e);

    void onPlayerInteraction(PlayerInteractEvent e);

    void onServerCommand(ServerCommandEvent e);
}
