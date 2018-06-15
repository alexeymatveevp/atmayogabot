package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.model.YogaUser;
import com.alexeym.atmayoga.model.TrainingItem;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexey Matveev on 08.06.2018
 */
public class TrainingSheetParser {

    /**
     * Get only user cells right now, as plain list.
     */
    public List<TrainingItem> parseTrainingSheetItems(List<List<Object>> sheet) {
        if (sheet == null || sheet.size() <= 1) {
            return new ArrayList<>();
        }
        List<TrainingItem> result = new ArrayList<>();
        // first row is a header
        List<Object> headerRow = sheet.get(0);
        // get parsed dates with column index
        Map<Integer, Date> datesMap = SheetParsingUtils.findDatesWithIndex(headerRow);
        // get all rows except header
        List<List<Object>> dataRows = sheet.subList(1, sheet.size());

        YogaUser currentYogaUser = null;

        for (List<Object> userRow : dataRows) {
            // if name not empty - it's new user row
            // if empty - previous user activity
            String nameOrEmpty = userRow.get(0).toString();
            if (StringUtils.isNotEmpty(nameOrEmpty)) {
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

            // category of training
            String activityCategory = userRow.get(1).toString();
            if (activityCategory == null || activityCategory.equals("")) {
                activityCategory = SheetParsingUtils.generateUnknownActivity();
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
                            TrainingItem item = new TrainingItem(
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
        }
        return result;
    }

}
