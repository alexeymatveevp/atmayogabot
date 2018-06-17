package com.alexeym.atmayoga;

import com.alexeym.atmayoga.checkvist.CheckvistThoughtsCollector;
import com.alexeym.atmayoga.common.DurationUnit;
import com.alexeym.atmayoga.google.SheetDataService;
import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.stat.StatisticPrinter;
import com.alexeym.atmayoga.stat.StatisticResult;
import com.alexeym.atmayoga.thoughts.Thought;
import com.alexeym.atmayoga.thoughts.ThoughtType;
import com.alexeym.atmayoga.thoughts.ThoughtsSender;
import com.alexeym.atmayoga.thoughts.impl.SimpleThought;
import org.apache.commons.collections.CollectionUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class ScheduledThoughtsProducer {

    CheckvistThoughtsCollector checkvistThoughtsCollector = new CheckvistThoughtsCollector();
    ThoughtsSender thoughtsSender = new ThoughtsSender();
    SheetDataService sheetDataService = new SheetDataService();
    StatisticPrinter statisticPrinter = new StatisticPrinter();

    public void periodicalThoughtsCheck() {
        try {

            List<Thought> thoughts = checkvistThoughtsCollector.getThoughts();

            // find priority thoughts
            List<Thought> priorityThoughts = thoughts.stream()
                    .filter(t -> t.getPriority() == 1)
                    .collect(Collectors.toList());

            // find due
            List<Thought> thoughtsForToday = thoughts.stream()
                    .filter(t -> t.getDue() != null)
                    .filter(t -> t.getDue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(LocalDate.now()))
                    .collect(Collectors.toList());

            System.out.println(priorityThoughts);
            System.out.println(thoughtsForToday);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void periodicalThoughtProduceSuitable() {
        // make this one smart
        // consider thought type frequency
        // consider previous thoughts history.. in other way
        // mix with statistical static thoughts
        try {
            List<Thought> thoughts = checkvistThoughtsCollector.getThoughts();
            // consider date
            if (CollectionUtils.isNotEmpty(thoughts)) {
                Thought thought = YogaUtils.getRandomItem(thoughts);
                thoughtsSender.sendThought(thought);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void periodicalWeeklyThoughts() {
        // stats about how many people was this week, who is top-1 by the number of visits
        try {
            List<PresenceItem> items = sheetDataService.getPresenceItems(YogaUtils.getCurrentMonthAsRusString());
            List<PresenceItem> weekItems = items.stream()
                    .filter(item -> YogaUtils.thisWeekPredicate(item.getDate()))
                    .collect(Collectors.toList());
            List<PresenceItem> prevWeekItems;
            if (YogaUtils.fistWeekOfMonthPassed()) {
                // prev week can be found in the same sheet
                prevWeekItems = items.stream()
                        .filter(item -> YogaUtils.prevWeekPredicate(item.getDate()))
                        .collect(Collectors.toList());
            } else {
                // call prev. month sheet
                List<PresenceItem> itemsPrevMonth = sheetDataService.getPresenceItems(YogaUtils.getPrevMonthAsRusString());
                prevWeekItems = itemsPrevMonth.stream()
                        .filter(item -> YogaUtils.prevWeekPredicate(item.getDate()))
                        .collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(items)) {
                StatisticResult overall = statisticPrinter.gatherOverallVisits(weekItems, prevWeekItems, DurationUnit.WEEK);
                StatisticResult top1 = statisticPrinter.gatherMostPresentGuys(weekItems, DurationUnit.WEEK);
                StatisticResult top3 = statisticPrinter.gatherTop3Presence(weekItems, DurationUnit.WEEK);
                // cmp interest rate
                Map<Integer, List<StatisticResult>> rate2Stat = Arrays.asList(overall, top1, top3).stream()
                        .collect(Collectors.groupingBy(StatisticResult::getInterestRate));
                Map.Entry<Integer, List<StatisticResult>> maxEntry = rate2Stat.entrySet().stream()
                        .max(Map.Entry.comparingByKey())
                        .get();
                List<StatisticResult> stats = maxEntry.getValue();
                StatisticResult statToPrint;
                if (stats.size() == 1) {
                    statToPrint = stats.get(0);
                    System.out.println("Single stat with ir=" + maxEntry.getKey());
                } else {
                    statToPrint = stats.get((int) (Math.random() * stats.size()));
                    System.out.println("Multiple stats with interest rate: stats=" + stats.size() + " ir=" + maxEntry.getKey());
                }
                thoughtsSender.sendThought(new SimpleThought(statToPrint.getText(), ThoughtType.STAT));
            } else {
                thoughtsSender.sendThought(new SimpleThought("Нет статистики за эту неделю (не накопилась)", ThoughtType.STAT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Thought produceRandomThought() throws Exception {
        List<Thought> thoughts = checkvistThoughtsCollector.getThoughts();
        // consider date
        if (CollectionUtils.isNotEmpty(thoughts)) {
            return YogaUtils.getRandomItem(thoughts);
        }
        return null;
    }

}
