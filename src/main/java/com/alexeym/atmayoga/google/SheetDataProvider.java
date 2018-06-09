package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.common.YogaUserTrainingItem;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexey Matveev on 08.06.2018
 */
public class SheetDataProvider {

    SheetQueryService queryService = new SheetQueryService();
    TrainingSheetParser trainingSheetParser = new TrainingSheetParser();
    YogaUserMatcher yogaUserMatcher = new YogaUserMatcher();

    public YogaUserTrainingItem getUserLastActivity(YogaUser yogaUser) throws Exception {
        List<List<Object>> sheet = queryService.queryTrainingSheet("июнь 2018"); // "июнь 2018!A1:F"
        List<YogaUserTrainingItem> trainingItems = trainingSheetParser.parseTrainingSheetItems(sheet);
        List<YogaUserTrainingItem> userTrainingItems = trainingItems.stream()
                .filter(item -> yogaUserMatcher.checkUsersMostLikelyMatches(yogaUser, item.getUser()))
                .collect(Collectors.toList());
        if (userTrainingItems != null) {
            // find item with latest date
            Date latestDate = null;
            YogaUserTrainingItem latestItem = null;
            for (YogaUserTrainingItem item : userTrainingItems) {
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

}
