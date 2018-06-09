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
    String KOTIK = "/kotik";
    String TAVRIK = "/tavrik";
    String ACTIVITY = "/activity";

    String executeAndGetUserResponse(Message userMsg);

}
