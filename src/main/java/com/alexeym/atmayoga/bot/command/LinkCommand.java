package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import com.alexeym.atmayoga.bot.BotMessages;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class LinkCommand implements BotCommand {
    @Override
    public String executeAndGetUserResponse(Message userMsg, AtmayogaBot bot) {
        return "Вот ссылка на наш список:\n" + BotMessages.LINK_TO_SHEET;
    }
}
