package com.alexeym.atmayoga.bot.command;

import com.alexeym.atmayoga.BotMessages;
import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.asanaguess.AsanaGuessGame;
import com.alexeym.atmayoga.asanaguess.GuessResult;
import com.alexeym.atmayoga.bot.BotUtils;
import com.alexeym.atmayoga.model.YogaUser;
import org.telegram.telegrambots.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class AsanaGuessCommand implements BotCommand {

    List<String> WRONG_ANSWER_RESPONSE = new ArrayList<>();

    {
        WRONG_ANSWER_RESPONSE.add("хм... нет");
        WRONG_ANSWER_RESPONSE.add("нет)");
        WRONG_ANSWER_RESPONSE.add("неа");
        WRONG_ANSWER_RESPONSE.add("пробуй еще");
        WRONG_ANSWER_RESPONSE.add("все фигня давай по новой");
        WRONG_ANSWER_RESPONSE.add("ух, нет");
        WRONG_ANSWER_RESPONSE.add("даже не близко");
        WRONG_ANSWER_RESPONSE.add("не угадал");
        WRONG_ANSWER_RESPONSE.add("ты наугад пишешь? попробуй еще)");
    }

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

    public void handleUserGuess(Long chatId, YogaUser user, String text) {
        AsanaGuessGame game = GlobalContext.GUESS_GAME;
        if (game.isGameStarted(chatId)) {
            GuessResult guessResult = game.guessMessage(chatId, user.getId(), text); // user id and chat id are the same
            if (guessResult.isCorrect()) {
                if (guessResult.getMatchPercentage() == 100) {
                    GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, "Да 100% верно!)"));
                    game.stopGameForChat(chatId);
                } else {
                    GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, "Почти, верно на " + guessResult.getMatchPercentage() + " процентов, " +
                            "правильный ответ: " + game.getCorrectResultForChat(chatId)));
                    game.stopGameForChat(chatId);
                }
            } else {
                String randomResponse = YogaUtils.getRandomItem(WRONG_ANSWER_RESPONSE);
                GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, randomResponse));
            }
        }
    }
}
