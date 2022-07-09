package com.neomechanical.neoperformance.performanceOptimiser.managers;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class TweakData {
    //Performance tweak settings
    private @NotNull
    final Integer tpsHaltAt;//18 is the default value.
    private @NotNull Boolean notifyAdmin;//true is the default value.
    private @NotNull Integer mobCap;
    private @NotNull Boolean allowJoinWhileHalted;

    //Mailing settings
    private @NotNull Boolean useMailServer;
    private @NotNull String outgoingHost;
    private @NotNull Integer outgoingPort;
    private @NotNull String senderEmail;
    private @NotNull String senderPassword;
    private @NotNull String[] recipients;
}
