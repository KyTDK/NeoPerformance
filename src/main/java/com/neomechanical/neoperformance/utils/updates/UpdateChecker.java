package com.neomechanical.neoperformance.utils.updates;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.neomechanical.neoperformance.utils.updates.IsUpToDate.isUpToDate;

// From: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates
public class UpdateChecker implements PerformanceConfigurationSettings {
    public static Boolean UpToDate;
    private final JavaPlugin plugin;
    private final int resourceId;

    public void start() {
        if (getVisualData().getShowPluginUpdateInMain()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new UpdateChecker(NeoPerformance.getInstance(), 103183).getVersion(version -> UpToDate = isUpToDate(NeoPerformance.getInstance().getDescription().getVersion(), version));
                }
            }.runTaskTimer(NeoPerformance.getInstance(), 0, 20L * 300);
        }
    }

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }
}

