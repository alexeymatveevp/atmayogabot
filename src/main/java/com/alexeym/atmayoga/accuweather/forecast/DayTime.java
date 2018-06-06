package com.alexeym.atmayoga.accuweather.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexey Matveev on 06.06.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayTime {
    @JsonProperty("IconPhrase")
    String phrase;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
