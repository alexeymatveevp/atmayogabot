package com.alexeym.atmayoga.thoughts;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.bot.BotUtils;
import com.alexeym.atmayoga.checkvist.CheckvistService;
import com.alexeym.atmayoga.model.ThoughtMemory;
import com.alexeym.atmayoga.storage.CommonStorage;
import com.alexeym.atmayoga.thoughts.impl.CheckvistThought;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class ThoughtsSender {

    CheckvistService checkvistService = new CheckvistService();
    CommonStorage storage = new CommonStorage();

    // safety set which ensures the same task is not executed twice at the same time
    Set<Long> currentlyProcessingThoughtId = new HashSet<>();

    public void sendThought(Thought thought) throws Exception {
        if (thought != null) {
            System.out.println("picked thought: " + thought.getType() + " with prio: " + thought.getPriority());
            Long thoughtId = thought.getId();
            if (thoughtId == null || !currentlyProcessingThoughtId.contains(thoughtId)) {
                if (thoughtId != null) {
                    currentlyProcessingThoughtId.add(thoughtId);
                }
                List<ThoughtStep> steps = thought.getSteps();
                Iterator<ThoughtStep> iterator = steps.iterator();
                processNextStep(iterator, success -> {
                    try {
                        if (success) {
                            if (thought instanceof CheckvistThought) {
                                try {
                                    CheckvistThought checkvistThought = (CheckvistThought) thought;
                                    checkvistService.closeTask(checkvistThought.getChecklistId(), checkvistThought.getId());
                                } catch (Exception e) {
                                    System.out.println("Error closing checkvist task: " + thought);
                                    e.printStackTrace();
                                }
                            }
                        }
                    } finally {
                        if (thoughtId != null) {
                            currentlyProcessingThoughtId.remove(thoughtId);
                        }
                    }
                    return null;
                });
            }
            storage.upsert(new ThoughtMemory(new Date(), thought.getName(), thought.getType().name()));
        } else {
            System.out.println("Warning! No new thoughts found.");
        }
    }

    void processNextStep(Iterator<ThoughtStep> iterator, Function<Boolean, Void> successCallback) throws Exception {
        // TODO send to global chat for now, any last chat this bot was added to
        if (iterator.hasNext()) {
            ThoughtStep step = iterator.next();
            if (step instanceof TextThougthStep) {
                TextThougthStep textStep = (TextThougthStep) step;
                String text = textStep.getText();
                if (StringUtils.isNotEmpty(text)) {
                    GlobalContext.BOT.sendMsg(BotUtils.createTextMsg(GlobalContext.currentChatId, text));
                }
                processNextStep(iterator, successCallback);
            } else if (step instanceof DelayThoughtStep) {
                DelayThoughtStep delayThoughtStep = (DelayThoughtStep) step;
                int secondsDelay = delayThoughtStep.getSecondsDelay();
                GlobalContext.SCHEDULING_MANAGER.runTaskWithDelay(() -> {
                    try {
                        processNextStep(iterator, successCallback);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, secondsDelay, TimeUnit.SECONDS);
            } else if (step instanceof StickerThoughtStep) {
                StickerThoughtStep stickerStep = (StickerThoughtStep) step;
                String stickerId = stickerStep.getStickerId();
                if (StringUtils.isNotEmpty(stickerId)) {
                    GlobalContext.BOT.sendStkr(BotUtils.createStickerMsg(GlobalContext.currentChatId, stickerId));
                }
                processNextStep(iterator, successCallback);
            }
        } else {
            // it's done
            successCallback.apply(true);
        }
    }

}
