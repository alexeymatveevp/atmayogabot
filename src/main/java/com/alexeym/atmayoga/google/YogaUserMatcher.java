package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.model.YogaUser;
import com.alexeym.atmayoga.storage.CommonStorage;
import com.alexeym.atmayoga.storage.UserStorage;
import org.telegram.telegrambots.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexey Matveev on 09.06.2018
 */
public class YogaUserMatcher {

    /**
     * Will map all non-intuitive user names.
     * The ones like "Евгений Бабенко" are ok, so they are not in the map.
     * If no user in map - treat them as usual with .split(" ").
     */
    // TODO match not by username but by ID, need to collect all ids
    public final Map<String, String> PRESENCE_NAME_TO_TELEGRAM_NAME_MAP = new HashMap<>();
    public final Map<String, String> TELEGRAM_TO_PRESENCE_REVERSE_MAP = new HashMap<>();

    {
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Эвьяван", "Эвиаван");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Кирилл", "Kirill Golm");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Анатолий", "Anatoly Vostryakov");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Леша", "Alexey Matveev");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Наталья СМ", "Natali");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Саша", "Котова Саша");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Оксана", "Oksana Fonicheva");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Лена", ""); // ???
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Наталья", ""); // ???
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Наталья Общительная", "Natalia");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Даша ", "Дарья Романцева");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Алена Лисенкина", "Alena Lisenkina");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Максим", "Максим Топоров");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Рузанна", "Ruzanna Martirosyan");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Настя Нестерова", "Nastya Nesterova");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Дмитрий", "Dmitry Nazarov");
        PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.put("Надежда", "Nadezhda");

        for (Map.Entry<String, String> entry : PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.entrySet()) {
            TELEGRAM_TO_PRESENCE_REVERSE_MAP.put(entry.getValue(), entry.getKey());
        }

    }

    public static final String NAME_SEPARATOR = " ";

    CommonStorage storage = new CommonStorage();

    public YogaUser tryGetExistingYogaUser(User telegramUser) {
        return tryGetExistingYogaUser(getAdoptedUserName(telegramUser));
    }

    public String getAdoptedUserName(User telegramUser) {
        return telegramUser.getFirstName() + NAME_SEPARATOR + telegramUser.getLastName();
    }

    public YogaUser tryGetExistingYogaUser(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        String[] nameParts = name.split(" ");
        if (nameParts.length == 1) {
            // only name is not an option right now
            return null;
        }
        if (nameParts.length == 2) {
            String fname = nameParts[0];
            String lname = nameParts[1];
            List<YogaUser> knownUsers = storage.findAll(YogaUser.class);
            for (YogaUser knownUser : knownUsers) {
                if (knownUser.getFirstName().equalsIgnoreCase(fname) && knownUser.getLastName().equalsIgnoreCase(lname)) {
                    return knownUser;
                }
            }
        }
        return null;
    }

    public boolean checkUsersMostLikelyMatches(YogaUser user, YogaUser anotherUser) {
        if (user.getId() != null && anotherUser.getId() != null && user.getId().equals(anotherUser.getId())) {
            return true;
        }
        if (user.getFirstName().equalsIgnoreCase(anotherUser.getFirstName())
                && user.getLastName().equalsIgnoreCase(anotherUser.getLastName())) {
            return true;
        }
        return false;
    }

    public String getTelegramNameFromPresenceSheetName(String sheetUserName) {
        String adoptedName = PRESENCE_NAME_TO_TELEGRAM_NAME_MAP.get(sheetUserName);
        return adoptedName == null ? sheetUserName : adoptedName;
    }

    public String getPresenceSheetNameFromTelegramUser(YogaUser user) {
        // hm.. should be fname + " " + lname
        // but can be exceptions
        // in exceptional case return null
        return TELEGRAM_TO_PRESENCE_REVERSE_MAP.get(user.getName());
    }

}
