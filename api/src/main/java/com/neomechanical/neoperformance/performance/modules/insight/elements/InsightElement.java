package com.neomechanical.neoperformance.performance.modules.insight.elements;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import org.bukkit.entity.Player;

public abstract class InsightElement<T> {
    public T recommendedValue;
    public boolean canFix = true;
    public boolean canEditValue = true;
    public boolean sendDoneMessage = true;
    public abstract boolean isInsightApplicableOrAlreadyPresent();

    public InsightElement() {
        setDefaultValue();
    }

    public String getRecommendedValue() {
        return String.valueOf(recommendedValue);
    }

    public void setRecommendedValue(String recommendedValue) {
        try {
            if (this.recommendedValue instanceof String) {
                this.recommendedValue = (T) recommendedValue;
            } else if (this.recommendedValue instanceof Integer) {
                this.recommendedValue = (T) Integer.valueOf(recommendedValue);
            } else if (this.recommendedValue instanceof Boolean) {
                this.recommendedValue = (T) Boolean.valueOf(recommendedValue);
            } else if (this.recommendedValue instanceof Double) {
                this.recommendedValue = (T) Double.valueOf(recommendedValue);
            } else {
                NeoUtils.getNeoUtilities().getFancyLogger().warn("Failed to set value");
            }
        } catch (Exception e) {
            NeoUtils.getNeoUtilities().getFancyLogger().warn("Failed to convert values: " + e.getMessage());
        }
    }

    public abstract void setDefaultValue();

    public abstract T currentValue();

    protected abstract void fixInternally(Player player);

    public void fix(Player player) {
        fixInternally(player);
    }
}
