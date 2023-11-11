package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class HaltSettings {
    private boolean allowJoinWhileHalted;
    private boolean haltTeleportation;
    private double maxSpeed;
    private boolean haltExplosions;
    private boolean haltRedstone;
    private boolean haltMobSpawning;
    private boolean haltInventoryMovement;
    private boolean haltCommandBlock;
    private boolean haltItemDrops;
    private boolean haltBlockBreaking;
    private boolean haltPlayerInteractions;
    private boolean haltProjectiles;
    private boolean haltEntityInteractions;
    private boolean haltEntityTargeting;
    private boolean haltVehicleCollisions;
    private boolean haltBlockPhysics;
    private int haltTimeout;
    public HaltSettings(FileConfiguration fileConfiguration) {
        this.allowJoinWhileHalted = fileConfiguration.getBoolean("halt_settings.allowJoinWhileHalted");
        haltTeleportation = fileConfiguration.getBoolean("halt_settings.haltTeleportation");
        this.maxSpeed = fileConfiguration.getDouble("halt_settings.maxSpeed");
        this.haltExplosions = fileConfiguration.getBoolean("halt_settings.haltExplosions");
        this.haltRedstone = fileConfiguration.getBoolean("halt_settings.haltRedstone");
        this.haltMobSpawning = fileConfiguration.getBoolean("halt_settings.haltMobSpawning");
        this.haltInventoryMovement = fileConfiguration.getBoolean("halt_settings.haltInventoryMovement");
        this.haltCommandBlock = fileConfiguration.getBoolean("halt_settings.haltCommandBlock");
        this.haltItemDrops = fileConfiguration.getBoolean("halt_settings.haltItemDrops");
        this.haltBlockBreaking = fileConfiguration.getBoolean("halt_settings.haltBlockBreaking");
        this.haltPlayerInteractions = fileConfiguration.getBoolean("halt_settings.haltPlayerInteractions");
        this.haltProjectiles = fileConfiguration.getBoolean("halt_settings.haltProjectiles");
        this.haltEntityInteractions = fileConfiguration.getBoolean("halt_settings.haltEntityInteractions");
        this.haltEntityTargeting = fileConfiguration.getBoolean("halt_settings.haltEntityTargeting");
        this.haltVehicleCollisions = fileConfiguration.getBoolean("halt_settings.haltVehicleCollisions");
        this.haltBlockPhysics = fileConfiguration.getBoolean("halt_settings.haltBlockPhysics");
        this.haltTimeout = fileConfiguration.getInt("halt_settings.haltTimeout");
    }
}
