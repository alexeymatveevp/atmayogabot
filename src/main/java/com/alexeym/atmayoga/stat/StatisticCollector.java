package com.alexeym.atmayoga.stat;

import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.model.YogaMessage;
import com.alexeym.atmayoga.model.YogaUser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Presents raw data in statistical way.
 */
public class StatisticCollector {

    public LinkedHashMap<Integer, List<YogaUser>> getPresenceToUsers(List<PresenceItem> presenceItems) {
        Map<YogaUser, List<PresenceItem>> userToPresence = presenceItems.stream()
                .collect(Collectors.groupingBy(PresenceItem::getUser));

        Map<YogaUser, Integer> userToPresenceCount = userToPresence.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    List<PresenceItem> items = e.getValue();
                    return items == null ? 0 : items.size();
                }));

        Map<Integer, List<YogaUser>> presenceCountToUsers = new HashMap<>();
        for (Map.Entry<YogaUser, Integer> entry : userToPresenceCount.entrySet()) {
            Integer presenceCount = entry.getValue();
            List<YogaUser> yogaUsers = presenceCountToUsers.computeIfAbsent(presenceCount, k -> new ArrayList<>());
            yogaUsers.add(entry.getKey());
        }
        return presenceCountToUsers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<YogaUser, List<YogaMessage>> userToSortedByDateMessages(Map<Integer, YogaUser> usersMap, List<YogaMessage> messages) {
        Map<YogaUser, List<YogaMessage>> result = new HashMap<>();
        for (YogaMessage message : messages) {
            Integer userId = message.getUserId();
            YogaUser yogaUser = usersMap.get(userId);
            List<YogaMessage> userMessages = result.computeIfAbsent(yogaUser, k -> new ArrayList<>());
            userMessages.add(message);
        }
        // sort
        for (Map.Entry<YogaUser, List<YogaMessage>> entry : result.entrySet()) {
            List<YogaMessage> value = entry.getValue();
            value.sort(Comparator.comparing(YogaMessage::getDate));
        }
        return result;
    }

}
