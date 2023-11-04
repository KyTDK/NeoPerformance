package com.neomechanical.neoperformance.performance.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.config.PerformanceTweaksConfiguration;
import com.neomechanical.neoperformance.performance.managers.data.*;
import com.neomechanical.neoperformance.performance.smart.smartReport.relatedManagers.SparkData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    @Getter
    private final SparkData sparkData;
    private final List<CommandSender> bypassedPlayers = new ArrayList<>();
    @Getter
    private boolean manualHalt = false;
    //Tweak data
    @Getter
    private TweakData tweakData;
    //Halt Data
    @Getter
    private HaltData haltData;
    //Visual data
    @Getter
    private VisualData visualData;
    //Mail data
    @Getter
    private MailData mailData;
    //Commands
    @Getter
    private CommandData commandData;
    //Lag Notifier data
    @Getter
    private LagNotifierData lagNotifierData;
    //Lag Notifier data
    @Getter
    private HaltActionData haltActionData;
    public DataManager() {
        sparkData = new SparkData(Bukkit.getPluginManager().getPlugin("spark") != null);
    }

    public void loadTweakSettings(NeoPerformance plugin) {
        new PerformanceTweaksConfiguration(plugin).loadTweakSettings(this);
    }

    public void setTweakData(TweakData tweakData) {
        this.tweakData = tweakData;
    }

    public void setHaltData(HaltData haltData) {
        this.haltData = haltData;
    }

    public void setMailData(MailData mailData) {
        this.mailData = mailData;
    }

    public void setVisualData(VisualData visualData) {
        this.visualData = visualData;
    }

    public void setLagNotifierData(LagNotifierData lagNotifierData) {
        this.lagNotifierData = lagNotifierData;
    }

    public void setHaltActionData(HaltActionData haltActionData) {
        this.haltActionData = haltActionData;
    }

    public void setCommandData(CommandData commandData) {
        this.commandData = commandData;
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
