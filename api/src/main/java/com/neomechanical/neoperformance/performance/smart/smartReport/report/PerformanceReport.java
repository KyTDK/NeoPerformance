package com.neomechanical.neoperformance.performance.smart.smartReport.report;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.TextComponent;
import com.neomechanical.neoconfig.neoutils.messages.MessageUtil;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradeData;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.Grading;
import com.neomechanical.neoperformance.utils.messages.Messages;
import org.bukkit.entity.Player;

import java.util.List;

public class PerformanceReport {
    private final TextComponent.Builder textComponentBuilder;

    public PerformanceReport(PerformanceReport.PerformanceReportBuilder builder) {
        this.textComponentBuilder = builder.textComponentBuilder;
    }

    public void sendReport(Player player) {
        TextComponent message = textComponentBuilder.build();
        if (message.children().size() > 0) {
            MessageUtil.send(player, Messages.MAIN_PERFORMANCE_REPORT_PREFIX);
            MessageUtil.sendMM(player, message);
            MessageUtil.send(player, Messages.MAIN_SUFFIX);
        }
    }

    public static class PerformanceReportBuilder {
        private final TextComponent.Builder textComponentBuilder = Component.text();

        public PerformanceReportBuilder addGrades(List<IGradingSubject> gradingSubjects) {
            int gradeValues = 0;
            //Set overall grading
            for (IGradingSubject gradingSubject : gradingSubjects) {
                gradeValues += gradingSubject.performGrading().getGradeValue();
            }
            textComponentBuilder.append(Component.text("Overall grading: " + Grading.getFancyGrade(gradeValues / gradingSubjects.size()))).append(Component.newline());
            for (IGradingSubject gradingSubject : gradingSubjects) {
                GradeData gradeData = gradingSubject.performGrading();
                textComponentBuilder.append(Component.text(gradeData.getGradeSubject() + ": " + Grading.getFancyGrade(gradeData.getGradeValue())).append(Component.newline()));
            }
            return this;
        }

        public PerformanceReport build() {
            return new PerformanceReport(this);
        }
    }
}
