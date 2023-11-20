package com.neomechanical.neoperformance.performance.modules.smartNotifier.report;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.performance.modules.smartNotifier.managers.LagData;
import com.neomechanical.neoperformance.utils.messages.Messages;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LagReportBuilder {
    CompletableFuture<TextComponent.Builder> builderCompletableFuture = new CompletableFuture<>();

    public void addData(CompletableFuture<List<LagData>> dataListFuture) {
        builderCompletableFuture = dataListFuture.thenApply(dataList -> {
            TextComponent.Builder builder = Component.text();
            int dataSize = dataList.size();

            for (int i = 0; i < dataSize; i++) {
                LagData data = dataList.get(i);

                // Null means there is no data to report
                if (data == null) {
                    return builder; // Return original builder unchanged
                }

                TextComponent.Builder dataComponent = data.getMessageData();
                builder.append(Component.text("  ")).append(Component.text("[")).append(Component.text(data.getDataName()))
                        .append(Component.text("]"))
                        .append(Component.newline());

                builder.append(dataComponent);

                // Add newline only if it's not the last iteration
                if (i < dataSize - 1) {
                    builder.append(Component.newline());
                }
            }

            return builder;
        });
    }


    public void sendReport(Player player) {
        builderCompletableFuture.thenApply(builder -> {
            TextComponent message = builder.build();
            if (!message.children().isEmpty()) {
                MessageUtil.send(player, Messages.MAIN_LAG_REPORT_PREFIX);
                MessageUtil.sendMM(player, message);
                MessageUtil.send(player, Messages.MAIN_SUFFIX);
            }
            return null;
        });
    }
}
