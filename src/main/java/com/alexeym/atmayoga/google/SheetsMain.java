package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.common.YogaUserTrainingItem;

/**
 * Created by Alexey Matveev on 6/5/2018.
 */
public class SheetsMain {


    public static void main(String[] args) throws Exception {
        SheetDataProvider sheetDataProvider = new SheetDataProvider();
        YogaUserTrainingItem userLastActivity = sheetDataProvider.getUserLastActivity(new YogaUser(null, "Евгений", "Улитин"));
        System.out.println(userLastActivity);
    }
}
