package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.accuweather.AdoptedWeatherResponse;
import com.alexeym.atmayoga.accuweather.WeatherService;
import com.alexeym.atmayoga.bot.AtmayogaBot;
import com.alexeym.atmayoga.bot.BotMessages;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class TavrikCommand implements BotCommand {

    WeatherService weatherService = new WeatherService();

    @Override
    public String executeAndGetUserResponse(Message userMsg, AtmayogaBot bot) {
        try {
            AdoptedWeatherResponse response = weatherService.getClosestWeekendWeatherSummary();
            if (response == null) {
                // too far from weekends
                return "Ещё далековато до выходных - рано загадывать :)";
            }

            String msg = "";
            LocalDateTime now = LocalDateTime.now();
            int dayOfWeek = now.getDayOfWeek().get(ChronoField.DAY_OF_WEEK);
            if (dayOfWeek == 6) {
                msg += "Завтра";
            } else {
                msg += "В субботу";
            }
            String note = response.getNote();
            msg += " обещают \"" + note + "\"\n";
            msg += "Температуру от " + response.getMinT() + " до " + response.getMaxT() + " градусов\n";
            msg += "...\n";

            if (note.contains("дожд") || note.contains("ливни") || note.contains("ливень")) {
                // totally no
                msg += "Ух, наверное стоит в зале";
                if (response.getMaxT() > 18) {
                    msg += "\nХотя температура вроде нормальная - можно и в таврик :)";
                }
            } else {
                float maxT = response.getMaxT();
                if (maxT < 10) {
                    // too cold
                    msg += "Прохладно, пойдем в зал";
                } else if (maxT < 15) {
                    // warm but not enough
                    msg += "Чуток прохладно, но можно и в таврик при большом желании";
                } else if (maxT < 20) {
                    // good weather
                    msg += "Погода неплохая, можно и в таврик";
                } else {
                    // perfect!
                    msg += "Идем в таврик! :)";
                }
            }
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return BotMessages.SMTH_WRONG;
        }
    }
}
