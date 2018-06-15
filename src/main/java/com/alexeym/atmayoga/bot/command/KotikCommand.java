package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.StickerConstants;
import com.alexeym.atmayoga.bot.BotUtils;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class KotikCommand implements BotCommand {

    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        // handle kotik command
        TimeTypeResolver.TimeType timeType = TimeTypeResolver.getCurrentTimeType();
        // sticker easter egg
        int sticker = (int) (Math.random() * 100);
        String stickerId = null;
        if (sticker == 1) stickerId = StickerConstants.CATCUS_CMOK;
        if (sticker == 2) stickerId = StickerConstants.CATCUS_GO_AWAY;
        if (sticker == 3) stickerId = StickerConstants.CATCUS_HELLO;
        if (sticker == 4) stickerId = StickerConstants.CATCUS_KUS;
        if (sticker == 5) stickerId = StickerConstants.CATCUS_TUT;
        if (sticker == 6) stickerId = StickerConstants.ANIMALS_CUTE_CAT;
        if (sticker == 7) stickerId = StickerConstants.ANIMALS_FUNNY_CAT;
        if (sticker == 8) stickerId = StickerConstants.ANIMALS_GRUMPY_CAT;
        if (sticker == 9) stickerId = StickerConstants.ANIMALS_OOOOOH_CAT;
        if (sticker == 10) stickerId = StickerConstants.ANIMALS_PIDRILA_CAT;
        if (sticker == 11) stickerId = StickerConstants.PUSHEENJJ_KOTIK1;
        if (sticker == 12) stickerId = StickerConstants.PUSHEENJJ_KOTIK2_CUP;
        if (sticker == 13) stickerId = StickerConstants.PUSHEENJJ_KOTIK_ANGRY_YOGA;
        if (sticker == 14) stickerId = StickerConstants.PUSHEENJJ_KOTIK_SERENE_YOGA;
        if (sticker == 15) stickerId = StickerConstants.KAMIKAZECAT_YOGA;
        if (stickerId != null) {
            SendSticker stickerMsg = BotUtils.createStickerMsg(userMsg.getChatId(), stickerId);
            try {
                GlobalContext.BOT.sendStkr(stickerMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        // standard
        String response;
        if (timeType == TimeTypeResolver.TimeType.NIGHT) {
            response = "спать иди, никаких котиков";
        } else {
            long t = (int) (Math.random() * 11);
            if (t == 0) response = "^_^";
            else if (t == 1) response = "O_O";
            else if (t == 2) response = "фффф!";
            else if (t == 3) response = "кусь";
            else if (t == 4) response = "ну что котик?";
            else if (t == 5) response = "мурр, мурр.. ффф!";
            else if (t == 6) response = "мурррр.. ррмрр";
            else if (t == 7) response = "я тебе не котик";
            else if (t == 8) response = "поза кошки очень полезна для спины";
            else if (t == 9) response = "атань";
            else response = "цмок *=";
        }
        return response;
    }
}
