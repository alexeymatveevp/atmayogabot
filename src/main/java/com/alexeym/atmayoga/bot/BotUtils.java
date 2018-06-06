package com.alexeym.atmayoga.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class BotUtils {

    public static SendMessage createTextMsg(Long chatId, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(text);
        return s;
    }

    public static SendSticker createStickerMsg(Long chatId, String stickerId) {
        SendSticker s = new SendSticker();
        s.setChatId(chatId);
        s.setSticker(stickerId);
        return s;
    }

}
