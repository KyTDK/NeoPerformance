package com.neomechanical.neoperformance.performanceOptimiser.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceTweaksConfiguration;
import com.neomechanical.neoperformance.performanceOptimiser.managers.data.*;
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
    private CommandData commandData;
    private LagNotifierData lagNotifierData;
    private boolean restoringRedstone = false;

    public DataManager() {
    }

    public void loadTweakSettings() {
        new PerformanceTweaksConfiguration().loadTweakSettings(this);
    }

    //Tweak data
    public TweakData getTweakData() {
        return this.tweakData;
    }

    public void setTweakData(TweakData tweakData) {
        this.tweakData = tweakData;
    }

    //Halt Data
    public HaltData getHaltData() {
        return this.haltData;
    }

    public void setHaltData(HaltData haltData) {
        this.haltData = haltData;
    }

    //Mail data
    public MailData getMailData() {
        return this.mailData;
    }

    public void setMailData(MailData mailData) {
        this.mailData = mailData;
    }

    //Visual data
    public VisualData getVisualData() {
        return this.visualData;
    }

    public void setVisualData(VisualData visualData) {
        this.visualData = visualData;
    }

    //Lag Notifier data
    public LagNotifierData getLagNotifierData() {
        return this.lagNotifierData;
    }

    public void setLagNotifierData(LagNotifierData lagNotifierData) {
        this.lagNotifierData = lagNotifierData;
    }

    //Commands
    public CommandData getCommandData() {
        return this.commandData;
    }

    public void setCommandData(CommandData commandData) {
        this.commandData = commandData;
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

    public boolean isRestoringRedstone() {
        return restoringRedstone;
    }

    public void setRestoringRedstone(boolean restoringRedstone) {
        this.restoringRedstone = restoringRedstone;
    }

    public boolean isBypassed(Player player) {
        return bypassedPlayers.contains(player);
    }
}
