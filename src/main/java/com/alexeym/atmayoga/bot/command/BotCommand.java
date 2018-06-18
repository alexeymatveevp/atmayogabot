package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public interface BotCommand {

    String START = "/start";
    String TRAINING_LINK = "/training_link";
    String TRAINING_LAST = "/training_last";
    String PRESENCE_LAST = "/presence_last";
    String PRESENCE_THIS_MONTH = "/presence_month";
    String STAT_TOP1_OVERALL = "/stat_top1_overall";
    String STAT_MONTH_TOP1 = "/stat_month_top1";
    String STAT_MONTH_TOP3 = "/stat_month_top3";
    String STAT_MY_ACTIVITY = "/stat_my_activity";
    String TAVRIK = "/tavrik";
    String KOTIK = "/kotik";
    String ASANA_GUESS_GAME = "/asana_guess_game";

    String executeAndGetUserResponse(Message userMsg);

}
