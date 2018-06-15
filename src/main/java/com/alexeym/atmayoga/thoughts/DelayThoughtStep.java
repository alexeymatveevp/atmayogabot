package com.alexeym.atmayoga.thoughts;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class DelayThoughtStep extends ThoughtStep {

    private int secondsDelay;

    public DelayThoughtStep() {
    }

    public DelayThoughtStep(int secondsDelay) {
        this.secondsDelay = secondsDelay;
    }

    public int getSecondsDelay() {
        return secondsDelay;
    }

    public void setSecondsDelay(int secondsDelay) {
        this.secondsDelay = secondsDelay;
    }
}
