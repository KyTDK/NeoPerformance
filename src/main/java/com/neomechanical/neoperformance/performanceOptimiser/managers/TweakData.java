package com.neomechanical.neoperformance.performanceOptimiser.managers;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class TweakData {
    //Performance tweak settings
    private @NotNull
    final Integer tpsHaltAt;//18 is the default value.
    private @NotNull Boolean notifyAdmin;//true is the default value.
    private @NotNull Boolean broadcastHalt;
    private @NotNull Integer mobCap;

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
    private @NotNull Boolean haltProjectiles;
    private @NotNull Boolean haltEntityBreeding;
    private @NotNull Boolean haltEntityInteractions;
    private @NotNull Boolean haltEntityTargeting;
    private @NotNull Boolean haltVehicleCollisions;
    private @NotNull Boolean haltBlockPhysics;

    //Mailing settings
    private @NotNull Boolean useMailServer;
    private @NotNull String outgoingHost;
    private @NotNull Integer outgoingPort;
    private @NotNull String senderEmail;
    private @NotNull String senderPassword;
    private @NotNull String[] recipients;
}
