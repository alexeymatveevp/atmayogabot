package com.alexeym.atmayoga.scheduler;

import com.alexeym.atmayoga.GlobalContext;
import com.alexeym.atmayoga.bot.BotUtils;
import com.alexeym.atmayoga.model.ThoughtMemory;
import com.alexeym.atmayoga.model.UserMetadata;
import com.alexeym.atmayoga.YogaConstants;
import com.alexeym.atmayoga.model.YogaUser;
import com.alexeym.atmayoga.storage.CommonStorage;
import com.alexeym.atmayoga.storage.UserStorage;
import com.alexeym.atmayoga.thoughts.Thought;
import com.alexeym.atmayoga.thoughts.ThoughtsProducer;
import com.alexeym.atmayoga.thoughts.ThoughtsSender;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexey Matveev on 09.06.2018
 */
public class ScheduledTasksManager {

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    CommonStorage storage = new CommonStorage();
    ThoughtsProducer thoughtsProducer = new ThoughtsProducer();
    ThoughtsSender thoughtsSender = new ThoughtsSender();

    public void start() {
        // periodically will check current date and time
        // there will be a list of date-times or rules which will trigger some action
        // action will be in other package, or be a bot command
        // should support 1-time tasks, like messages regarding app-updates / changelog
    }

    public void checkEndOfWeek() {
        // this should be started with periodic task scheduler
        // weekly broadcast should be very concise
        // for example some simple stats:
        // - how many people this week overall (+/- delta with previous week)
        // - who was all 4 times (list of first names)
    }

    public void setupSchedulerAndTasks() {
        // test thoughts
        Cron4j.scheduler.schedule(Cron4j.EVERY_MINUTE, () -> {
            Thought thought = thoughtsProducer.produceRandomThought();
            System.out.println("picked thought: " + thought.getName());
            try {
                thoughtsSender.sendThought(thought);
                storage.upsert(new ThoughtMemory(new Date(), thought.getName(), thought.getType().name()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Cron4j.scheduler.start();
    }

    public void runTaskWithDelay(Runnable task, int delay, TimeUnit timeUnit) {
        executor.schedule(task, delay, timeUnit);
    }

    /**
     * Uncomment this and add the message.
     * The message will be send once to every known user.
     * Delivery status will be stored in a special store.
     * Use unique key for update messages.
     */
    public void checkOneTimeBroadcastMessage() {
        System.out.println("Checking one-time messages...");
        // uncomment this if you want to send message to ALL users
        // beware this will reach and spam everyone
        String message = null;
        String thisUpdateId = null;
//        String message = "Привет Атмаёжик,\n" +
//                "--\n" +
//                "Хотел сказать тебе что теперь для списока команд видены подсказки если ткнуть в значек \"/\" снизу на твоей клавиатуре \n" +
//                "А также хотел рассказать тебе о пасхалке: /kotik";
//        String thisUpdateId = BroadcastMessages.CHANGELOG_09_06_2018;

        if (message != null) {
            System.out.println("There are new things to tell! Trying to send...");
            List<YogaUser> knownUsers = storage.findAll(YogaUser.class);
            boolean errorSend = false;
            int sendCount = 0;
            int failedCount = 0;
            for (YogaUser knownUser : knownUsers) {
                Integer userId = knownUser.getId();
                UserMetadata userMetadata = storage.findById(userId, UserMetadata.class);
                if (userMetadata == null) {
                    // create
                    userMetadata = new UserMetadata();
                    userMetadata.setUserId(userId);
                }
                // dont send the same thing twice
                if (!userMetadata.getReceivedUpdateMessages().contains(thisUpdateId)) {
                    try {
                        GlobalContext.BOT.sendMsg(BotUtils.createTextMsg(userId.longValue(), message));
                        System.out.println("Send for user: " + userId);
                        sendCount++;
                    } catch (Exception e) {
                        failedCount++;
                        System.out.println("Oops, smth went wrong! Retry maybe?");
                        e.printStackTrace();
                        if (!errorSend) {
                            errorSend = true;
                            GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(YogaConstants.ADMIN_USER_ID.longValue(), "Error broadcasting: " + e.getMessage()));
                        }
                    }
                    userMetadata.getReceivedUpdateMessages().add(thisUpdateId);
                }
                storage.upsert(userMetadata);
            }
            System.out.println("Send: " + sendCount + " / " + knownUsers.size());
            System.out.println("Failed: " + failedCount + " / " + knownUsers.size());
        }
    }

}
