package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.utils.mail.EmailClient;
import com.neomechanical.neoutils.ServerMetrics;
import com.neomechanical.neoutils.messages.MessageUtil;
import com.neomechanical.neoutils.server.ServerInfo;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltNotifier {
    protected static void notify(DataManager dataManager) {
        ServerInfo serverInfo = new ServerInfo();
        //Don't do any notifications for the first 20 seconds of startup to avoid false positives.
        if (serverInfo.getUptime(ServerMetrics.TimeDataType.SECONDS) <= 20) {
            return;
        }
        if (dataManager.getMailData().getUseMailServer()) {
            EmailClient emailClient = new EmailClient(dataManager);
            //Is run asynchronously
            emailClient.sendAsHtml(getLanguageManager().getString("email_notifications.subject", null),
                    getLanguageManager().getString("email_notifications.body", null));
        }
        String message = getLanguageManager().getString("notify.serverHalted", null);
        if (dataManager.getTweakData().getBroadcastHalt()) {
            MessageUtil.sendMMAll(message);
        } else if (dataManager.getTweakData().getNotifyAdmin()) {
            MessageUtil.sendMMAdmins(message);
        }
    }
}
