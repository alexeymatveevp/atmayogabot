package com.alexeym.atmayoga.asanaguess;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.StickerConstants;
import com.alexeym.atmayoga.bot.BotUtils;
import com.alexeym.atmayoga.model.YogaMessage;
import com.alexeym.atmayoga.model.YogaUser;
import com.alexeym.atmayoga.storage.UserStorage;
import info.debatty.java.stringsimilarity.NGram;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexey Matveev on 18.06.2018
 */
public class AsanaGuessGame {

    int minutesTillGameEnd = 1;

    UserStorage userStorage = new UserStorage();

    Map<Long, AsanaGuess> gameStatePerChat = new HashMap<>();

//    FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());
//    CosineDistance cosineDistance = new CosineDistance();
//    HammingDistance hammingDistance = new HammingDistance();
    JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
//    NGram threeGram = new NGram(3);

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
        // run game timeout
        ScheduledFuture scheduledFuture = GlobalContext.SCHEDULING_MANAGER.runTaskWithDelay(() -> {
            UserGuess closestGuess = getClosestGuess(chatId);
            String text;
            Integer userId = closestGuess.getUserId();
            String correctAnswer = CORRECT_ANSWER_MAP.get(asanaSticker);
            if (userId != null) {
                YogaUser user = userStorage.getUser(userId);
                text = "Время вышло, правильный ответ был: " + correctAnswer + "\nБлиже всего к разгадке оказался " +
                        user.getFullName() + " с ответом " + closestGuess.getGuess() + "(" + closestGuess.getPercentage() + "% схожести)";
            } else {
                text = "Время вышло, правильный ответ был: " + correctAnswer;
            }
            GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, text));
            stopGameForChat(chatId);
        }, minutesTillGameEnd, TimeUnit.MINUTES);

        // store game details
        gameStatePerChat.put(chatId, new AsanaGuess(
                chatId, asanaSticker, LocalDateTime.now(), scheduledFuture
        ));
        return true;
    }

    public GuessResult guessMessage(Long chatId, Integer userId, String text) {
        AsanaGuess game = gameStatePerChat.get(chatId);
        if (game == null) {
            return null;
        }
        if (text == null) {
            return new GuessResult(false, 0);
        }
        String correctAnswer = CORRECT_ANSWER_MAP.get(game.getAsanaSticker());

        // remember the text
        game.addMessage(new YogaMessage(null, userId, chatId, new Date(), text, null));
        text = text.toLowerCase();

        // calculate similarity
//        int percentage = (int)(jaroWinklerDistance.apply(correctAnswer, text) * 100);
//        Double apply = cosineDistance.apply(correctAnswer, text);
//        Integer apply = fuzzyScore.fuzzyScore(correctAnswer, text);
//        System.out.println(apply);
//        int percentage = (int)(apply * 100);
        int percentage = calculateSimilarity(correctAnswer, text);
        return new GuessResult(percentage > 80, percentage);
    }

    private int calculateSimilarity(String correctAnswer, String guess) {
//        double distance = threeGram.distance(correctAnswer, guess);
//        int percentage = (int)((1 - distance) * 100);
        double distance = jaroWinklerDistance.apply(correctAnswer, guess);
        int percentage = (int)(distance * 100);
        return percentage;
    }

    public void stopGameForChat(Long chatId) {
        AsanaGuess asanaGuess = gameStatePerChat.get(chatId);
        if (asanaGuess != null) {
            ScheduledFuture gameTimeoutFuture = asanaGuess.getGameTimeoutFuture();
            gameTimeoutFuture.cancel(false);
            gameStatePerChat.remove(chatId);
        }
    }

    public boolean isGameStarted(Long chatId) {
        return gameStatePerChat.containsKey(chatId);
    }

    public boolean checkTimeExpired(Long chatId) {
        if (!isGameStarted(chatId)) return false;
        AsanaGuess asanaGuess = gameStatePerChat.get(chatId);
        LocalDateTime start = asanaGuess.getStart();
        return start.plusMinutes(minutesTillGameEnd).isBefore(LocalDateTime.now());
    }

    public UserGuess getClosestGuess(Long chatId) {
        if (!isGameStarted(chatId)) return null;
        AsanaGuess asanaGuess = gameStatePerChat.get(chatId);
        List<YogaMessage> messages = asanaGuess.getMessages();
        String correctAnswer = CORRECT_ANSWER_MAP.get(asanaGuess.getAsanaSticker());
        int maxPercentage = 0;
        Integer userId = null;
        String userGuess = null;
        for (YogaMessage message : messages) {
            String guess = message.getText();
            int percentage = calculateSimilarity(correctAnswer, guess);
            if (percentage > maxPercentage) {
                userId = message.getUserId();
                userGuess = guess;
                maxPercentage = percentage;
            }
        }
        return new UserGuess(userId, maxPercentage, userGuess);
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
