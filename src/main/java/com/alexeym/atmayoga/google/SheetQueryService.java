package com.alexeym.atmayoga.google;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class SheetQueryService {

    public List<List<Object>> queryTrainingSheet(String sheetName) throws Exception {
        ValueRange response = SheetServiceInitializer.getSheetsService().spreadsheets().values()
                .get(SheetServiceInitializer.ATMAYOGA_SPREADSHEET_ID, sheetName)
                .execute();

//        ValueRange response2 = SheetServiceInitializer.getSheetsService().spreadsheets().developerMetadata()
//                .get(SheetServiceInitializer.ATMAYOGA_SPREADSHEET_ID, sheetName)
//                .execute();
        return response.getValues();
    }

}
