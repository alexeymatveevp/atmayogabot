package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.bot.BotMessages;
import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.common.YogaUserTrainingItem;
import com.alexeym.atmayoga.google.SheetDataProvider;
import com.alexeym.atmayoga.google.YogaUserMatcher;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class LastTrainingCommand implements BotCommand {

    public static final Locale LOCALE_RU = new Locale("ru");

    SheetDataProvider sheetDataProvider = new SheetDataProvider();
    YogaUserMatcher yogaUserMatcher = new YogaUserMatcher();

    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        // handle list command
        User user = userMsg.getFrom();
        try {
            YogaUser yogaUser = yogaUserMatcher.tryGetExistingYogaUser(user);
            if (yogaUser == null) {
                yogaUser = new YogaUser(null, user.getFirstName(), user.getLastName());
            }
            YogaUserTrainingItem userLastActivity = sheetDataProvider.getUserLastActivity(yogaUser);
            return interpretResults(yogaUser, userLastActivity);
        } catch (Exception e) {
            e.printStackTrace();
            return BotMessages.SMTH_WRONG;
        }
    }

    public String interpretResults(YogaUser yogaUser, YogaUserTrainingItem userLastActivity) {
        if (userLastActivity == null) {
            return "Хм.. может тебя нет в нашем списке? Добавляйся!\n" + BotMessages.LINK_TO_SHEET;
        }
        String text = "Последняя добавленная активность";

        Date date = userLastActivity.getDate();
        if (date != null) {
            String dateText = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(LOCALE_RU).withZone(ZoneId.systemDefault()).format(date.toInstant());
            text += " добавлена " + dateText;
        }
        text += ":\n"
                + "\"" + userLastActivity.getNote() + "\"\n"
                + "(из категории \"" + userLastActivity.getActivityCategory() + "\")";
        return text;
    }

}
