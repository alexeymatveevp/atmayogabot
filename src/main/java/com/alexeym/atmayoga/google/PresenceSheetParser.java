package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.model.YogaUser;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PresenceSheetParser {



    public List<PresenceItem> parsePresenceSheetItems(List<List<Object>> sheet) {
        List<PresenceItem> result = new ArrayList<>();
        if (sheet == null || sheet.size() <= 1) {
            return result;
        }

        List<Object> rowWithDates = sheet.get(1);
        Map<Integer, Date> datesMap = SheetParsingUtils.findDatesWithIndex(rowWithDates);
        // find when was tavrik
        List<Object> rowWithTavrik = sheet.get(2);
        Map<Integer, Boolean> tavrikMap = new HashMap<>();
        int idx = 0;
        for (Object cell : rowWithTavrik) {
            String text = cell.toString();
            // any text in cell is treated as YES
            if (StringUtils.isNotEmpty(text)) {
                // should also contain date under
                if (datesMap.get(idx) != null) {
                    tavrikMap.put(idx, Boolean.TRUE);
                }
            }
            idx++;
        }

        // parse presence
        List<List<Object>> userRows = sheet.subList(3, sheet.size());
        for (List<Object> row : userRows) {
            // if row or name is empty - just skip the row
            if (row.size() == 0) {
                continue;
            }
            String nameOrEmpty = row.get(0).toString();
            if (StringUtils.isEmpty(nameOrEmpty)) {
                continue;
            }
            YogaUser yogaUser = new YogaUser();
            // adopt the yoga user name because they do not match in the sheet and in telegram
            nameOrEmpty = getAdoptedUserName(nameOrEmpty);
            String[] nameParts = nameOrEmpty.split(" ");
            if (nameParts.length == 1) {
                yogaUser.setFirstName(nameParts[0]);
            } else {
                yogaUser.setFirstName(nameParts[0]);
                yogaUser.setLastName(nameParts[1]);
            }

            int cellIdx = 0;
            for (Object cell : row) {
                // start with B
                if (cellIdx >= 1) {
                    String text = cell.toString();
                    if (StringUtils.isNotEmpty(text)) {
                        Date date = datesMap.get(cellIdx);
                        // treat as presence only if there is a date above
                        if (date != null) {
                            Boolean tavrik = tavrikMap.get(cellIdx);
                            PresenceItem item = new PresenceItem(
                                    yogaUser,
                                    date,
                                    Boolean.TRUE.equals(tavrik),
                                    text
                            );
                            result.add(item);
                        }
                    }
                }
                cellIdx++;
            }
        }
        return result;
    }

    private String getAdoptedUserName(String sheetUserName) {
        String adoptedName = PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.get(sheetUserName);
        return adoptedName == null ? sheetUserName : adoptedName;
    }

}
