package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.common.YogaUserTrainingItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexey Matveev on 08.06.2018
 */
public class TrainingSheetParser {

    Pattern DATE_PATTERN = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");
    SimpleDateFormat TRAINING_SHEET_DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy");

    /**
     * Get only user cells right now, as plain list.
     */
    public List<YogaUserTrainingItem> parseTrainingSheetItems(List<List<Object>> sheet) {
        if (sheet == null || sheet.size() <= 1) {
            return new ArrayList<>();
        }
        List<YogaUserTrainingItem> result = new ArrayList<>();
        // first row is a header
        List<Object> headerRow = sheet.get(0);
        // get parsed dates with column index
        Map<Integer, Date> datesMap = findDatesWithIndex(headerRow);
        // get all rows except header
        List<List<Object>> dataRows = sheet.subList(1, sheet.size());
        int rowNumber = 1;

        YogaUser currentYogaUser = null;

        for (List<Object> userRow : dataRows) {
            String nameOrEmpty = userRow.get(0).toString();
            String activityCategory = userRow.get(1).toString();
            if (activityCategory == null || activityCategory.equals("")) {
                activityCategory = SheetParsingUtils.generateUnknownActivity();
            }

            // if name not empty - it's new user row
            // if empty - previous user activity
            if (nameOrEmpty != null && !nameOrEmpty.equals("")) {

                YogaUser yogaUser = new YogaUser();
                String[] nameParts = nameOrEmpty.split(" ");
                if (nameParts.length == 1) {
                    yogaUser.setFirstName(nameParts[0]);
                } else {
                    yogaUser.setFirstName(nameParts[0]);
                    yogaUser.setLastName(nameParts[1]);
                }
                currentYogaUser = yogaUser;
            }

            // shouldn't be empty at this state
            if (currentYogaUser != null) {
                int columnIndex = 0;
                for (Object cell : userRow) {
                    // start reading with C column
                    if (columnIndex >= 2) {
                        Date cellDate = datesMap.get(columnIndex);
                        String userNote = cell.toString(); // that's what we're here for!
                        // create new item only if there is note
                        if (userNote != null && !userNote.equals("")) {
                            YogaUserTrainingItem item = new YogaUserTrainingItem(
                                    currentYogaUser,
                                    activityCategory,
                                    cellDate,
                                    userNote
                            );
                            result.add(item);
                        }
                    }
                    columnIndex++;
                }
            }
            rowNumber++;
        }
        return result;
    }

    private Map<Integer, Date> findDatesWithIndex(List<Object> headerRow) {
        Map<Integer, Date> result = new TreeMap<>();
        int index = 0;
        for (Object cell : headerRow) {
            // should start with C
            if (index >= 2) {
                Matcher m = DATE_PATTERN.matcher(cell.toString());
                if (m.matches()) {
                    try {
                        Date date = TRAINING_SHEET_DATE_FORMAT.parse(cell.toString());
                        result.put(index, date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            index++;
        }
        return result;
    }

}
