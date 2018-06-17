package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.YogaConstants;
import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.model.YogaUser;
import com.alexeym.atmayoga.model.TrainingItem;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexey Matveev on 08.06.2018
 */
public class SheetDataService {

    TrainingSheetParser trainingSheetParser = new TrainingSheetParser();
    PresenceSheetParser presenceSheetParser = new PresenceSheetParser();
    YogaUserMatcher yogaUserMatcher = new YogaUserMatcher();

    public TrainingItem getUserLastActivity(YogaUser yogaUser) throws Exception {
        List<List<Object>> sheet = querySheet(YogaConstants.TRAINING_SPREADSHEET_ID, "июнь 2018"); // "июнь 2018!A1:F"
        List<TrainingItem> trainingItems = trainingSheetParser.parseTrainingSheetItems(sheet);
        List<TrainingItem> userTrainingItems = trainingItems.stream()
                .filter(item -> yogaUserMatcher.checkUsersMostLikelyMatches(yogaUser, item.getUser()))
                .collect(Collectors.toList());
        if (userTrainingItems != null) {
            // find item with latest date
            Date latestDate = null;
            TrainingItem latestItem = null;
            for (TrainingItem item : userTrainingItems) {
                Date date = item.getDate();
                if (latestDate == null || (date != null && latestDate.before(date))) {
                    latestDate = date;
                    latestItem = item;
                }
            }
            return latestItem;
        }
        return null;
    }

    public List<PresenceItem> getPresenceItems(String month) throws Exception {
        List<List<Object>> sheet = querySheet(YogaConstants.PRESENCE_SPREADSHEET_ID, month);
        return presenceSheetParser.parsePresenceSheetItems(sheet);
    }

    public List<PresenceItem> getPresenceItemsForCurrentMonth() throws Exception {
        return getPresenceItems(YogaUtils.getCurrentMonthAsRusString());
    }

    public List<List<Object>> querySheet(String sheetId, String sheetName) throws Exception {
        ValueRange response = SheetServiceInitializer.getSheetsService().spreadsheets().values()
                .get(sheetId, sheetName)
                .execute();
        return response.getValues();
    }



}
