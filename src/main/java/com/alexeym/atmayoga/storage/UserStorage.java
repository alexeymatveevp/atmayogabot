package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.common.YogaUser;
import org.telegram.telegrambots.api.objects.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class UserStorage {

    public List<YogaUser> getKnownUsers() {
        return JsonDBInit.jsonDBTemplate.findAll(YogaUser.class);
    }

    public void upsertUser(YogaUser user) {
        JsonDBInit.jsonDBTemplate.upsert(user);
    }

    public YogaUser getUser(Integer id) {
        return JsonDBInit.jsonDBTemplate.findById(id, YogaUser.class);
    }

    public void anotherUserCame(User user) {
        Integer id = user.getId();
        YogaUser yogaUser = JsonDBInit.jsonDBTemplate.findById(id, YogaUser.class);
        if (yogaUser == null) {
            // ok new one
            YogaUser newUser = new YogaUser();
            newUser.setId(id);
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            yogaUser = newUser;
        }
        yogaUser.setNumberOfMessagesThisWeek(yogaUser.getNumberOfMessagesThisWeek() + 1);
        JsonDBInit.jsonDBTemplate.upsert(yogaUser);
    }
    
}
