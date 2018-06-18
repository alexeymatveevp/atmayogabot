package com.alexeym.atmayoga.asanaguess;

import com.alexeym.atmayoga.model.YogaMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class AsanaGuess {
    Long chatId;
    String asanaSticker;
    LocalDateTime start;
    List<YogaMessage> messages;

    public AsanaGuess(Long chatId, String asanaSticker, LocalDateTime start) {
        this.chatId = chatId;
        this.asanaSticker = asanaSticker;
        this.start = start;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getAsanaSticker() {
        return asanaSticker;
    }

    public void setAsanaSticker(String asanaSticker) {
        this.asanaSticker = asanaSticker;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void addMessage(YogaMessage message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    public List<YogaMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<YogaMessage> messages) {
        this.messages = messages;
    }
}
