package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class SendTextThougthStep extends ThoughtStep {

    private String text;

    public SendTextThougthStep() {
    }

    public SendTextThougthStep(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
