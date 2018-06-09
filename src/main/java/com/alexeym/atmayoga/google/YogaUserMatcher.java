package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.storage.UserStorage;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;

/**
 * @author Alexey Matveev on 09.06.2018
 */
public class YogaUserMatcher {

    public static final String NAME_SEPARATOR = " ";

    UserStorage userStorage = new UserStorage();

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
            List<YogaUser> knownUsers = userStorage.getKnownUsers();
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

}
