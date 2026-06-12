package com.neomechanical.neoperformance.performance.performanceHeartBeat;

import com.neomechanical.neoutils.NeoUtils;
import com.neomechanical.neoutils.ServerMetrics;
import com.neomechanical.neoutils.messages.MessageUtil;
import com.neomechanical.neoutils.server.ServerInfo;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.integrations.discorsrv.DiscordSRVHook;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.utils.mail.EmailClient;

import static com.neomechanical.neoperformance.NeoPerformance.getLanguageManager;

public class HaltNotifier {

    protected static void notifyHalted(NeoPerformance plugin) {
        long uptimeSeconds = new ServerInfo().getUptime(ServerMetrics.TimeDataType.SECONDS);

        if (uptimeSeconds > 20) {
            notify(plugin, "notify.serverHalted", "discord.serverHalted");
        }
    }

    protected static void notifyResumed(NeoPerformance plugin) {
        notify(plugin, "notify.serverResumed", "discord.serverResumed");
    }

    private static void notify(NeoPerformance plugin, String notifyKey, String discordKey) {
        DataManager dataManager = plugin.getDataManager();
        if (dataManager.emailNotifications().isUseMailServer()) {
            sendEmailNotification(plugin, dataManager);
        }

        String message = getLanguageManager().getString(notifyKey, null);

        NeoUtils.getNeoUtilities().getFancyLogger().fancyLog(message, true);

        DiscordSRVHook discordSRVHook = (DiscordSRVHook) dataManager.getHookIntegrations().getIntegration("DiscordSRV");
        if (discordSRVHook != null && dataManager.tweakSettings().isNotifyDiscord()) {
            discordSRVHook.sendMessage(getLanguageManager().getString(discordKey, null));
        }

        if (dataManager.tweakSettings().isBroadcastHalt()) {
            MessageUtil.sendMMAll(message);
        } else if (dataManager.tweakSettings().isNotifyAdmin()) {
            MessageUtil.sendMMAdmins(message);
        }
    }

    private static void sendEmailNotification(NeoPerformance plugin, DataManager dataManager) {
        EmailClient emailClient = new EmailClient(plugin, dataManager);
        emailClient.sendAsHtml(
                getLanguageManager().getString("email_notifications.subject_resumed", null),
                getLanguageManager().getString("email_notifications.body_resumed", null)
        );
    }
}
