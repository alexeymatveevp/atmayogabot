package com.alexeym.atmayoga.common;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.util.Date;

@Document(collection = "yogaMessages", schemaVersion= "1.0")
public class YogaMessage {

    @Id
    private Integer id;
    private Integer userId;
    private Long chatId;
    private Date date;
    private String text;
    private String stickerId;

    public YogaMessage(Integer id, Integer userId, Long chatId, Date date, String text, String stickerId) {
        this.id = id;
        this.userId = userId;
        this.chatId = chatId;
        this.date = date;
        this.text = text;
        this.stickerId = stickerId;
    }

    public YogaMessage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }
}
