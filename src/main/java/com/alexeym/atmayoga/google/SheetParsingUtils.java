package com.alexeym.atmayoga.google;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexey Matveev on 09.06.2018
 */
public class SheetParsingUtils {

    static Pattern DATE_PATTERN = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");
    static SimpleDateFormat SHEET_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static String generateUnknownActivity() {
        return "неизвестная активность";
    }

    public static Map<Integer, Date> findDatesWithIndex(List<Object> datesRow) {
        Map<Integer, Date> result = new TreeMap<>();
        int index = 0;
        for (Object cell : datesRow) {
            // should start with C
//            if (index >= 2) {
            Matcher m = DATE_PATTERN.matcher(cell.toString());
            if (m.matches()) {
                try {
                    Date date = SHEET_DATE_FORMAT.parse(cell.toString());
                    result.put(index, date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
//            }
            index++;
        }
        return result;
    }

}
