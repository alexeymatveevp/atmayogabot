package com.alexeym.atmayoga.bot.command;

import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class StartCommand implements BotCommand {
    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        // handle start command
        String firstName = userMsg.getFrom().getFirstName();
        return getStartCommandText(firstName);
    }

    public String getStartCommandText(String firstName) {
        String t = "Привет, Атмаёжик " + firstName + "! Вот что ты можешь сделать:\n\n";
        t += "таблица тренировок\n";
        t += BotCommand.TRAINING_LINK + " - получить ссылку на таблицу индивидуальных тренировок\n";
        t += BotCommand.TRAINING_LAST + " - посмотреть последнюю запись (когда ж это было)\n\n";
        t += "присутствие\n";
        t += BotCommand.PRESENCE_LAST + " - вспомнить когда ты был последний раз\n";
        t += BotCommand.PRESENCE_THIS_MONTH + " - посчитать сколько раз ты был за этот месяц\n\n";
        t += "статистика\n";
        t += BotCommand.STAT_TOP1_OVERALL + " - узнать кто топ-1 за все время (только статистически конечно)\n";
        t += BotCommand.STAT_MONTH_TOP1 + " - узнать кто топ-1 за этот месяц\n";
        t += BotCommand.STAT_MONTH_TOP3 + " - список топ-3 по посещениям за месяц\n";
        t += BotCommand.STAT_MY_ACTIVITY + " - узнать свою суммарную йога-активность\n\n";
        t += "другое\n";
        t += BotCommand.TAVRIK + " - узнать что там по таврику\n\n";
        t += BotCommand.ASANA_GUESS_GAME + " - игра \"угадай асану\"\n";
        t += "(+ ищи пасхалку)";
        return t;
    }

}
