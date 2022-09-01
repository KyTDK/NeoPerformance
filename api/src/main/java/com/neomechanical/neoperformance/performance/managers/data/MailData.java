package com.neomechanical.neoperformance.performance.managers.data;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class MailData {
    //Mailing settings
    private @NotNull Boolean useMailServer;
    private @NotNull String outgoingHost;
    private @NotNull Integer outgoingPort;
    private @NotNull String senderEmail;
    private @NotNull String senderPassword;
    private @NotNull String[] recipients;
}
