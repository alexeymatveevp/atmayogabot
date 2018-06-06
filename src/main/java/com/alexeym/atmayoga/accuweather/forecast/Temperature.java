package com.alexeym.atmayoga.accuweather.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexey Matveev on 06.06.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature {
    @JsonProperty("Minimum")
    TemperatureMinMax min;
    @JsonProperty("Maximum")
    TemperatureMinMax max;

    public TemperatureMinMax getMin() {
        return min;
    }

    public void setMin(TemperatureMinMax min) {
        this.min = min;
    }

    public TemperatureMinMax getMax() {
        return max;
    }

    public void setMax(TemperatureMinMax max) {
        this.max = max;
    }
}
