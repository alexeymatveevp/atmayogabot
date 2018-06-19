package com.alexeym.atmayoga.guessgame;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.ScheduledTasksManager;
import com.alexeym.atmayoga.StickerConstants;
import com.alexeym.atmayoga.asanaguess.AsanaGuessGame;
import org.junit.Test;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class TestGuessGame {

    @Test
    public void testRandomSticker() throws Exception {
        AsanaGuessGame game = new AsanaGuessGame();
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
        System.out.println(game.produceRandomAsanaSticker());
    }

    @Test
    public void testGame() throws Exception {
        GlobalContext.SCHEDULING_MANAGER = new ScheduledTasksManager();
        AsanaGuessGame game = new AsanaGuessGame();
        Long chat = 123L;
        String stickerId = StickerConstants.GUESS_BALASANA;
        System.out.println("Balasana");
        boolean started = game.startGameForChat(stickerId, chat);
        if (started) {
            System.out.println(game.guessMessage(chat, 1, "баласана"));
            System.out.println(game.guessMessage(chat, 1, "баласа"));
            System.out.println(game.guessMessage(chat, 1, "халасана"));
            System.out.println(game.guessMessage(chat, 1,"шавасана"));
            System.out.println(game.guessMessage(chat, 1,"улыбасана"));
            System.out.println(game.guessMessage(chat, 1,"бхуджангасана"));
            System.out.println(game.guessMessage(chat, 1,"гг"));
            game.stopGameForChat(chat);
        }
        System.out.println("Sarvangasana");
        stickerId = StickerConstants.GUESS_SARVANGASANA;
        started = game.startGameForChat(stickerId, chat);
        if (started) {
            System.out.println(game.guessMessage(chat, 1,"шарвангасана"));
            System.out.println(game.guessMessage(chat, 1,"сарвонгасана"));
            System.out.println(game.guessMessage(chat, 1,"сорвангасана"));
        }
    }
}
