package com.alexeym.atmayoga.common;

import java.time.LocalDateTime;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class TimeTypeResolver {

    public static TimeType getCurrentTimeType() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        if (hour < 7) return TimeType.NIGHT;
        else if (hour < 10) return TimeType.MORNING;
        else if (hour < 18) return TimeType.DAY;
        else return TimeType.EVENING;
    }

    public enum TimeType {
        MORNING, DAY, EVENING, NIGHT
    }

}
