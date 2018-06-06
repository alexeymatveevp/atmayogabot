package com.alexeym.atmayoga.accuweather;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class Main {

    public static void main(String[] args) throws Exception {
        WeatherService weatherService = new WeatherService();
        AdoptedWeatherResponse closestWeekendWeatherSummary = weatherService.getClosestWeekendWeatherSummary();
        System.out.println(closestWeekendWeatherSummary);
    }
}
