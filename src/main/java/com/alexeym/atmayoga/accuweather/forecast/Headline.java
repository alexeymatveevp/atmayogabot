package com.alexeym.atmayoga.accuweather.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexey Matveev on 06.06.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Headline {
    @JsonProperty("Text")
    String text;
    @JsonProperty("Category")
    String category; // rain

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
