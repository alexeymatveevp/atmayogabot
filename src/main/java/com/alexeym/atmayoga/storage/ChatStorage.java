package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.common.Chat;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public class ChatStorage {

    public Chat findById(Long id) {
        return JsonDBInit.jsonDBTemplate.findById(id, Chat.class);
    }

    public void upsertChat(Chat chat) {
        JsonDBInit.jsonDBTemplate.upsert(chat);
    }

}
