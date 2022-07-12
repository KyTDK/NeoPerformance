package com.neomechanical.neoperformance.performanceOptimiser.managers;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HaltData {
    //Halt settings
    private @NotNull Boolean allowJoinWhileHalted;
    private @NotNull Integer maxSpeed;
    private @NotNull Boolean haltTeleportation;
    private @NotNull Boolean haltExplosions;
    private @NotNull Boolean haltRedstone;
    private @NotNull Boolean haltChunkLoading;
    private @NotNull Boolean haltMobSpawning;
    private @NotNull Boolean haltInventoryMovement;
    private @NotNull Boolean haltCommandBlock;
    private @NotNull Boolean haltItemDrops;
    private @NotNull Boolean haltBlockBreaking;
    private @NotNull Boolean haltProjectiles;
    private @NotNull Boolean haltEntityBreeding;
    private @NotNull Boolean haltEntityInteractions;
    private @NotNull Boolean haltEntityTargeting;
    private @NotNull Boolean haltVehicleCollisions;
    private @NotNull Boolean haltBlockPhysics;
    private @NotNull Integer haltTimeout;
}
