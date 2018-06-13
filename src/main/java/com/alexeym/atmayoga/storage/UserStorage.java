package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.common.UserMetadata;
import com.alexeym.atmayoga.common.YogaUser;
import org.telegram.telegrambots.api.objects.Message;
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
            yogaUser = new YogaUser();
            yogaUser.setId(id);
        }
        yogaUser.setFirstName(user.getFirstName());
        yogaUser.setLastName(user.getLastName());
        yogaUser.setUsername(user.getUserName());
        JsonDBInit.jsonDBTemplate.upsert(yogaUser);
//        String text = userMsg.getText();
//        if (text != null && !text.startsWith("/")) {
//            yogaUser.addMessage(text);
//        }
    }

    public UserMetadata findUserMetadata(Integer userId) {
        return JsonDBInit.jsonDBTemplate.findById(userId, UserMetadata.class);
    }

    public void updateUserMetadata(UserMetadata metadata) {
        JsonDBInit.jsonDBTemplate.upsert(metadata);
    }
    
}
