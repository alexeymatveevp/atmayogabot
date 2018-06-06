package com.alexeym.atmayoga.accuweather;

import com.alexeym.atmayoga.accuweather.forecast.Forecast;
import com.alexeym.atmayoga.accuweather.forecast.ForecastResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class WeatherService {

    public static final String API_KEY = "jJbGCzlycb4KYyvgv8nqphDm86Kfawp2";

    public static final String ST_PETE_LOCATION_ID = "295212";
    public static final String FIVE_DAYS_FORECAST_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day"; // + locationId

    public static final String ST_PETE_5_DAYS_FORECAST_URL = FIVE_DAYS_FORECAST_URL + "/" + ST_PETE_LOCATION_ID + "?apikey=" + API_KEY + "&language=ru-ru&metric=true";

    static ObjectMapper objectMapper = new ObjectMapper();
    static OkHttpClient client = new OkHttpClient();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public AdoptedWeatherResponse getClosestWeekendWeatherSummary() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().get(ChronoField.DAY_OF_WEEK); // 1 to 7
        // target days are: 6 and 7
        // as forecast is 5 days accepted days are:
        // 2,3,4,5,6.. 7 and 1 are too far away from 6
        if (dayOfWeek == 1 || dayOfWeek == 7) return null;

        System.out.println(ST_PETE_5_DAYS_FORECAST_URL);
        Request request = new Request.Builder()
                .url(ST_PETE_5_DAYS_FORECAST_URL)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();
        System.out.println(responseJson);
        ForecastResponse forecastResponse = objectMapper.readValue(responseJson, ForecastResponse.class);
        // forecasts in response are ordered
        // if today is 2, then we need 4th index to get day 6 = 6 - 2
        // if today is 3, then we need 3th index to get day 6 = 6 - 3
        // ...
        // exception:
        // if today is 6, then we need 1th index to get day 7
        int offsetDaysIndex = dayOfWeek == 6 ? 1 : 6 - dayOfWeek;
        Forecast forecast = forecastResponse.getDailyForecasts().get(offsetDaysIndex);
        return new AdoptedWeatherResponse(
                forecast.getDay().getPhrase(),
                forecast.getTemperature().getMin().getValue(),
                forecast.getTemperature().getMax().getValue()
        );
    }


}
