package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class StickerThoughtStep extends ThoughtStep {

    private String stickerId;

    public StickerThoughtStep() {
    }

    public StickerThoughtStep(String stickerId) {
        this.stickerId = stickerId;
    }

    @Override
    public String toString() {
        return "StickerThoughtStep{" +
                "stickerId='" + stickerId + '\'' +
                '}';
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }
}
