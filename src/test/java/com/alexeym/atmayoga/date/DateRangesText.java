package com.alexeym.atmayoga.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class DateRangesText {

    @Test
    public void testDates() throws Exception {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate lastDay = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 7);

        System.out.println(firstDay);
        System.out.println(lastDay);

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        System.out.println(localDate.isEqual(now));
        System.out.println(localDate.isAfter(firstDay));
        System.out.println(localDate.isBefore(lastDay));

    }

}
