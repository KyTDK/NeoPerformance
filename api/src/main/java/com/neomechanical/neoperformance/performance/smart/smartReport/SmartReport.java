package com.neomechanical.neoperformance.performance.smart.smartReport;

import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.Component;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.format.NamedTextColor;
import com.neomechanical.neoconfig.neoutils.kyori.adventure.text.format.TextDecoration;
import com.neomechanical.neoconfig.neoutils.server.resources.DataSize;
import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartReport.grading.GradingSubjectsManager;
import com.neomechanical.neoperformance.performance.smart.smartReport.gradingSubjects.IGradingSubject;
import com.neomechanical.neoperformance.performance.smart.smartReport.report.PerformanceReport;
import com.neomechanical.neoperformance.performance.smart.smartReport.utils.CPU;

import java.text.DecimalFormat;
import java.util.List;

public class SmartReport {
    private final NeoPerformance plugin;

    public SmartReport(NeoPerformance plugin) {
        this.plugin = plugin;
    }

    public PerformanceReport getPerformanceReport() {
        //Generate data
        GradingSubjectsManager gradingSubjectsManager = new GradingSubjectsManager(plugin);
        List<IGradingSubject> gradingSubjects = gradingSubjectsManager.getAllGrades();
        Runtime runtime = Runtime.getRuntime();
        Component usedMemory = Component.text("Used memory: ")
                .color(NamedTextColor.GRAY)
                .append(Component.text((DataSize.ofBytes(runtime.totalMemory() - runtime.freeMemory()).toMegabytes()) + " MB").decorate(TextDecoration.BOLD));
        Component freeMemory = Component.text("Free memory: ")
                .color(NamedTextColor.GRAY)
                .append(Component.text(DataSize.ofBytes(runtime.freeMemory()).toMegabytes() + " MB").decorate(TextDecoration.BOLD));
        Component maxMemory = Component.text("Max memory: ")
                .color(NamedTextColor.GRAY)
                .append(Component.text(DataSize.ofBytes(runtime.maxMemory()).toGigabytes() + " GB").decorate(TextDecoration.BOLD));
        Component availableProcessors = Component.text("Available processors: ")
                .color(NamedTextColor.GRAY)
                .append(Component.text(runtime.availableProcessors()).decorate(TextDecoration.BOLD));
        Component cpuJvmLoad = Component.text("JVM CPU load: ")
                .color(NamedTextColor.GRAY)
                .append(Component.text(new DecimalFormat("###.##").format(CPU.getProcessCpuLoad() * 100) + "%").decorate(TextDecoration.BOLD));
        Component cpuSystemLoad = Component.text("System CPU load: ")
                .color(NamedTextColor.GRAY)
                .append(Component.text(new DecimalFormat("###.##").format(CPU.getSystemCpuLoad() * 100) + "%").decorate(TextDecoration.BOLD));
        return new PerformanceReport.PerformanceReportBuilder(gradingSubjects)
                .setOverallGrade()
                .addExtraInformation(Component.text("[Memory]"))
                .addExtraInformation(usedMemory)
                .addExtraInformation(freeMemory)
                .addExtraInformation(maxMemory)
                .addExtraInformation(Component.text("[CPU]"))
                .addExtraInformation(availableProcessors)
                .addExtraInformation(cpuJvmLoad)
                .addExtraInformation(cpuSystemLoad)
                .addExtraInformation(Component.text("[Grading subjects]").append(Component.newline()))
                .addIndividualGradeSection()
                .build();
    }
}
