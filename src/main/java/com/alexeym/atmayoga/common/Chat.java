package com.alexeym.atmayoga.common;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class Chat {

    private Long chatId;

    public Chat(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
