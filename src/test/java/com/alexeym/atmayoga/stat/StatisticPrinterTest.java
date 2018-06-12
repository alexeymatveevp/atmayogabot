package com.alexeym.atmayoga.stat;

import com.alexeym.atmayoga.common.PresenceItem;
import com.alexeym.atmayoga.google.SheetDataService;
import org.junit.Test;

import java.util.List;

public class StatisticPrinterTest {

    SheetDataService sheetDataService = new SheetDataService();
    StatisticPrinter statisticPrinter = new StatisticPrinter();

    @Test
    public void testGatherTop3Presence() throws Exception {
        List<PresenceItem> presenceItems = sheetDataService.getPresenceItems();
        String print = statisticPrinter.gatherTop3Presence(presenceItems);
        System.out.println(print);
    }

    @Test
    public void testGatherMostPresentGuys() throws Exception {
        List<PresenceItem> presenceItems = sheetDataService.getPresenceItems("Май");
        String print = statisticPrinter.gatherMostPresentGuys(presenceItems);
        System.out.println(print);
    }

}
