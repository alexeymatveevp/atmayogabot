package com.alexeym.atmayoga.accuweather.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexey Matveev on 06.06.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureMinMax {
    @JsonProperty("Value")
    float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
