package com.neomechanical.neoperformance.performanceOptimiser.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigCore;
import com.neomechanical.neoperformance.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;

public class performanceTweaksConfiguration {
    FileConfiguration config = PerformanceConfigCore.config;
    String performanceSettings = "performance_tweak_settings.";
    String mailSettings = "email_notifications.";
    public TweakData loadTweakSettings() {
        int tpsHaltAt = 0;
        boolean notifyAdmin = false;
        int mobCap = 0;
        boolean allowJoinWhileHalted = false;
        int maxSpeed = 0;
        boolean useMailServer = false;
        String outgoingHost = "";
        int outgoingPort = 0;
        String senderEmail = "";
        String senderPassword = "";
        String[] recipients = {};
        if (config.contains(performanceSettings + "tpsHaltAt")) {
            tpsHaltAt = config.getInt(performanceSettings + "tpsHaltAt");
        }
        if (config.contains(performanceSettings + "notifyAdmin")) {
            notifyAdmin = config.getBoolean(performanceSettings + "notifyAdmin");
        }
        if (config.contains(performanceSettings + "mobCap")) {
            mobCap = config.getInt(performanceSettings + "mobCap");
        }
        if (config.contains(performanceSettings + "allowJoinWhileHalted")) {
            allowJoinWhileHalted = config.getBoolean(performanceSettings + "allowJoinWhileHalted");
        }
        if (config.contains(performanceSettings + "maxSpeed")) {
            maxSpeed = config.getInt(performanceSettings + "maxSpeed");
        }
        if (config.contains(mailSettings + "use_mail_server") && config.contains(mailSettings + "use_mail_server")) {
            useMailServer = true;
            outgoingHost = config.getString(mailSettings + "mail_server_host");
            outgoingPort = config.getInt(mailSettings + "mail_server_port");
            senderEmail = config.getString(mailSettings + "mail_server_username");
            senderPassword = config.getString(mailSettings + "mail_server_password");
            recipients = config.getStringList(mailSettings + "recipients").toArray(new String[0]);
        }
        if (useMailServer && (outgoingHost == null || senderEmail == null || senderPassword == null || recipients.length == 0)) {
            Logger.warn("Mailing configurations not found. Mailing will be disabled.");
            return new TweakData(tpsHaltAt, notifyAdmin, mobCap, allowJoinWhileHalted, maxSpeed, false, "", outgoingPort, "", "", recipients);
        }
        return new TweakData(tpsHaltAt, notifyAdmin, mobCap, allowJoinWhileHalted, maxSpeed, useMailServer, outgoingHost, outgoingPort, senderEmail, senderPassword, recipients);
    }
}
