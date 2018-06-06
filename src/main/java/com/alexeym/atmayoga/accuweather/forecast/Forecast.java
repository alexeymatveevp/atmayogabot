package com.alexeym.atmayoga.accuweather.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Alexey Matveev on 06.06.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
//    @JsonProperty("Date")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
//    LocalDateTime date;
    @JsonProperty("Temperature")
    Temperature temperature;
    @JsonProperty("Day")
    DayTime day;

//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public DayTime getDay() {
        return day;
    }

    public void setDay(DayTime day) {
        this.day = day;
    }
}
