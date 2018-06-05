package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.Practice;
import com.alexeym.atmayoga.common.YogaUser;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class SheetQueryService {

    Pattern DATE_PATTERN = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");

    public List<YogaUser> getAllUsers() throws Exception {
//        final String rangeQuery = "июнь 2018!A1:F";
        final String rangeQuery = "июнь 2018";

        ValueRange response = SheetServiceInitializer.getSheetsService().spreadsheets().values()
                .get(SheetServiceInitializer.ATMAYOGA_SPREADSHEET_ID, rangeQuery)
                .execute();
        List<List<Object>> rows = response.getValues();
        if (rows == null || rows.size() <= 1) {
            return new ArrayList<>();
        } else {
            List<YogaUser> result = new ArrayList<>();
            // first row is a header
            List<Object> headerRow = rows.get(0);
            // get all dates
//            List<LocalDate> dates = parseDatesRange(headerRow);
            rows = rows.subList(1, rows.size());
            int rowNumber = 0;
            YogaUser currentYogaUser = null;
            for (List<Object> userRow : rows) {
                rowNumber++;
                if (rowNumber % 2 == 1) {
                    String userName = userRow.get(0).toString();
                    String[] nameParts = userName.split(" ");
                    currentYogaUser = new YogaUser(null, nameParts[0], nameParts[1]);
                }
                // ensure there is some data
                if (userRow.size() > 2) {
                    if (rowNumber % 2 == 1) {
                        // individual practice
                        int practiceStartIndex = 2;
                        List<Object> practiceRow = userRow.subList(practiceStartIndex, userRow.size());
                        int practiceCount = 0;
                        for (Object p : practiceRow) {
                            String date = headerRow.get(practiceCount + practiceStartIndex).toString();
                            Practice pr = currentYogaUser.getPracticeMap().get(date);
                            if (pr == null) {
                                pr = new Practice();
                                currentYogaUser.getPracticeMap().put(date, pr);
                            }
                            pr.setIndividual(p.toString());
                            practiceCount++;
                        }

                    } else {
                        // physical activity
                        int practiceStartIndex = 2;
                        List<Object> practiceRow = userRow.subList(practiceStartIndex, userRow.size());
                        int practiceCount = 0;
                        for (Object p : practiceRow) {
                            String date = headerRow.get(practiceCount + practiceStartIndex).toString();
                            Practice pr = currentYogaUser.getPracticeMap().get(date);
                            if (pr == null) {
                                pr = new Practice();
                                currentYogaUser.getPracticeMap().put(date, pr);
                            }
                            pr.setPhysical(p.toString());
                            practiceCount++;
                        }
                    }
                }
                if (currentYogaUser != null && !result.contains(currentYogaUser)) {
                    result.add(currentYogaUser);
                }
            }
            return result;
        }
    }

    List<LocalDate> parseDatesRange(List<Object> headerRow) {
        List<LocalDate> result = new ArrayList<>();
        // start with C
        headerRow = headerRow.subList(2, headerRow.size());
        // remove all garbage
        Iterator<Object> it = headerRow.iterator();
        while (it.hasNext()) {
            Object cell = it.next();
            Matcher m = DATE_PATTERN.matcher(cell.toString());
            if (m.matches()) {
                LocalDate date = LocalDate.parse(cell.toString());
                result.add(date);
            }
        }
        return result;
    }

}
