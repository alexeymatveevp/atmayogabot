package com.alexeym.atmayoga;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.concurrent.TimeUnit;

/**
 * Created by Alexey Matveev on 6/4/2018.
 */
public class BotStarter {

     static {
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        AtmayogaBot bot = new AtmayogaBot();
        ScheduledTasksManager scheduledTasksManager = new ScheduledTasksManager();

        // horray! I can set everything to global context
        GlobalContext.BOT = bot;
        GlobalContext.SCHEDULING_MANAGER = scheduledTasksManager;
        // yay!!!

        // setup cron4j scheduler
        scheduledTasksManager.initAlgorithm();

        // run the bot
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
