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

    public DataManager() {
    }

    public void loadTweakSettings() {
        new PerformanceTweaksConfiguration().loadTweakSettings(this);
    }

    //Tweak data
    public TweakData getTweakData() {
        return this.tweakData;
    }

    public TweakData setTweakData(TweakData tweakData) {
        this.tweakData = tweakData;
        return this.tweakData;
    }

    //Halt Data
    public HaltData getHaltData() {
        return this.haltData;
    }

    public HaltData setHaltData(HaltData haltData) {
        this.haltData = haltData;
        return this.haltData;
    }

    //Mail data
    public MailData getMailData() {
        return this.mailData;
    }

    public MailData setMailData(MailData mailData) {
        this.mailData = mailData;
        return this.mailData;
    }

    //Visual data
    public VisualData getVisualData() {
        return this.visualData;
    }

    public VisualData setVisualData(VisualData visualData) {
        this.visualData = visualData;
        return this.visualData;
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

    public boolean isBypassed(Player player) {
        return bypassedPlayers.contains(player);
    }
}
