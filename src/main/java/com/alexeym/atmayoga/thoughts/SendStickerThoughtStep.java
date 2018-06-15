package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class SendStickerThoughtStep extends ThoughtStep {

    private String stickerId;

    public SendStickerThoughtStep() {
    }

    public SendStickerThoughtStep(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }
}
