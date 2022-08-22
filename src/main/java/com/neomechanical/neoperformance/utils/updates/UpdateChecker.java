package com.neomechanical.neoperformance.utils.updates;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performanceOptimiser.managers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.neomechanical.neoutils.updates.IsUpToDate.isUpToDate;

// From: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates
public class UpdateChecker {
    public static Boolean UpToDate;
    private final NeoPerformance plugin;
    private final int resourceId;
    private final DataManager dataManager;

    public UpdateChecker(NeoPerformance plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
        this.dataManager = plugin.getDataManager();
    }

    public void start() {
        if (dataManager.getVisualData().getShowPluginUpdateInMain()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new UpdateChecker(plugin, 103183).getVersion(version -> UpToDate = isUpToDate(plugin.getDescription().getVersion(), version));
                }
            }.runTaskTimer(plugin, 0, 20L * 300);
        }
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

