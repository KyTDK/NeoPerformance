package com.neomechanical.neoperformance.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.utils.Tps;
import com.neomechanical.neoutils.languages.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RegisterLanguageManager implements PerformanceConfigurationSettings, Tps {
    public void register(JavaPlugin plugin) {
        new LanguageManager(plugin)
                .setLanguageCode(() -> getVisualData().getLanguage())
                .setLanguageFile("de-DE.yml", "en-US.yml", "es-ES.yml", "tr-TR.yml", "fr-FR.yml", "ru-RU.yml", "zh-CN.yml")
                .addInternalPlaceholder("%TPS%", (Player player) -> getFancyTps())
                .addInternalPlaceholder("%TPSHALT%", (Player player) -> getFancyHaltTps())
                .addInternalPlaceholder("%SERVERHALTED%", (Player player) -> fancyIsServerHalted())
                .addInternalPlaceholder("%PLAYERCOUNT%", (Player player) -> String.valueOf(Bukkit.getOnlinePlayers().size()))
                .addInternalPlaceholder("%PLAYER%", (Player player) -> player == null ? "None" : player.getName())
                .addInternalPlaceholder("%UPDATESTATUS%", (Player player) -> getFancyUpdateStatus())
                .set();
    }
}
