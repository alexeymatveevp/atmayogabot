package com.alexeym.atmayoga.stat;

import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.common.DurationUnit;
import com.alexeym.atmayoga.model.PresenceItem;
import com.alexeym.atmayoga.google.SheetDataService;
import org.junit.Test;

import java.util.List;

public class StatisticPrinterTest {

    SheetDataService sheetDataService = new SheetDataService();
    StatisticPrinter statisticPrinter = new StatisticPrinter();

    @Test
    public void testGatherTop3Presence() throws Exception {
        List<PresenceItem> presenceItems = sheetDataService.getPresenceItemsForCurrentMonth();
        StatisticResult result = statisticPrinter.gatherTop3Presence(presenceItems, DurationUnit.WEEK);
        System.out.println(result.getInterestRate());
        System.out.println(result.getText());
    }

    @Test
    public void testGatherMostPresentGuys() throws Exception {
        List<PresenceItem> presenceItems = sheetDataService.getPresenceItems(YogaUtils.getCurrentMonthAsRusString());
        StatisticResult result = statisticPrinter.gatherMostPresentGuys(presenceItems, DurationUnit.WEEK);
        System.out.println(result.getInterestRate());
        System.out.println(result.getText());
    }

}
