package com.alexeym.atmayoga;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Matveev on 6/4/2018.
 */
public class AtmayogaBot extends TelegramLongPollingBot {

    public static final String I_AM_A_HAMSTER = "I am a hamster...";

    public void onUpdateReceived(Update update) {
        System.out.println("Update received");
        System.out.println(update);
        if (update.hasMessage()) {
            Message userMsg = update.getMessage();
            Long chatId = userMsg.getChatId();
            String text = userMsg.getText();
            if (text.equalsIgnoreCase("выбор")) {
                SendMessage msg = createMsg(chatId, "Yay");
                setButtons(msg);
                sendMsg(msg);
            } else if (text.equalsIgnoreCase("хомяк")) {
                SendMessage msg = createMsg(chatId, "кто тут хомяк?");
                setInline(msg);
                sendMsg(msg);
            } else {
                sendMsg(createMsg(chatId, "(Not implemented)"));
            }

            boolean reached = FibCounter.visit();
            if (reached) {
                sendMsg(createMsg(chatId, "Number of overall messages is: " + FibCounter.visits));
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (callbackQuery != null) {
                Integer chatId = callbackQuery.getFrom().getId();
                AnswerCallbackQuery acq = new AnswerCallbackQuery();
                acq.setCallbackQueryId(callbackQuery.getId());
                acq.setText("Pussy hamster ^_^");
                acq.setShowAlert(true);
                try {
                    answerCallbackQuery(acq);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    SendMessage createMsg(Long chatId, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        return s;
    }

    private void sendMsg(SendMessage msg) {
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(msg);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Привет"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void setInline(SendMessage sendMessage) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("Hamster!!").setCallbackData(I_AM_A_HAMSTER));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        sendMessage.setReplyMarkup(markupKeyboard);
    }

    public String getBotUsername() {
        return "Atmayoga Bot";
    }

    public String getBotToken() {
        return "557292258:AAHbWOz4HW3Q8UMQq3VxPkf1qv7v3UB518c";
    }
}
