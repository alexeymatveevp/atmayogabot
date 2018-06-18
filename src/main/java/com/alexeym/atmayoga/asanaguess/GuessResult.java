package com.alexeym.atmayoga.asanaguess;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class GuessResult {
    boolean correct;
    int matchPercentage;

    public GuessResult(boolean correct, int matchPercentage) {
        this.correct = correct;
        this.matchPercentage = matchPercentage;
    }

    @Override
    public String toString() {
        return "GuessResult{" +
                "correct=" + correct +
                ", matchPercentage=" + matchPercentage +
                '}';
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }
}
