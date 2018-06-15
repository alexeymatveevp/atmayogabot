package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.model.UserMetadata;
import com.alexeym.atmayoga.model.YogaUser;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class UserStorage {

    public YogaUser getUser(Integer id) {
        return JsonDBInit.jsonDBTemplate.findById(id, YogaUser.class);
    }

    public void anotherUserCame(User user) {
        Integer id = user.getId();
        YogaUser yogaUser = JsonDBInit.jsonDBTemplate.findById(id, YogaUser.class);
        if (yogaUser == null) {
            // ok new one
            yogaUser = new YogaUser();
            yogaUser.setId(id);
        }
        yogaUser.setFirstName(user.getFirstName());
        yogaUser.setLastName(user.getLastName());
        yogaUser.setUsername(user.getUserName());
        JsonDBInit.jsonDBTemplate.upsert(yogaUser);
    }

}
