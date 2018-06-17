package com.alexeym.atmayoga.stat;

import com.alexeym.atmayoga.common.DurationUnit;
import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.model.YogaMessage;
import com.alexeym.atmayoga.model.YogaUser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prints different variations of statistics in human-readable format.
 */
public class StatisticPrinter {

    StatisticCollector statisticCollector = new StatisticCollector();

    public StatisticResult gatherTop3Presence(List<PresenceItem> presenceItems, DurationUnit durationUnit) {
        StatisticResult result = new StatisticResult();
        StringBuilder text = new StringBuilder();
        if (durationUnit == DurationUnit.WEEK) {
            text.append("Топ-3 за эту неделю (кол-во посещений):\n");
        } else if (durationUnit == DurationUnit.MONTH) {
            text.append("Топ-3 в этом месяце (кол-во посещений):\n");
        }
        // handle top-3
        // should be already sorted
        LinkedHashMap<Integer, List<YogaUser>> sortedTop3 = statisticCollector.getPresenceToUsers(presenceItems)
                .entrySet().stream()
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        int top = 1;
        for (Map.Entry<Integer, List<YogaUser>> entry : sortedTop3.entrySet()) {
            Integer visits = entry.getKey();
            List<YogaUser> users = entry.getValue();
            String usersString = PrintUtils.printListWithDelimiter(users, YogaUser::getFullName);
            text.append(top).append(". ").append(usersString).append(" - ").append(visits).append("\n");
            top++;
            if (users.size() > 3) result.setInterestRate(-1); // too much output
        }

        // interest rate modifiers
        if (durationUnit == DurationUnit.MONTH) result.setInterestRate(1); // top-3 is preferable for MONTH
        if (sortedTop3.size() < 3) result.setInterestRate(-1);

        result.setText(text.toString());
        return result;
    }

    public StatisticResult gatherMostPresentGuys(List<PresenceItem> presenceItems, DurationUnit durationUnit) {
        StatisticResult result = new StatisticResult();
        StringBuilder text = new StringBuilder();
        // get only 1 result
        Optional<Map.Entry<Integer, List<YogaUser>>> first = statisticCollector.getPresenceToUsers(presenceItems)
                .entrySet().stream()
                .findFirst();
        if (first.isPresent()) {
            Map.Entry<Integer, List<YogaUser>> entry = first.get();
            List<YogaUser> users = entry.getValue();
            Integer presence = entry.getKey();
            if (users.size() == 1) {
                YogaUser yogaUser = users.get(0);
                if (durationUnit == DurationUnit.WEEK) {
                    if (presence == 4) {
                        text.append(yogaUser.getFullName()).append(" на этой неделе единственный посетил все 4 занятия - так держать!");
                        result.setInterestRate(1); // 100%
                    } else {
                        text.append("На этой неделе больше всех посещал йогу ").append(yogaUser.getFullName());
                        text.append(" - кол-во занятий - ").append(presence);
                    }
                } else if (durationUnit == DurationUnit.MONTH) {
                    if (presence >= 16) {
                        text.append("Ого, ничего себе! ").append(yogaUser.getFullName());
                        text.append(" в этом месяце посетил все ").append(presence).append(" занятий!");
                        text.append(" Поздравляем!! Так держать) Вот это сила духа.");
                        result.setInterestRate(2); // wow! 100% MONTH
                    } else if (presence > 10) {
                        text.append("Не сидел без дела в этом месяце определенно ").append(yogaUser.getFullName());
                        text.append(" - аж ").append(presence).append(" посещений!");
                        result.setInterestRate(1); // a lot
                    } else {
                        text.append("Больше всех в этом месяце ходил ").append(yogaUser.getFullName());
                        text.append(" - ").append(presence).append(" занятий");
                    }
                }
            } else {
                if (durationUnit == DurationUnit.WEEK) {
                    text.append("На этой неделе несколько йогов были самыми активными посетителями: ");
                    String usersString = PrintUtils.printListWithDelimiter(users, YogaUser::getFullName);
                    text.append(usersString).append(" (с кол-вом посещений: ").append(presence).append(")");
                    if (presence == 4) {
                        result.setInterestRate(2); // 100%
                        text.append("\n(ударная получилась неделя!)");
                    }
                } else if (durationUnit == DurationUnit.MONTH) {
                    text.append("В этом месяце несколько йогов шли бок о бок! Вот они: ");
                    String usersString = PrintUtils.printListWithDelimiter(users, YogaUser::getFullName);
                    text.append(usersString).append("\nпосещений: ").append(presence).append("\nМолодцы!");
                    if (presence > 10) {
                        result.setInterestRate(1);
                    }
                }
            }
        } else {
            text.append("Хм.. кажется в этом месяце еще никто не посещал занятия (могу врать)");
            result.setInterestRate(0);
        }
        result.setText(text.toString());
        return result;
    }

    public StatisticResult gatherOverallVisits(List<PresenceItem> currentItems, List<PresenceItem> prevItems, DurationUnit durationUnit) {
        StatisticResult result = new StatisticResult();
        StringBuilder text = new StringBuilder();
        int prevVisits = prevItems.size();
        int curVisits = currentItems.size();
        if (durationUnit == DurationUnit.WEEK) {
            text.append("Суммарное кол-во посещений за эту неделю: ").append(curVisits);
            if (prevVisits != 0) {
                if (curVisits > prevVisits) {
                    text.append("\n(на ").append(curVisits - prevVisits).append(" больше чем на предыдущей)");
                } else if (curVisits < prevVisits) {
                    text.append("\n(на ").append(prevVisits - curVisits).append(" меньше чем на предыдущей)");
                }
            }
        } else if (durationUnit == DurationUnit.MONTH) {
            text.append("Эгей йожики, в этом месяце мы все вместе сходили на йогу ").append(curVisits).append(" раз");
            if (prevVisits != 0) {
                if (curVisits > prevVisits) {
                    text.append("\n(на ").append(curVisits - prevVisits).append(" больше чем в прошлом месяце!)");
                } else if (curVisits < prevVisits) {
                    text.append("\n(на ").append(prevVisits - curVisits).append(" меньше чем в прошлом)");
                }
            }
        }
        result.setText(text.toString());
        return result;
    }

    public String gatherTop3MostActiveChatUsers(Map<Integer, YogaUser> usersMap, List<YogaMessage> messages) {
        StringBuilder result = new StringBuilder();
        Map<YogaUser, List<YogaMessage>> userToMessages = statisticCollector.userToSortedByDateMessages(usersMap, messages);
        if (userToMessages == null || userToMessages.isEmpty()) {
            result.append("Да какая ж тут статистика - никто не писал за этот месяц :(");
            return result.toString();
        }
        Map<YogaUser, Integer> userToMessagesCount = userToMessages.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() == null ? 0 : e.getValue().size()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // sort more messages first
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        int top = 1;
        for (Map.Entry<YogaUser, Integer> entry : userToMessagesCount.entrySet()) {
            YogaUser user = entry.getKey();
            Integer messagesCount = entry.getValue();
            result.append(top).append(". ").append(user.getFullName()).append(" - ").append(messagesCount).append(" сообщений\n");
            top++;
        }
        return result.toString();
    }

}
