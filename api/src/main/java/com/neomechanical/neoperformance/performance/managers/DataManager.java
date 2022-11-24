package com.neomechanical.neoperformance.performance.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.config.PerformanceTweaksConfiguration;
import com.neomechanical.neoperformance.performance.managers.data.*;
import com.neomechanical.neoperformance.performance.smart.smartReport.relatedManagers.SparkData;
import org.bukkit.Bukkit;
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
    private HaltActionData haltActionData;
    private final SparkData sparkData;
    public DataManager() {
        sparkData = new SparkData(Bukkit.getPluginManager().getPlugin("spark") != null);
    }

    public void loadTweakSettings(NeoPerformance plugin) {
        new PerformanceTweaksConfiguration(plugin).loadTweakSettings(this);
    }

    public SparkData getSparkData() {
        return sparkData;
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

    //Lag Notifier data
    public HaltActionData getHaltActionData() {
        return this.haltActionData;
    }

    public void setHaltActionData(HaltActionData haltActionData) {
        this.haltActionData = haltActionData;
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
        return bypassedPlayers.contains(player) || player.hasPermission("neoperformance.bypass.auto");
    }
}
