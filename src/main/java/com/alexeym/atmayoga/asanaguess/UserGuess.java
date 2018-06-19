package com.alexeym.atmayoga.asanaguess;

/**
 * @author Alexey Matveev on 19.06.2018
 */
public class UserGuess {
    Integer userId;
    int percentage;
    String guess;

    public UserGuess(Integer userId, int percentage, String guess) {
        this.userId = userId;
        this.percentage = percentage;
        this.guess = guess;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
