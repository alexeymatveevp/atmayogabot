package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.BotMessages;
import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.asanaguess.AsanaGuessGame;
import com.alexeym.atmayoga.bot.BotUtils;
import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class AsanaGuessCommand implements BotCommand {
    @Override
    public String executeAndGetUserResponse(Message userMsg) {
        AsanaGuessGame game = GlobalContext.GUESS_GAME;
        Long chatId = userMsg.getChatId();
        // ok start over again
        if (game.isGameStarted(chatId)) {
            game.stopGameForChat(chatId);
            GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, "Не вопрос, начнем сначала!"));
        }
        String randomAsana = game.produceRandomAsanaSticker();
        game.startGameForChat(randomAsana, chatId);
        try {
            GlobalContext.BOT.sendStkr(BotUtils.createStickerMsg(chatId, randomAsana));
        } catch (Exception e) {
            e.printStackTrace();
            game.stopGameForChat(chatId);
            GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, BotMessages.SMTH_WRONG_TRY_AGAIN));
        }
        return null;
    }
}
