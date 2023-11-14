package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.ServerMetrics;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoconfig.neoutils.server.ServerInfo;
import com.neomechanical.neoperformance.integrations.discorsrv.DiscordSRVHook;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.utils.mail.EmailClient;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltNotifier {

    protected static void notifyHalted(DataManager dataManager) {
        long uptimeSeconds = new ServerInfo().getUptime(ServerMetrics.TimeDataType.SECONDS);

        if (uptimeSeconds > 20) {
            notify(dataManager, "notify.serverHalted", "discord.serverHalted");
        }
    }

    protected static void notifyResumed(DataManager dataManager) {
        notify(dataManager, "notify.serverResumed", "discord.serverResumed");
    }

    private static void notify(DataManager dataManager, String notifyKey, String discordKey) {
        if (dataManager.getPerformanceConfig().getEmailNotifications().isUseMailServer()) {
            sendEmailNotification(dataManager);
        }

        String message = getLanguageManager().getString(notifyKey, null);

        // Log to console
        NeoUtils.getNeoUtilities().getFancyLogger().fancyLog(message, true);

        // Send DiscordSRV message
        DiscordSRVHook discordSRVHook = (DiscordSRVHook) dataManager.getHookIntegrations().getIntegration("DiscordSRV");
        if (discordSRVHook != null) {
            discordSRVHook.sendMessage(getLanguageManager().getString(discordKey, null));
        }

        // Broadcast or notify admins
        if (dataManager.getPerformanceConfig().getPerformanceTweakSettings().isBroadcastHalt()) {
            MessageUtil.sendMMAll(message);
        } else if (dataManager.getPerformanceConfig().getPerformanceTweakSettings().isNotifyAdmin()) {
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
