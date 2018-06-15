package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class StickerThoughtVariant extends ThoughtVariant {

    private String stickerId;

    public StickerThoughtVariant() {
    }

    public StickerThoughtVariant(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }
}
