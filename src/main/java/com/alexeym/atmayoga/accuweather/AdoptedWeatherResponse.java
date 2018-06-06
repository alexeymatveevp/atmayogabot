package com.alexeym.atmayoga.accuweather;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class AdoptedWeatherResponse {

    private String note;
    private float minT;
    private float maxT;

    public AdoptedWeatherResponse(String note, float minT, float maxT) {
        this.note = note;
        this.minT = minT;
        this.maxT = maxT;
    }

    public String getNote() {
        return note;
    }

    public float getMinT() {
        return minT;
    }

    public float getMaxT() {
        return maxT;
    }
}
