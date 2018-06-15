package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.model.YogaUser;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PresenceSheetParser {

    /**
     * Will map all non-intuitive user names.
     * The ones like "Евгений Бабенко" are ok, so they are not in the map.
     * If no user in map - treat them as usual with .split(" ").
     */
    // TODO match not by username but by ID, need to collect all ids
    Map<String, String> PRESENCE_NAME_TO_TELEGRAM_NAME_MAP = new HashMap<>();

    {
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Эвьяван", "Эвиаван");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Кирилл", "Kirill Golm");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Анатолий", "Anatoly Vostryakov");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Леша", "Alexey Matveev");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Наталья СМ", "Natali");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Саша", "Котова Саша");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Оксана", "Oksana Fonicheva");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Лена", null); // ???
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Наталья", null); // ???
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Наталья Общительная", "Natalia");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Даша ", "Дарья Романцева");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Алена Лисенкина", "Alena Lisenkina");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Максим", "Максим Топоров");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Рузанна", "Ruzanna Martirosyan");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Настя Нестерова", "Nastya Nesterova");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Дмитрий", "Dmitry Nazarov");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Надежда", "Nadezhda");
    }

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
