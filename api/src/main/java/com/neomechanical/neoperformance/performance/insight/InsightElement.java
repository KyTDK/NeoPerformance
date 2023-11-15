package com.neomechanical.neoperformance.performance.insight;

public abstract class InsightElement {
    public abstract boolean isInsightApplicableOrAlreadyPresent();

    public abstract String recommendedValue();

    public abstract String currentValue();

    public abstract void fix();
}
