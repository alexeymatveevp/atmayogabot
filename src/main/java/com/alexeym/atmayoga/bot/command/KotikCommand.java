package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.bot.AtmayogaBot;
import com.alexeym.atmayoga.bot.BotUtils;
import com.alexeym.atmayoga.common.TimeTypeResolver;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class KotikCommand implements BotCommand {

    public static final String KOTIK_STICKER_1 = "CAADAgADbgEAAjbsGwVfHkWISq9DiQI";
    public static final String KOTIK_STICKER_2 = "CAADAgADCwEAAjbsGwWHI_EgLDceaQI";
    public static final String KOTIK_STICKER_3 = "CAADAgADcQEAAjbsGwXNjEIRlFWV3gI";

    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        // handle kotik command
        TimeTypeResolver.TimeType timeType = TimeTypeResolver.getCurrentTimeType();
        // sticker easter egg
        int sticker = (int) Math.round(Math.random() * 50);
        String stickerId = null;
        if (sticker == 1) stickerId = KOTIK_STICKER_1;
        if (sticker == 2) stickerId = KOTIK_STICKER_2;
        if (sticker == 3) stickerId = KOTIK_STICKER_3;
        if (stickerId != null) {
            SendSticker stickerMsg = BotUtils.createStickerMsg(userMsg.getChatId(), stickerId);
            try {
                GlobalContext.BOT.sendSticker(stickerMsg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return null;
        }
        // standard
        String response;
        if (timeType == TimeTypeResolver.TimeType.MORNING) {
            response = "мурмурмур";
        } else if (timeType == TimeTypeResolver.TimeType.DAY) {
            long t = Math.round(Math.random() * 5);
            if (t == 0) {
                response = "^_^";
            } else if (t == 1) {
                response = "O_O";
            } else if (t == 2) {
                response = "фффф!";
            } else if (t == 3) {
                response = "кусь";
            } else {
                response = "цмок *=";
            }
        } else if (timeType == TimeTypeResolver.TimeType.EVENING) {
            response = "ну что котик?";
        } else {
            response = "спать иди, завтра много дел";
        }
        return response;
    }
}
