package com.alexeym.atmayoga.stat;

import com.alexeym.atmayoga.common.PresenceItem;
import com.alexeym.atmayoga.common.YogaMessage;
import com.alexeym.atmayoga.common.YogaUser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prints different variations of statistics in human-readable format.
 */
public class StatisticPrinter {

    StatisticCollector statisticCollector = new StatisticCollector();

    public String gatherTop3Presence(List<PresenceItem> presenceItems) {
        StringBuilder result = new StringBuilder("Топ-3 по посещаемости в этом месяце:\n");
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
            result.append(top).append(". ").append(usersString).append(" - ").append(visits).append(" занятий\n");
            top++;
        }
        return result.toString();
    }

    public String gatherMostPresentGuys(List<PresenceItem> presenceItems) {
        StringBuilder result = new StringBuilder();
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
                result.append("В этом месяце самым активным пока является ").append(yogaUser.getFullName());
                if (presence > 10) {
                    result.append(" - аж ").append(presence).append(" занятий!");
                } else {
                    result.append(" - ").append(presence).append(" занятий");
                }
            } else {
                result.append("Первое место по кол-ву посещений делят несколько йогов, вот они: ");
                String usersString = PrintUtils.printListWithDelimiter(users, YogaUser::getFullName);
                result.append(usersString).append(" с кол-вом посещений - ").append(presence);
            }
        } else {
            result.append("Хм.. кажется в этом месяце еще никто не посещал занятия (могу врать)");
        }
        return result.toString();
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
