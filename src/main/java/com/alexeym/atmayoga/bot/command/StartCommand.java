package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class StartCommand implements BotCommand {
    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        // handle start command
        String t = "Привет, Атмаёжик " + userMsg.getFrom().getFirstName() + "!\n\n";
        t+="Вот что ты можешь сделать:\n\n";
        t+= TRAINING_LINK + " - получить ссылку на нашу таблицу индивидуальных занятий\n\n";
        t+= TRAINING_LAST + " - получить сводку по твоим занятиям (если записываешься в таблицу)\n\n";
        t+=TAVRIK + " - узнать что там по таврику\n\n";
        t+= ACTIVITY + " - узнать свою активность в чате";
        return t;
    }

}
