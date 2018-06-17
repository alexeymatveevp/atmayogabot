package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class TextThougthStep extends ThoughtStep {

    private String text;

    public TextThougthStep() {
    }

    @Override
    public String toString() {
        return "TextThougthStep{" +
                "text='" + text + '\'' +
                '}';
    }

    public TextThougthStep(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
