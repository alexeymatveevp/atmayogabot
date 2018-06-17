package com.alexeym.atmayoga.bot;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.YogaConstants;
import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.model.Chat;
import com.alexeym.atmayoga.model.ReactionMemory;
import com.alexeym.atmayoga.reactions.Reaction;
import com.alexeym.atmayoga.reactions.ReactionContext;
import com.alexeym.atmayoga.reactions.ReactionsHolder;
import com.alexeym.atmayoga.ScheduledTasksManager;
import com.alexeym.atmayoga.storage.ChatStorage;
import com.alexeym.atmayoga.storage.CommonStorage;
import com.alexeym.atmayoga.storage.MessageStorage;
import org.apache.commons.collections.CollectionUtils;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexey Matveev on 6/9/2018.
 */
public class GroupMessageHandler {

    private boolean someoneTriedCommand = false;

    ReactionsHolder reactionsHolder = new ReactionsHolder();
    ScheduledTasksManager scheduledTasksManager = new ScheduledTasksManager();
    ChatStorage chatStorage = new ChatStorage();
    MessageStorage messageStorage = new MessageStorage();
    CommonStorage commonStorage = new CommonStorage();

    public void handleGroupMessage(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();
        User user = message.getFrom();


        // TODO for now bot will respond to the last chat which will write to it
        GlobalContext.currentChatId = chatId;

        // remember all chat messages
        messageStorage.addUserMessage(message);

        // bot added first time or new yoga users
        List<User> newChatMembers = message.getNewChatMembers();
        if (!CollectionUtils.isEmpty(newChatMembers)) {
            // welcome
            if (newChatMembers.size() == 1 && Boolean.TRUE.equals(newChatMembers.get(0).getBot()) && YogaConstants.BOT_NAME.equals(newChatMembers.get(0).getUserName())) {
                // remember chat
                Chat storedChat = chatStorage.findById(chatId);
                if (storedChat == null) {
                    storedChat = new Chat(chatId, message.getChat().getTitle());
                    chatStorage.upsertChat(storedChat);
                }
                // welcome
                String wellcomeText1 = "Всем привет) я новый бот Amtayoga версии 1.0";
                scheduledTasksManager.runTaskWithDelay(() -> {
                    GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, wellcomeText1));
                }, 5, TimeUnit.SECONDS);
                String wellcomeText2 = "Я буду участвовать в нашей группе и стараться помогать чем смогу! Надеюсь на поддержку и понимание)";
                scheduledTasksManager.runTaskWithDelay(() -> {
                    GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, wellcomeText2));
                }, 15, TimeUnit.SECONDS);
                String wellcomeText3 = "(спасибо за внимание)";
                scheduledTasksManager.runTaskWithDelay(() -> {
                    GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, wellcomeText3));
                }, 30, TimeUnit.SECONDS);
            } else {
                // welcome new users

            }
        }

        // command protection
        if (text != null && text.startsWith("/") && !someoneTriedCommand) {
            scheduledTasksManager.runTaskWithDelay(() -> {
                GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, "Команды я принимаю только в личке (чтобы не спамить), хотя потом может будут и групповые команды, если придумаем какие были бы полезны"));
            }, 5, TimeUnit.SECONDS);
            scheduledTasksManager.runTaskWithDelay(() -> {
                GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(chatId, "(кстати если есть идеи - пишите мне - обязательно прочту)"));
            }, 10, TimeUnit.SECONDS);
            someoneTriedCommand = true;
        }

        // REACTIONS
        ReactionContext reactionContext = new ReactionContext(text, message);
        List<Reaction> reactions = reactionsHolder.getAvailabelReactions();
        for (Reaction reaction : reactions) {
            if (reaction.matches(reactionContext)) {
                // ok try to process reaction
                if (reaction.getType() == Reaction.ReactionType.AUTO) {
                    // process automatic reaction: calculate probability dice
                    double rollTheDice = YogaUtils.rollTheDice();
                    if (rollTheDice < reaction.baseProbability()) {
                        // get one random invariant
                        List<String> reactionVariants = reaction.getReactionVariants();
                        String reactionResponse = YogaUtils.getRandomItem(reactionVariants);
                        // run with delay; make delay more natural
                        int delay = YogaUtils.getDelayNaturalDeviation(reaction.delay());
                        scheduledTasksManager.runTaskWithDelay(() -> {
                            try {
                                GlobalContext.BOT.sendMsg(BotUtils.createTextMsg(chatId, reactionResponse));
                                commonStorage.upsert(new ReactionMemory(new Date(), reaction.getName(), text, reactionResponse));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, delay, TimeUnit.SECONDS);
                        System.out.println("Reaction " + reaction.getClass().getSimpleName() + " for message \"" + text + "\" triggered; dice=" + rollTheDice + " delay=" + delay + " reaction=" + reactionResponse);
                    } else {
                        System.out.println("Reaction " + reaction.getClass().getSimpleName() + " for message \"" + text + "\" not triggered because of dice: " + rollTheDice);
                    }
                } else if (reaction.getType() == Reaction.ReactionType.MANUAL) {
                    // full control to reaction class
                    reaction.processManually();
                }
            }
        }

    }

}
