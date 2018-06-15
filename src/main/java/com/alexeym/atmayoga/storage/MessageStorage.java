package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.model.YogaMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.stickers.Sticker;

import java.util.Date;
import java.util.List;

public class MessageStorage {

    public void addUserMessage(Message message) {
        Sticker sticker = message.getSticker();
        YogaMessage yogaMessage = new YogaMessage(
                message.getMessageId(),
                message.getFrom().getId(),
                message.getChatId(),
                new Date(message.getDate()),
                message.getText(),
                sticker == null ? null : sticker.getFileId()
        );
        upsertMessage(yogaMessage);
    }

    public void upsertMessage(YogaMessage message) {
        JsonDBInit.jsonDBTemplate.upsert(message);
    }

    public List<YogaMessage> getAllMessages() {
        // TODO make a short-living cache
        return JsonDBInit.jsonDBTemplate.findAll(YogaMessage.class);
    }

}
