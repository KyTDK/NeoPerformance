package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class LagReport {
    static final TextComponent.Builder builder = Component.text();

    private static void sendReport() {
        TextComponent message = builder.build();
    }

    public LagReport reportBuilder(TextComponent.Builder builder) {
        return this;
    }

    private LagReport addData(TextComponent.Builder dataComponent, String dataName) {
        dataComponent.append(Component.text(dataName));
        builder.append(dataComponent);
        return this;
    }
}
