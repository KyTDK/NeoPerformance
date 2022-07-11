package com.neomechanical.neoperformance.performanceOptimiser.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceTweaksConfiguration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private boolean manualHalt = false;
    private final List<CommandSender> bypassedPlayers = new ArrayList<>();
    private TweakData tweakData;
    private HaltData haltData;
    private VisualData visualData;
    private MailData mailData;

    public DataManager() {
    }

    public void loadTweakSettings() {
        new PerformanceTweaksConfiguration().loadTweakSettings(this);
    }

    public TweakData getTweakData() {
        return this.tweakData;
    }

    public TweakData setTweakData(TweakData tweakData) {
        this.tweakData = tweakData;
        return this.tweakData;
    }

    public HaltData getHaltData() {
        return this.haltData;
    }

    public HaltData setHaltData(HaltData haltData) {
        this.haltData = haltData;
        return this.haltData;
    }

    public MailData getMailData() {
        return this.mailData;
    }

    public MailData setMailData(MailData mailData) {
        this.mailData = mailData;
        return this.mailData;
    }

    public VisualData getVisualData() {
        return this.visualData;
    }

    public VisualData setVisualData(VisualData visualData) {
        this.visualData = visualData;
        return this.visualData;
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
