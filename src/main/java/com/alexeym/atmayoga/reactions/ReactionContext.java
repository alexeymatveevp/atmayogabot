package com.alexeym.atmayoga.reactions;

import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public class ReactionContext {

    private String text;
    private Message message;

    public ReactionContext() {
    }

    public ReactionContext(String text, Message message) {
        this.text = text;
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
