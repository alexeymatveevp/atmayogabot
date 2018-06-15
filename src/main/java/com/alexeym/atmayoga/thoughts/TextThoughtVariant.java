package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class TextThoughtVariant extends ThoughtVariant {

    private String text;

    public TextThoughtVariant() {
    }

    public TextThoughtVariant(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
