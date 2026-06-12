package com.neomechanical.neoperformance.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

/**
 * Permission checks for login-time events where a {@link org.bukkit.entity.Player}
 * instance is not available (and must not be created on the main thread).
 */
public final class OfflinePermissionUtils {
    private OfflinePermissionUtils() {
    }

    public static boolean hasPermission(UUID uuid, String permission) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (checkVaultPermission(offlinePlayer, permission)) {
            return true;
        }

        Boolean luckPermsResult = checkLuckPermsPermission(uuid, permission);
        return luckPermsResult != null && luckPermsResult;
    }

    private static boolean checkVaultPermission(OfflinePlayer offlinePlayer, String permission) {
        try {
            Class<?> permissionClass = Class.forName("net.milkbowl.vault.permission.Permission");
            RegisteredServiceProvider<?> registration =
                    Bukkit.getServer().getServicesManager().getRegistration(permissionClass);
            if (registration == null) {
                return false;
            }
            Object provider = registration.getProvider();
            return (boolean) provider.getClass()
                    .getMethod("playerHas", String.class, OfflinePlayer.class, String.class)
                    .invoke(provider, null, offlinePlayer, permission);
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private static Boolean checkLuckPermsPermission(UUID uuid, String permission) {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            return null;
        }
        try {
            Class<?> providerClass = Class.forName("net.luckperms.api.LuckPermsProvider");
            Object api = providerClass.getMethod("get").invoke(null);
            Object userManager = api.getClass().getMethod("getUserManager").invoke(api);
            Object user = userManager.getClass().getMethod("getUser", UUID.class).invoke(userManager, uuid);
            if (user == null) {
                Object userFuture = userManager.getClass().getMethod("loadUser", UUID.class).invoke(userManager, uuid);
                user = userFuture.getClass().getMethod("join").invoke(userFuture);
            }
            if (user == null) {
                return false;
            }
            Object cachedData = user.getClass().getMethod("getCachedData").invoke(user);
            Object permissionData = cachedData.getClass().getMethod("getPermissionData").invoke(cachedData);
            Object tristate = permissionData.getClass().getMethod("checkPermission", String.class).invoke(permissionData, permission);
            return (Boolean) tristate.getClass().getMethod("asBoolean").invoke(tristate);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
