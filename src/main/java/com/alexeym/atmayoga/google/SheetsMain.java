package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.model.YogaUser;
import com.alexeym.atmayoga.model.TrainingItem;

/**
 * Created by Alexey Matveev on 6/5/2018.
 */
public class SheetsMain {


    public static void main(String[] args) throws Exception {
        SheetDataService sheetDataService = new SheetDataService();
        TrainingItem userLastActivity = sheetDataService.getUserLastActivity(new YogaUser(null, "Евгений", "Улитин"));
        System.out.println(userLastActivity);
    }
}
