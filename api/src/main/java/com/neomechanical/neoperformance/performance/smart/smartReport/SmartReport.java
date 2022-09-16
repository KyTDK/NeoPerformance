package com.neomechanical.neoperformance.performance.smart.smartReport;

import com.neomechanical.neoconfig.neoutils.NeoUtils;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.event.ClickEvent;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.event.HoverEvent;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.minimessage.MiniMessage;
import com.neomechanical.neoconfig.neoutils.languages.LanguageManager;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradingSubjectsManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.report.PerformanceReport;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.SparkUtils;

import java.util.List;

public class SmartReport {
    private final NeoPerformance plugin;

    public SmartReport(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public PerformanceReport getPerformanceReportOverview() {
        //Generate data
        GradingSubjectsManager gradingSubjectsManager = new GradingSubjectsManager(plugin);
        List<IGradingSubject> gradingSubjects = gradingSubjectsManager.getAllGrades();
        LanguageManager languageManager = NeoUtils.getManagers().getLanguageManager();
        Component serverGrade = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.serverGrade", null));
        Component usedMemory = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.usedMemory", null));
        Component freeMemory = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.freeMemory", null));
        Component maxMemory = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.maxMemory", null));
        Component availableProcessors = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.availableProcessors", null));
        Component cpuJvmLoad = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.cpuJvmLoad", null));
        Component cpuSystemLoad = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.cpuSystemLoad", null));
        Component mstpMinuteAverage = MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.mstpAverage", null));
        Component notice = Component.empty()
                .append(MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.notice", null)))
                .clickEvent(ClickEvent.runCommand("/np report subjects"))
                .hoverEvent(HoverEvent.showText(MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.noticeHover", null))));
        //Build report
        PerformanceReport.PerformanceReportBuilder performanceReport = new PerformanceReport.PerformanceReportBuilder(gradingSubjects)
                .addHeader(serverGrade)
                .addExtraInformation(MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.memoryTitle", null)))
                .addExtraInformation(usedMemory)
                .addExtraInformation(freeMemory)
                .addExtraInformation(maxMemory)
                .addExtraInformation(MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.cpuTitle", null)))
                .addExtraInformation(availableProcessors)
                .addExtraInformation(cpuJvmLoad)
                .addExtraInformation(cpuSystemLoad);
        SparkUtils.runIfEnabled(() -> performanceReport
                .addExtraInformation(MiniMessage.miniMessage().deserialize(languageManager.getString("smartReport.otherTitle", null)))
                .addExtraInformation(mstpMinuteAverage));
        performanceReport.addExtraInformation(notice);
        return performanceReport.build();
    }

    public PerformanceReport getPerformanceReportSubjects() {
        GradingSubjectsManager gradingSubjectsManager = new GradingSubjectsManager(plugin);
        List<IGradingSubject> gradingSubjects = gradingSubjectsManager.getAllGrades();
        return new PerformanceReport.PerformanceReportBuilder(gradingSubjects)
                .addIndividualGradeSection()
                .build();
    }
}
