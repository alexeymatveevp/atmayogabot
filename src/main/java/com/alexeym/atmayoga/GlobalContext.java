package com.alexeym.atmayoga;

import com.alexeym.atmayoga.bot.AtmayogaBot;

/**
 * My sweet global context... mmm.
 * Because I can.
 *
 * @author Alexey Matveev on 09.06.2018
 */
public class GlobalContext {

    public static AtmayogaBot BOT;
    public static ScheduledTasksManager SCHEDULING_MANAGER;
    public static Long currentChatId = YogaConstants.TRAINING_CHAT_ID;

}
