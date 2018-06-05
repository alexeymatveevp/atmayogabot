package com.alexeym.atmayoga.bot;

import com.alexeym.atmayoga.common.PrettyPrinter;
import com.alexeym.atmayoga.common.TimeTypeResolver;
import com.alexeym.atmayoga.common.YogaUser;
import com.alexeym.atmayoga.common.YogaUserStorage;
import com.alexeym.atmayoga.google.SheetQueryService;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
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

    private static final String LINK_TO_SHEET = "https://docs.google.com/spreadsheets/d/1b7kQqBXbqohJ7hSI546eQu0MMOL1fg3vW5KldtzcvSA/edit#gid=0";

    SheetQueryService sheetQueryService = new SheetQueryService();

    public static final String I_AM_A_HAMSTER = "I am a hamster...";

    public void onUpdateReceived(Update update) {
        System.out.println("Update received");
        System.out.println(update);
        if (update.hasMessage()) {
            Message userMsg = update.getMessage();
            Long chatId = userMsg.getChatId();
            String text = userMsg.getText();
            List<MessageEntity> entities = userMsg.getEntities();
            User user = userMsg.getFrom();

            if (entities != null) {
                MessageEntity messageEntity = entities.get(0);
                if (messageEntity.getType().equals("bot_command") && text.equals("/start")) {
                    // handle start command
                    // remember new user or check existing
                    YogaUser yogaUser = YogaUserStorage.usersMap.get(user.getId());
                    if (yogaUser == null) {
                        yogaUser = new YogaUser(user.getId(), user.getFirstName(), user.getLastName());
                        YogaUserStorage.usersMap.put(user.getId(), yogaUser);
                    }
                    String t = "Привет, новый Атмаёжик! :)\n\n";
                    t+="Вот что ты можешь сделать:\n\n";
                    t+="/link - получить ссылку на таблицу занятий\n\n";
                    t+="/list - получить сводку по твоим занятиям";
                    sendMsg(createMsg(chatId, t));
                } else if (messageEntity.getType().equals("bot_command") && text.equals("/list")) {
                    // handle list command
                    try {
                        List<YogaUser> allUsers = sheetQueryService.getAllUsers();
                        for (YogaUser yogaUser : allUsers) {
                            // try to merge with existing user
                            if (user.getFirstName().equals(yogaUser.getFirstName()) && user.getLastName().equals(yogaUser.getLastName())) {
                                YogaUser storedYogaUser = YogaUserStorage.usersMap.get(user.getId());
                                if (storedYogaUser == null) {
                                    storedYogaUser = yogaUser;
                                    YogaUserStorage.usersMap.put(user.getId(), storedYogaUser);
                                }
                                storedYogaUser.setId(user.getId());
                                storedYogaUser.setPracticeMap(yogaUser.getPracticeMap());
                                String responseMsg = PrettyPrinter.prettyUserPractice(yogaUser);
                                sendMsg(createMsg(chatId, responseMsg));
                                return;
                            }
                        }
                        sendMsg(createMsg(chatId, "Хм.. может тебя нет в нашем списке? Добавляйся!\n" + LINK_TO_SHEET));
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        sendMsg(createMsg(chatId, "Хм.. что-то пошло не так"));
                        return;
                    }
                } else if (messageEntity.getType().equals("bot_command") && text.equals("/link")) {
                    // handle link command
                    sendMsg(createMsg(chatId, "Вот ссылка на наш список:\n" + LINK_TO_SHEET));
                    return;
                } else if (messageEntity.getType().equals("bot_command") && text.equals("/kotik")) {
                    // handle kotik command
                    TimeTypeResolver.TimeType timeType = TimeTypeResolver.getCurrentTimeType();
                    String response;
                    if (timeType == TimeTypeResolver.TimeType.MORNING) {
                        response = "мурмурмур";
                    } else if (timeType == TimeTypeResolver.TimeType.DAY) {
                        long t = Math.round(Math.random() * 5);
                        if (t == 0) {
                            response = "^_^";
                        } else if (t == 1) {
                            response = "O_O";
                        } else if (t == 2) {
                            response = "фффф!";
                        } else if (t == 3) {
                            response = "кусь";
                        } else {
                            response = "цмок *=";
                        }
                    } else if (timeType == TimeTypeResolver.TimeType.EVENING) {
                        response = "ну что котик?";
                    } else {
                        response = "спать иди, завтра много дел";
                    }
                    sendMsg(createMsg(chatId, response));
                    return;
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
