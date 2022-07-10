package com.neomechanical.neoperformance.utils.updates;

public class IsUpToDate {
    public static boolean isUpToDate(String currentVersion, String latestVersion) {
        String[] currentVersionSplit = currentVersion.split("\\.");
        String[] latestVersionSplit = latestVersion.split("\\.");
        for (int i = 0; i < currentVersionSplit.length; i++) {
            if (Integer.parseInt(currentVersionSplit[i]) < Integer.parseInt(latestVersionSplit[i])) {
                return false;
            }
        }
        return true;
    }
}
