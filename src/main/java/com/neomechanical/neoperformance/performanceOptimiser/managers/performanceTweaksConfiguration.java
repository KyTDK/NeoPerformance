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
        boolean useMailServer = false;
        String outgoingHost = "";
        int outgoingPort = 0;
        String senderEmail = "";
        String senderPassword = "";
        String[] recipients = {};
            if (config.getString(performanceSettings+"tpsHaltAt") != null) {
                tpsHaltAt = config.getInt(performanceSettings+"tpsHaltAt");
            }
            if (config.getString(performanceSettings+"notifyAdmin") != null) {
                notifyAdmin = config.getBoolean(performanceSettings+"notifyAdmin");
            }
            if (config.getString(mailSettings + "use_mail_server") != null && config.getBoolean(mailSettings + "use_mail_server")) {
                useMailServer = true;
                outgoingHost = config.getString(mailSettings + "mail_server_host");
                outgoingPort = config.getInt(mailSettings + "mail_server_port");
                senderEmail = config.getString(mailSettings + "mail_server_username");
                senderPassword = config.getString(mailSettings + "mail_server_password");
                recipients = config.getStringList(mailSettings + "recipients").toArray(new String[0]);
            }
        if (useMailServer && (outgoingHost == null || senderEmail == null || senderPassword == null || recipients.length == 0)) {
            Logger.warn("Mailing configurations not found. Mailing will be disabled.");
            return new TweakData(tpsHaltAt, notifyAdmin, false, "", outgoingPort, "", "", recipients);
        }
        return new TweakData(tpsHaltAt, notifyAdmin, useMailServer, outgoingHost, outgoingPort, senderEmail, senderPassword, recipients);
    }
}
