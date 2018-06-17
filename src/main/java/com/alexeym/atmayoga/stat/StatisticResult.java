package com.alexeym.atmayoga.stat;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class StatisticResult {

    String text;
    int interestRate; // 0 default, 1 interesting, 2 must print this!, -1 - bad stat

    public StatisticResult() {
    }

    public StatisticResult(String text, int interestRate) {
        this.text = text;
        this.interestRate = interestRate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }
}
