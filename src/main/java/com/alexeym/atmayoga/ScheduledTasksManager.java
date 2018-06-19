package com.alexeym.atmayoga;

import com.alexeym.atmayoga.scheduler.Cron4j;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexey Matveev on 09.06.2018
 */
public class ScheduledTasksManager {

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    ScheduledThoughtsProducer scheduledThoughtsProducer = new ScheduledThoughtsProducer();

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

    public void initAlgorithm() {
        // repeatedly check prio and due tasks
        executor.scheduleAtFixedRate(scheduledThoughtsProducer::periodicalThoughtsCheck, 1, 5,TimeUnit.MINUTES);
//        executor.scheduleAtFixedRate(scheduledThoughtsProducer::periodicalThoughtsCheck, 2, 10,TimeUnit.SECONDS);

        // every day perform random thought dice roll
        // also roll for random time: 10am - 9pm
        Cron4j.scheduler.schedule(Cron4j.EVERY_DAY_AT_10_AM, () -> {
            int produceThoughtDice = (int) (Math.random() * 3); // 66% yes, 33% no
            if (produceThoughtDice < 2) {
                int timeShift = (int) (Math.random() * 12); // 0-11
                executor.schedule(scheduledThoughtsProducer::periodicalThoughtProduceSuitable, timeShift, TimeUnit.HOURS);
            } else {
                System.out.println("Thought not produced today because of the dice: " + produceThoughtDice);
            }
        });
//        Cron4j.scheduler.schedule(Cron4j.EVERY_MINUTE, () -> {
//                executor.schedule(scheduledThoughtsProducer::periodicalThoughtProduceSuitable, 0, TimeUnit.SECONDS);
//        });

        // every week perform a statistical overview
        // should be not very long and with a lot of variations
        Cron4j.scheduler.schedule(Cron4j.EVERY_WEEK_SUNDAY_18_PM, () -> {
//        Cron4j.scheduler.schedule(Cron4j.EVERY_MINUTE, () -> {
            executor.schedule(scheduledThoughtsProducer::periodicalWeeklyThoughts, 0, TimeUnit.SECONDS);
        });

        // start the scheduler
        Cron4j.scheduler.start();
    }

    public ScheduledFuture runTaskWithDelay(Runnable task, int delay, TimeUnit timeUnit) {
        return executor.schedule(task, delay, timeUnit);
    }

    /**
     * Uncomment this and add the message.
     * The message will be send once to every known user.
     * Delivery status will be stored in a special store.
     * Use unique key for update messages.
     */
//    public void checkOneTimeBroadcastMessage() {
//        System.out.println("Checking one-time messages...");
//        // uncomment this if you want to send message to ALL users
//        // beware this will reach and spam everyone
//        String message = null;
//        String thisUpdateId = null;
////        String message = "Привет Атмаёжик,\n" +
////                "--\n" +
////                "Хотел сказать тебе что теперь для списока команд видены подсказки если ткнуть в значек \"/\" снизу на твоей клавиатуре \n" +
////                "А также хотел рассказать тебе о пасхалке: /kotik";
////        String thisUpdateId = BroadcastMessages.CHANGELOG_09_06_2018;
//
//        if (message != null) {
//            System.out.println("There are new things to tell! Trying to send...");
//            List<YogaUser> knownUsers = storage.findAll(YogaUser.class);
//            boolean errorSend = false;
//            int sendCount = 0;
//            int failedCount = 0;
//            for (YogaUser knownUser : knownUsers) {
//                Integer userId = knownUser.getId();
//                UserMetadata userMetadata = storage.findById(userId, UserMetadata.class);
//                if (userMetadata == null) {
//                    // create
//                    userMetadata = new UserMetadata();
//                    userMetadata.setUserId(userId);
//                }
//                // dont send the same thing twice
//                if (!userMetadata.getReceivedUpdateMessages().contains(thisUpdateId)) {
//                    try {
//                        GlobalContext.BOT.sendMsg(BotUtils.createTextMsg(userId.longValue(), message));
//                        System.out.println("Send for user: " + userId);
//                        sendCount++;
//                    } catch (Exception e) {
//                        failedCount++;
//                        System.out.println("Oops, smth went wrong! Retry maybe?");
//                        e.printStackTrace();
//                        if (!errorSend) {
//                            errorSend = true;
//                            GlobalContext.BOT.sendMsgErrorless(BotUtils.createTextMsg(YogaConstants.ADMIN_USER_ID.longValue(), "Error broadcasting: " + e.getMessage()));
//                        }
//                    }
//                    userMetadata.getReceivedUpdateMessages().add(thisUpdateId);
//                }
//                storage.upsert(userMetadata);
//            }
//            System.out.println("Send: " + sendCount + " / " + knownUsers.size());
//            System.out.println("Failed: " + failedCount + " / " + knownUsers.size());
//        }
//    }

}
