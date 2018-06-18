package com.alexeym.atmayoga.guessgame;

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
        AsanaGuessGame game = new AsanaGuessGame();
        Long chat = 123L;
        String stickerId = StickerConstants.GUESS_BALASANA;
        System.out.println("Balasana");
        boolean started = game.startGameForChat(stickerId, chat);
        if (started) {
            System.out.println(game.guessMessage(chat, "баласана"));
            System.out.println(game.guessMessage(chat, "баласа"));
            System.out.println(game.guessMessage(chat, "халасана"));
            System.out.println(game.guessMessage(chat, "шавасана"));
            System.out.println(game.guessMessage(chat, "улыбасана"));
            System.out.println(game.guessMessage(chat, "бхуджангасана"));
            game.stopGameForChat(chat);
        }
        System.out.println("Sarvangasana");
        stickerId = StickerConstants.GUESS_SARVANGASANA;
        started = game.startGameForChat(stickerId, chat);
        if (started) {
            System.out.println(game.guessMessage(chat, "шарвангасана"));
            System.out.println(game.guessMessage(chat, "сарвонгасана"));
            System.out.println(game.guessMessage(chat, "сорвангасана"));
        }
    }
}
