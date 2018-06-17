package com.alexeym.atmayoga.checkvist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskNote {

    String comment;

    public TaskNote() {
    }

    public TaskNote(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
