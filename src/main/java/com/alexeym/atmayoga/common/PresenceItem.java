package com.alexeym.atmayoga.common;

import java.util.Date;

public class PresenceItem {

    /**
     * Can be either username holder or a stored user (with id) which app knows about.
     */
    private YogaUser user;
    private Date date;
    private boolean tavrik;
    /**
     * Sometimes there can be "2" instead of "1" in the sheet, not sure how to tread it yet.
     */
    private String text;

    public PresenceItem() {
    }

    public PresenceItem(YogaUser user, Date date, boolean tavrik, String text) {
        this.user = user;
        this.date = date;
        this.tavrik = tavrik;
        this.text = text;
    }

    public YogaUser getUser() {
        return user;
    }

    public void setUser(YogaUser user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isTavrik() {
        return tavrik;
    }

    public void setTavrik(boolean tavrik) {
        this.tavrik = tavrik;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
