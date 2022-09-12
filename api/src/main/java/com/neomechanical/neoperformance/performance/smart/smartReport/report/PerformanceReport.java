package com.neomechanical.neoperformance.performance.smart.smartReport.report;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.format.NamedTextColor;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.format.TextDecoration;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.minimessage.MiniMessage;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.Grading;
import com.neomechanical.neoperformance.utils.messages.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class PerformanceReport {
    private final TextComponent.Builder textComponentBuilder;

    public PerformanceReport(PerformanceReport.PerformanceReportBuilder builder) {
        this.textComponentBuilder = builder.textComponentBuilder;
    }

    public void sendReport(CommandSender player) {
        TextComponent message = textComponentBuilder.build();
        if (message.children().size() > 0) {
            player.sendMessage(MessageUtil.color(Messages.MAIN_PERFORMANCE_REPORT_PREFIX));
            MessageUtil.sendMM(player, message);
            player.sendMessage(MessageUtil.color(Messages.MAIN_SUFFIX));
        }
    }

    public static class PerformanceReportBuilder {
        private final TextComponent.Builder textComponentBuilder = Component.text();
        private final List<IGradingSubject> gradingSubjects;
        private final NeoPerformance plugin;

        public PerformanceReportBuilder(List<IGradingSubject> gradingSubjects, NeoPerformance plugin) {
            this.gradingSubjects = gradingSubjects;
            this.plugin = plugin;
        }

        public PerformanceReportBuilder setOverallGrade() {
            //Append overall grading
            textComponentBuilder.append(Component.text("Overall grading: ")
                    .color(NamedTextColor.GRAY)
                    .append(MiniMessage.miniMessage().deserialize(Grading.getFancyGrade(Grading.getServerGrade(plugin)))).decorate(TextDecoration.UNDERLINED));
            return this;
        }

        public PerformanceReportBuilder addExtraInformation(Component information) {
            textComponentBuilder.append(Component.newline());
            textComponentBuilder.append(information);
            return this;
        }

        public PerformanceReportBuilder addIndividualGradeSection() {
            //Display individual subjects
            for (IGradingSubject gradingSubject : gradingSubjects) {
                GradeData gradeData = gradingSubject.performGrading();
                textComponentBuilder.append(Component.text(gradeData.getGradeSubject() + ": ")
                        .color(NamedTextColor.GRAY)
                        .append(MiniMessage.miniMessage().deserialize(Grading.getFancyGrade(gradeData.getGradeValue()))));
                if (gradingSubjects.indexOf(gradingSubject) != gradingSubjects.size() - 1) {
                    textComponentBuilder.append(Component.newline());
                }
            }
            return this;
        }

        public PerformanceReport build() {
            return new PerformanceReport(this);
        }
    }
}
