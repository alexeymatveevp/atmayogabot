package com.alexeym.atmayoga.model;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

/**
 * @author Alexey Matveev on 05.06.2018
 */
@Document(collection = "chats", schemaVersion= "1.0")
public class Chat {

    @Id
    private Long chatId;
    private String title;

    public Chat() {
    }

    public Chat(Long chatId, String title) {
        this.chatId = chatId;
        this.title = title;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
