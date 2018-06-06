package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class StartCommand implements BotCommand {
    @Override
    public String executeAndGetUserResponse(Message userMsg, AtmayogaBot bot) {
        // handle start command
        String t = "Привет, Атмаёжик " + userMsg.getFrom().getFirstName() + "!\n\n";
        t+="Вот что ты можешь сделать:\n\n";
        t+=LINK + " - получить ссылку на нашу таблицу индивидуальных занятий\n\n";
        t+=LIST + "/list - получить сводку по твоим занятиям (если записываешься в таблицу)\n\n";
        t+=TAVRIK + "/tavrik - узнать что там по таврику\n\n";
        t+=MY_ACTIVITY + "/myactivity - узнать свою активность в чате";
        return t;
    }

}
