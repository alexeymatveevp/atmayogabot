package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.PrettyPrinter;
import com.alexeym.atmayoga.common.YogaUser;

import java.util.List;

/**
 * Created by Alexey Matveev on 6/5/2018.
 */
public class SheetsMain {


    public static void main(String[] args) throws Exception {

        SheetQueryService sheetQueryService = new SheetQueryService();
        List<YogaUser> allYogaUsers = sheetQueryService.getAllUsers();
//        System.out.println(allYogaUsers);
        System.out.println(PrettyPrinter.prettyUserPractice(allYogaUsers.get(1)));
    }
}
