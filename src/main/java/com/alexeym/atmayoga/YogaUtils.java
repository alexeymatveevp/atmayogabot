package com.alexeym.atmayoga;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public class YogaUtils {

    public static <T> T getRandomItem(List<T> list) {
        int size = list.size();
        int index = (int) (Math.random() * size);
        return list.get(index);
    }

    public static double rollTheDice() {
        return Math.random() * 100;
    }

    /**
     * Makes delay 10 work as 7, 8, 12 or 15.
     * @param delay
     * @return
     */
    public static int getDelayNaturalDeviation(int delay) {
        if (Math.random() > 0.5) {
            return (int) (delay - Math.random() * delay / 2);
        } else {
            return (int) (delay + Math.random() * delay / 2);
        }
    }

    public static String getPrevMonthAsRusString() {
        LocalDate now = LocalDate.now().minusMonths(1);
        Month month = now.getMonth();
        return getRusMonthName(month);
    }

    public static String getCurrentMonthAsRusString() {
        LocalDate now = LocalDate.now();
        Month month = now.getMonth();
        return getRusMonthName(month);
    }

    public static String getRusMonthName(Month month) {
        switch (month) {
            case JANUARY: return "Январь";
            case FEBRUARY: return "Февраль";
            case MARCH: return "Март";
            case APRIL: return "Апрель";
            case MAY: return "Май";
            case JUNE: return "Июнь";
            case JULY: return "Июль";
            case AUGUST: return "Август";
            case SEPTEMBER: return "Сентябрь";
            case OCTOBER: return "Октябрь";
            case NOVEMBER: return "Ноябрь";
            case DECEMBER: return "Декабрь";
        }
        return null;
    }

    public static boolean thisWeekPredicate(Date date) {
        if (date == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1);
        LocalDate lastDay = now.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 7);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return (localDate.isAfter(firstDay) || localDate.isEqual(firstDay)) && (localDate.isBefore(lastDay) || localDate.isEqual(lastDay));
    }

    public static boolean prevWeekPredicate(Date date) {
        if (date == null) {
            return false;
        }
        LocalDate now = LocalDate.now().minusWeeks(1);
        LocalDate firstDay = now.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1);
        LocalDate lastDay = now.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 7);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return (localDate.isAfter(firstDay) || localDate.isEqual(firstDay)) && (localDate.isBefore(lastDay) || localDate.isEqual(lastDay));
    }

    public static boolean fistWeekOfMonthPassed() {
        LocalDate now = LocalDate.now();
        return now.getDayOfMonth() > 7;
    }

    public static int randomiseDaytimeHourShiftForTime(LocalDateTime date) {
        // get any hour which is 10-21 for today
        // if it's not possible, for ex. if it's 23:00 already - return 0
        int hour = date.getHour();
        if (hour >= 21) {
            return 0;
        }
        if (hour >= 10) {
            return (int) (Math.random() * (21 - hour));
        }
        return (int) (Math.random() * (21 - 10)) + (10 - hour);
    }

}
