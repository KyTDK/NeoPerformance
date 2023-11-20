package com.neomechanical.neoperformance.performance.modules.insight.elements.software;

import com.neomechanical.neoperformance.performance.modules.insight.elements.InsightElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerType extends InsightElement<String> {
    @Override
    public boolean isInsightApplicableOrAlreadyPresent() {
        return !currentValue().equalsIgnoreCase(recommendedValue);
    }

    @Override
    public void setDefaultValue() {
        recommendedValue = "Paper";
        canFix = false;
        canEditValue = false;
    }

    @Override
    public String currentValue() {
        String version = Bukkit.getVersion();
        // Define the pattern to match the substring between two hyphens
        Pattern pattern = Pattern.compile("-(.*?)-");

        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(version);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract the substring between the two hyphens
            return matcher.group(1);
        } else {
            return "Unknown";
        }
    }

    @Override
    protected void fixInternally(Player player) {
    }

}
