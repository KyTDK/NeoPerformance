package com.neomechanical.neoperformance.performanceOptimiser.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.performanceTweaksConfiguration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TweakDataManager {
    private boolean manualHalt = false;
    private final List<CommandSender> bypassedPlayers = new ArrayList<>();
    private TweakData tweakData;

    public TweakDataManager() {
    }

    public void loadTweakSettings() {
        this.tweakData = new performanceTweaksConfiguration().loadTweakSettings();
    }

    public TweakData getTweakData() {
        return this.tweakData;
    }

    public boolean isManualHalt() {
        return manualHalt;
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

    public boolean isBypassed(Player player) {
        return bypassedPlayers.contains(player);
    }
}
