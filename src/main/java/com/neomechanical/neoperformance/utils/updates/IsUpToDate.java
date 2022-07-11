package com.neomechanical.neoperformance.utils.updates;
import java.lang.module.ModuleDescriptor.Version;

public class IsUpToDate {
    public static boolean isUpToDate(String currentVersion, String latestVersion) {
        Version current = Version.parse(currentVersion);
        Version latest = Version.parse(latestVersion);
        return current.compareTo(latest) >= 0;
    }
}
