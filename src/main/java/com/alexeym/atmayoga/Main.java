package com.alexeym.atmayoga;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by Alexey Matveev on 6/4/2018.
 */
public class Main {

    static {
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        AtmayogaBot bot = new AtmayogaBot();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
