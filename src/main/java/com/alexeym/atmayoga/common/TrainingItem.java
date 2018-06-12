package com.alexeym.atmayoga.common;

import java.util.Date;

/**
 * @author Alexey Matveev on 08.06.2018
 */
public class TrainingItem {

    /**
     * Can be either username holder or a stored user (with id) which app knows about.
     */
    private YogaUser user;
    private String activityCategory;
    private Date date;
    private String note;

    public TrainingItem() {
    }

    public TrainingItem(YogaUser user, String activityCategory, Date date, String note) {
        this.user = user;
        this.activityCategory = activityCategory;
        this.date = date;
        this.note = note;
    }

    public YogaUser getUser() {
        return user;
    }

    public void setUser(YogaUser user) {
        this.user = user;
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
