package com.alexeym.atmayoga.checkvist.model;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class TaskColor {

    String text;
    String background;

    public TaskColor() {
    }

    public TaskColor(String text, String background) {
        this.text = text;
        this.background = background;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
