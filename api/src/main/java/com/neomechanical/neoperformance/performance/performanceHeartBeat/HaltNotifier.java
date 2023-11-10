package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.ServerMetrics;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoconfig.neoutils.server.ServerInfo;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.utils.mail.EmailClient;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltNotifier {
    protected static void notifyHalted(DataManager dataManager) {
        ServerInfo serverInfo = new ServerInfo();
        long uptimeSeconds = serverInfo.getUptime(ServerMetrics.TimeDataType.SECONDS);

        // Don't do any notifications for the first 20 seconds of startup to avoid false positives.
        if (uptimeSeconds <= 20) {
            return;
        }

        if (dataManager.getMailData().getUseMailServer()) {
            sendEmailNotification(dataManager);
        }

        String message = getLanguageManager().getString("notify.serverHalted", null);

        //Log to console
        NeoUtils.getNeoUtilities().getFancyLogger().fancyLog(message, true);

        if (dataManager.getTweakData().getBroadcastHalt()) {
            MessageUtil.sendMMAll(message);
        } else if (dataManager.getTweakData().getNotifyAdmin()) {
            MessageUtil.sendMMAdmins(message);
        }
    }

    protected static void notifyResumed(DataManager dataManager) {
        if (dataManager.getMailData().getUseMailServer()) {
            sendEmailNotification(dataManager);
        }

        String message = getLanguageManager().getString("notify.serverResumed", null);

        //Log to console
        NeoUtils.getNeoUtilities().getFancyLogger().fancyLog(message, true);

        if (dataManager.getTweakData().getBroadcastHalt()) {
            MessageUtil.sendMMAll(message);
        } else if (dataManager.getTweakData().getNotifyAdmin()) {
            MessageUtil.sendMMAdmins(message);
        }
    }

    private static void sendEmailNotification(DataManager dataManager) {
        EmailClient emailClient = new EmailClient(dataManager);
        // Is run asynchronously
        emailClient.sendAsHtml(
                getLanguageManager().getString("email_notifications.subject_resumed", null),
                getLanguageManager().getString("email_notifications.body_resumed", null)
        );
    }

}
