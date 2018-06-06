package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.AtmayogaBot;
import com.alexeym.atmayoga.bot.BotMessages;
import com.alexeym.atmayoga.common.PrettyPrinter;
import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.google.SheetQueryService;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class ListCommand implements BotCommand {

    SheetQueryService sheetQueryService = new SheetQueryService();

    @Override
    public String executeAndGetUserResponse(Message userMsg, AtmayogaBot bot) {
        // handle list command
        User user = userMsg.getFrom();
        try {
            List<YogaUser> allUsers = sheetQueryService.getAllUsers();
            for (YogaUser yogaUser : allUsers) {
                // try to merge with existing user
                if (user.getFirstName().equals(yogaUser.getFirstName()) && user.getLastName().equals(yogaUser.getLastName())) {
                    return PrettyPrinter.prettyUserPractice(yogaUser);
                }
            }
            return "Хм.. может тебя нет в нашем списке? Добавляйся!\n" + BotMessages.LINK_TO_SHEET;
        } catch (Exception e) {
            e.printStackTrace();
            return BotMessages.SMTH_WRONG;
        }
    }
}
