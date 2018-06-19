package com.alexeym.atmayoga.asanaguess;

import com.alexeym.atmayoga.model.YogaMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class AsanaGuess {
    Long chatId;
    String asanaSticker;
    LocalDateTime start;
    List<YogaMessage> messages;
    ScheduledFuture gameTimeoutFuture;

    public AsanaGuess(Long chatId, String asanaSticker, LocalDateTime start, ScheduledFuture gameTimeoutFuture) {
        this.chatId = chatId;
        this.asanaSticker = asanaSticker;
        this.start = start;
        this.gameTimeoutFuture = gameTimeoutFuture;
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

    public ScheduledFuture getGameTimeoutFuture() {
        return gameTimeoutFuture;
    }

    public void setGameTimeoutFuture(ScheduledFuture gameTimeoutFuture) {
        this.gameTimeoutFuture = gameTimeoutFuture;
    }
}
