package com.neomechanical.neoperformance.performance.modules.smartSchedule.data;

import lombok.Data;


@Data
public class TimeDateData {
    //Time
    private int seconds = 0;
    private int minutes = 0;
    private int hours;
    //Date
    private int day;
    private int month;
    private int year;
    private boolean isPM;
}
