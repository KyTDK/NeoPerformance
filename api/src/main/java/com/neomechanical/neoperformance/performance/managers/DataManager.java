package com.neomechanical.neoperformance.performance.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.config.PerformanceConfig;
import com.neomechanical.neoperformance.config.PerformanceTweaksConfiguration;
import com.neomechanical.neoperformance.integrations.HookIntegrations;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataManager {
    private final HookIntegrations hookIntegrations;
    private final List<CommandSender> bypassedPlayers = new ArrayList<>();
    private boolean manualHalt = false;
    private PerformanceConfig performanceConfig;

    public DataManager() {
        hookIntegrations = new HookIntegrations();
    }

    public void loadTweakSettings(NeoPerformance plugin) {
        new PerformanceTweaksConfiguration(plugin).loadTweakSettings(this);
    }

    public void toggleManualHalt() {
        //toggle boolean manualHalt
        manualHalt = !manualHalt;
    }

    public boolean toggleBypass(CommandSender player) {
        //toggle boolean manualHalt
        if (bypassedPlayers.contains(player)) {
            bypassedPlayers.remove(player);
            return false;
        } else {
            bypassedPlayers.add(player);
            return true;
        }
    }

    public void setConfig(PerformanceConfig performanceConfig) {
        this.performanceConfig = performanceConfig;
    }
    public boolean isBypassed(Player player) {
        return bypassedPlayers.contains(player) || player.hasPermission("neoperformance.bypass.auto");
    }
}
