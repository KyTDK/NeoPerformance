package com.neomechanical.neoperformance.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Data
public class EmailNotifications {
    private FileConfiguration fileConfiguration;
    private boolean useMailServer;
    private String mailServerHost;
    private int mailServerPort;
    private String mailServerUsername;
    private String mailServerPassword;
    private List<String> recipients;

    public EmailNotifications(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;

        // Initialize fields inside the constructor
        this.useMailServer = fileConfiguration.getBoolean("email_notifications.use_mail_server");
        this.mailServerHost = fileConfiguration.getString("email_notifications.mail_server_host");
        this.mailServerPort = fileConfiguration.getInt("email_notifications.mail_server_port");
        this.mailServerUsername = fileConfiguration.getString("email_notifications.mail_server_username");
        this.mailServerPassword = fileConfiguration.getString("email_notifications.mail_server_password");
        this.recipients = fileConfiguration.getStringList("email_notifications.recipients");
    }
}