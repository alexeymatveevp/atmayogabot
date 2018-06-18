package com.alexeym.atmayoga.asanaguess;

import com.alexeym.atmayoga.StickerConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.HammingDistance;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class AsanaGuessGame {

    Map<Long, AsanaGuess> gameStatePerChat = new HashMap<>();

//    FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());
//    CosineDistance cosineDistance = new CosineDistance();
//    HammingDistance hammingDistance = new HammingDistance();
    JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    Map<String, String> CORRECT_ANSWER_MAP = new HashMap<>();

    {
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_ARDHA_MADSYENDRASANA, "ардха матсиендрасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_BALASANA, "баласана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_BHADRASANA, "бхадрасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_BDUJANGASANA, "бхуджангасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_CHAKRASANA, "чакрасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_DHANURASANA, "дханурасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_EKA_PADA_RAJAKAPOTASANA, "эка пада раджакапотасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_HALASANA, "халасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_MATSYASANA, "матсьясана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_MAYURASANA, "маюрасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_PASHCHIMOTTANASANA, "пашчимоттанасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_PAVANAMUKTASANA, "паванамуктасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_SARVANGASANA, "сарвангасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_SHAVASANA, "шавасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_TRIKANASANA, "триконасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_USHTRASANA, "уштрасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_UTKATASANA, "уткатасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_UTTANASANA, "уттанасана");
        CORRECT_ANSWER_MAP.put(StickerConstants.GUESS_VAJRASANA, "ваджрасана");
    }

    public boolean startGameForChat(String asanaSticker, Long chatId) {
        if (gameStatePerChat.containsKey(chatId)) {
            return false;
        }
        gameStatePerChat.put(chatId, new AsanaGuess(
                chatId, asanaSticker, LocalDateTime.now()
        ));
        return true;
    }

    public GuessResult guessMessage(Long chatId, String text) {
        AsanaGuess game = gameStatePerChat.get(chatId);
        if (game == null) {
            return null;
        }
        if (text == null) {
            return new GuessResult(false, 0);
        }
        String correctAnswer = CORRECT_ANSWER_MAP.get(game.getAsanaSticker());
        text = text.toLowerCase();
        int percentage = (int)(jaroWinklerDistance.apply(correctAnswer, text) * 100);
//        Double apply = cosineDistance.apply(correctAnswer, text);
//        Integer apply = fuzzyScore.fuzzyScore(correctAnswer, text);
//        System.out.println(apply);
//        int percentage = (int)(apply * 100);
        return new GuessResult(percentage > 90, percentage);
    }

    public void stopGameForChat(Long chatId) {
        gameStatePerChat.remove(chatId);
    }

    public boolean isGameStarted(Long chatId) {
        return gameStatePerChat.containsKey(chatId);
    }

    public String getCorrectResultForChat(Long chatId) {
        AsanaGuess guess = gameStatePerChat.get(chatId);
        if (guess == null) {
            return null;
        }
        return CORRECT_ANSWER_MAP.get(guess.getAsanaSticker());
    }

    public String produceRandomAsanaSticker() {
        Set<String> allKeys = CORRECT_ANSWER_MAP.keySet();
        int random = (int) (Math.random() * allKeys.size());
        Iterator<String> iterator = allKeys.iterator();
        int idx = 0;
        while (iterator.hasNext()) {
            String stickerId = iterator.next();
            if (idx == random) return stickerId;
            idx++;
        }
        return null;
    }

}
