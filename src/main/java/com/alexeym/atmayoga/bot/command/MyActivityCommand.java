package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.storage.UserStorage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class MyActivityCommand implements BotCommand {

    UserStorage userStorage = new UserStorage();

    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        YogaUser yogaUser = userStorage.getUser(userMsg.getFrom().getId());
        int messagesNum = yogaUser.getNumberOfMessagesThisWeek();
        if (messagesNum < 5) {
            // too small
            return "Новенький";
        } else if (messagesNum < 10) {
            return "Поддерживаешь разговор" + "(" + messagesNum + " сообщений на этой неделе)";
        } else if (messagesNum < 15) {
            return "Так держать!" + "(" + messagesNum + " сообщений на этой неделе)";
        } else if (messagesNum < 25) {
            return "Завсегдатай йог в чате" + "(" + messagesNum + " сообщений на этой неделе)";
        } else if (messagesNum < 50) {
            return "Активный участник обсуждений - видимо есть что рассказать" + "(" + messagesNum + " сообщений на этой неделе)";
        } else if (messagesNum < 100) {
            return "Оочень активный участник, половина сообщений от тебя!" + "(" + messagesNum + " сообщений на этой неделе)";
        } else {
            return "Ты тут живешь? Перестаю считать... " + "(" + messagesNum + " сообщений на этой неделе)";
        }
    }
}
