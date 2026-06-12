package com.neomechanical.neoperformance.performance.managers;

import com.neomechanical.neoutils.config.ConfigManager;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.config.ChatModerationSettings;
import com.neomechanical.neoperformance.config.Commands;
import com.neomechanical.neoperformance.config.EmailNotifications;
import com.neomechanical.neoperformance.config.HaltSettings;
import com.neomechanical.neoperformance.config.LagNotifier;
import com.neomechanical.neoperformance.config.PerformanceConfig;
import com.neomechanical.neoperformance.config.PerformanceTweakSettings;
import com.neomechanical.neoperformance.config.Visual;
import com.neomechanical.neoperformance.integrations.HookIntegrations;
import com.neomechanical.neoperformance.performance.utils.PerformanceConfigurationSettingsUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataManager {
    private final HookIntegrations hookIntegrations;
    private final List<CommandSender> bypassedPlayers = new ArrayList<>();
    private boolean manualHalt = false;
    @Getter(AccessLevel.NONE)
    private PerformanceConfig performanceConfig;

    public DataManager() {
        hookIntegrations = new HookIntegrations();
    }

    /**
     * Loads {@link PerformanceConfig} from disk and replaces the in-memory configuration.
     * Single entry point for configuration I/O (previously split across a helper class).
     */
    public void loadTweakSettings(NeoPerformance plugin) {
        setConfig(new PerformanceConfig(new ConfigManager(plugin, "performanceConfig.yml").getConfig()));
    }

    private PerformanceConfig requireConfig() {
        if (performanceConfig == null) {
            throw new IllegalStateException("Performance configuration has not been loaded yet.");
        }
        return performanceConfig;
    }

    /** Full configuration graph (same slices as {@link #haltSettings()} etc., for legacy APIs). */
    public PerformanceConfig getPerformanceConfig() {
        return requireConfig();
    }

    /** Halt-related toggles (TPS gate, redstone, joins, etc.). */
    public HaltSettings haltSettings() {
        return requireConfig().getHaltSettings();
    }

    /** Core TPS / caps / heartbeat tuning. */
    public PerformanceTweakSettings tweakSettings() {
        return requireConfig().getPerformanceTweakSettings();
    }

    /** Smart notify / lag reporter thresholds and intervals. */
    public LagNotifier lagNotifier() {
        return requireConfig().getLagNotifier();
    }

    public Visual visual() {
        return requireConfig().getVisual();
    }

    public EmailNotifications emailNotifications() {
        return requireConfig().getEmailNotifications();
    }

    public Commands commands() {
        return requireConfig().getCommands();
    }

    public ChatModerationSettings chatModeration() {
        return requireConfig().getChatModerationSettings();
    }

    public List<String> haltActionNames() {
        return requireConfig().getHaltActions();
    }

    /** Explosion allowed under configured nearby-entity cap (lag prevention). */
    public boolean allowsExplosion(EntityExplodeEvent event) {
        return PerformanceConfigurationSettingsUtils.canExplode(this, event);
    }

    /** Mob/item spawn allowed under configured density cap (lag prevention). */
    public boolean allowsMobSpawn(EntitySpawnEvent event) {
        return PerformanceConfigurationSettingsUtils.canMobSpawn(this, event);
    }

    /** Player movement allowed under halt max-speed when server is under load. */
    public boolean allowsPlayerMoveSpeed(double instantaneousSpeed) {
        return PerformanceConfigurationSettingsUtils.canMove(this, instantaneousSpeed);
    }

    public void toggleManualHalt() {
        manualHalt = !manualHalt;
    }

    public boolean toggleBypass(CommandSender player) {
        if (bypassedPlayers.contains(player)) {
            bypassedPlayers.remove(player);
            return false;
        }
        bypassedPlayers.add(player);
        return true;
    }

    public void setConfig(PerformanceConfig performanceConfig) {
        this.performanceConfig = performanceConfig;
    }

    public boolean isBypassed(Player player) {
        return bypassedPlayers.contains(player) || player.hasPermission("neoperformance.bypass.auto");
    }
}
