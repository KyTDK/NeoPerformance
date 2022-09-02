package com.neomechanical.neoperformance.managers;

import com.neomechanical.neoconfig.neoutils.languages.LanguageManager;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import com.neomechanical.neoperformance.performance.utils.TpsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RegisterLanguageManager {
    private final NeoPerformance plugin;
    private final DataManager dataManager;

    public RegisterLanguageManager(NeoPerformance plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();
    }

    public void register() {
        new LanguageManager(plugin)
                .setLanguageCode(() -> dataManager.getVisualData().getLanguage())
                .setLanguageFile("de-DE.yml", "en-US.yml", "es-ES.yml", "tr-TR.yml", "fr-FR.yml", "ru-RU.yml", "zh-CN.yml")
                .addInternalPlaceholder("%TPS%", (Player player) -> TpsUtils.getFancyTps(plugin))
                .addInternalPlaceholder("%TPSHALT%", (Player player) -> TpsUtils.getFancyHaltTps(plugin))
                .addInternalPlaceholder("%SERVERHALTED%", (Player player) -> TpsUtils.fancyIsServerHalted(TpsUtils.getTPS(plugin), plugin))
                .addInternalPlaceholder("%PLAYERCOUNT%", (Player player) -> String.valueOf(Bukkit.getOnlinePlayers().size()))
                .addInternalPlaceholder("%PLAYER%", (Player player) -> player == null ? "None" : player.getName())
                .addInternalPlaceholder("%UPDATESTATUS%", (Player player) -> TpsUtils.getFancyUpdateStatus())
                .set();
    }
}
