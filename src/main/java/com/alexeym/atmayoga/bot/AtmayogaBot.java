package com.alexeym.atmayoga.bot;

import com.alexeym.atmayoga.bot.command.BotCommand;
import com.alexeym.atmayoga.bot.command.KotikCommand;
import com.alexeym.atmayoga.bot.command.LinkCommand;
import com.alexeym.atmayoga.bot.command.LastTrainingCommand;
import com.alexeym.atmayoga.bot.command.MyActivityCommand;
import com.alexeym.atmayoga.bot.command.StartCommand;
import com.alexeym.atmayoga.bot.command.TavrikCommand;
import com.alexeym.atmayoga.storage.UserStorage;
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

    UserStorage userStorage = new UserStorage();

    StartCommand startCommand = new StartCommand();
    LastTrainingCommand lastTrainingCommand = new LastTrainingCommand();
    LinkCommand linkCommand = new LinkCommand();
    KotikCommand kotikCommand = new KotikCommand();
    TavrikCommand tavrikCommand = new TavrikCommand();
    MyActivityCommand myActivityCommand = new MyActivityCommand();

    public static final String I_AM_A_HAMSTER = "I am a hamster...";

    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasMessage()) {
            Message userMsg = update.getMessage();
            Long chatId = userMsg.getChatId();
            String text = userMsg.getText();
            User user = userMsg.getFrom();

            // just ping storage that another user is here
            userStorage.anotherUserCame(user, userMsg);

            // handle text command
            if (text != null) {
                // make bot command
                BotCommand command = null;
                if (text.equals(BotCommand.START)) command = startCommand;
                else if (text.equals(BotCommand.TRAINING_LINK)) command = linkCommand;
                else if (text.equals(BotCommand.TRAINING_LAST)) command = lastTrainingCommand;
                else if (text.equals(BotCommand.TAVRIK)) command = tavrikCommand;
                else if (text.equals(BotCommand.ACTIVITY)) command = myActivityCommand;
                else if (text.equals(BotCommand.KOTIK)) command = kotikCommand;

                // execute it and send response
                if (command != null) {
                    String responseToUser = command.executeAndGetUserResponse(userMsg);
                    if (responseToUser != null) {
                        sendMsg(BotUtils.createTextMsg(chatId, responseToUser));
                    }
                }
            }



            // handle

//            boolean reached = FibCounter.visit();
//            if (reached) {
//                sendMsg(createMsg(chatId, "Number of overall messages is: " + FibCounter.visits));
//            }

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
