package com.alexeym.atmayoga.thoughts;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.bot.BotUtils;
import com.alexeym.atmayoga.scheduler.ScheduledTasksManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class ThoughtsSender {

    public void sendThought(Thought thought) throws Exception {
        List<ThoughtVariant> variants = thought.getVariants();
        ThoughtVariant variant = YogaUtils.getRandomItem(variants);
        // TODO send to global chat for now, any last chat this bot was added to
        if (variant instanceof TextThoughtVariant) {
            TextThoughtVariant textThoughtVariant = (TextThoughtVariant) variant;
            String text = textThoughtVariant.getText();
            if (StringUtils.isNotEmpty(text)) {
                GlobalContext.BOT.sendMsg(BotUtils.createTextMsg(GlobalContext.currentChatId, text));
            }
        } else if (variant instanceof StickerThoughtVariant) {
            StickerThoughtVariant stickerThoughtVariant = (StickerThoughtVariant) variant;
            String stickerId = stickerThoughtVariant.getStickerId();
            if (StringUtils.isNotEmpty(stickerId)) {
                GlobalContext.BOT.sendSticker(BotUtils.createStickerMsg(GlobalContext.currentChatId, stickerId));
            }
        } else if (variant instanceof SequenceThoughtVariant) {
            SequenceThoughtVariant sequenceThoughtVariant = (SequenceThoughtVariant) variant;
            List<ThoughtStep> steps = sequenceThoughtVariant.getThoughtSteps();
            Iterator<ThoughtStep> iterator = steps.iterator();
            processNextStep(iterator);
        }
    }

    void processNextStep(Iterator<ThoughtStep> iterator) throws Exception {
        if (iterator.hasNext()) {
            ThoughtStep step = iterator.next();
            if (step instanceof SendTextThougthStep) {
                SendTextThougthStep textStep = (SendTextThougthStep) step;
                String text = textStep.getText();
                if (StringUtils.isNotEmpty(text)) {
                    GlobalContext.BOT.sendMsg(BotUtils.createTextMsg(GlobalContext.currentChatId, text));
                }
                processNextStep(iterator);
            } else if (step instanceof DelayThoughtStep) {
                DelayThoughtStep delayThoughtStep = (DelayThoughtStep) step;
                int secondsDelay = delayThoughtStep.getSecondsDelay();
                GlobalContext.SCHEDULING_MANAGER.runTaskWithDelay(() -> {
                    try {
                        processNextStep(iterator);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, secondsDelay, TimeUnit.SECONDS);
                Thread.sleep(secondsDelay);
            } else if (step instanceof SendStickerThoughtStep) {
                SendStickerThoughtStep stickerStep = (SendStickerThoughtStep) step;
                String stickerId = stickerStep.getStickerId();
                if (StringUtils.isNotEmpty(stickerId)) {
                    GlobalContext.BOT.sendStkr(BotUtils.createStickerMsg(GlobalContext.currentChatId, stickerId));
                }
                processNextStep(iterator);
            }
        }
    }

}
