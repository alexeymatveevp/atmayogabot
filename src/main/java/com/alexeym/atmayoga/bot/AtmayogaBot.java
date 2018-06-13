package com.alexeym.atmayoga.bot;

import com.alexeym.atmayoga.YogaConstants;
import com.alexeym.atmayoga.bot.command.BotCommand;
import com.alexeym.atmayoga.bot.command.KotikCommand;
import com.alexeym.atmayoga.bot.command.LinkCommand;
import com.alexeym.atmayoga.bot.command.LastTrainingCommand;
import com.alexeym.atmayoga.bot.command.MyActivityCommand;
import com.alexeym.atmayoga.bot.command.StartCommand;
import com.alexeym.atmayoga.bot.command.TavrikCommand;
import com.alexeym.atmayoga.storage.UserStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
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

    ObjectMapper objectMapper = new ObjectMapper();

    UserMessageHandler userMessageHandler = new UserMessageHandler();
    GroupMessageHandler groupMessageHandler = new GroupMessageHandler();

    public void onUpdateReceived(Update update) {
        try {
            System.out.println(objectMapper.writeValueAsString(update));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            // handle messages from users
            if (message.isUserMessage()) {
                userMessageHandler.handleUserMessage(message);
            } else if (message.isGroupMessage()) {
                if (message.getSticker() != null) {
                    System.out.println(message.getSticker().getFileId());
                    System.out.println(message.getSticker().getSetName());
                }
                groupMessageHandler.handleGroupMessage(message);
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

    public void sendMsg(SendMessage msg) {
        try {
//            setButtons(msg);
            resetKeyboard(msg);
            sendMessage(msg);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void resetKeyboard(SendMessage msg) {
        msg.setReplyMarkup(new ReplyKeyboardRemove());
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
        keyboardFirstRow.add(new KeyboardButton("/start"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        KeyboardButton btn2 = new KeyboardButton("/kotik");
        keyboardSecondRow.add(btn2);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

//    private void setInline(SendMessage sendMessage) {
//        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
//        buttons1.add(new InlineKeyboardButton().setText("Hamster!!").setCallbackData(I_AM_A_HAMSTER));
//        buttons.add(buttons1);
//
//        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
//        markupKeyboard.setKeyboard(buttons);
//        sendMessage.setReplyMarkup(markupKeyboard);
//    }

    public String getBotUsername() {
        return "Atmayoga Bot";
    }

    public String getBotToken() {
        return YogaConstants.TELEGRAM_TOKEN;
    }
}
