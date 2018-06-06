package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public interface BotCommand {

    String START = "/start";
    String LINK = "/link";
    String LIST = "/list";
    String KOTIK = "/kotik";
    String TAVRIK = "/tavrik";
    String MY_ACTIVITY = "/myactivity";

    String executeAndGetUserResponse(Message userMsg, AtmayogaBot bot);

}
