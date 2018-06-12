package com.alexeym.atmayoga.google;

import com.alexeym.atmayoga.common.PresenceItem;
import com.alexeym.atmayoga.common.TrainingItem;
import com.alexeym.atmayoga.common.YogaUser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class SheetDataServiceTest {

    @Test
    public void testTrainingSheet() throws Exception {
        SheetDataService service = new SheetDataService();
        TrainingItem userLastActivity = service.getUserLastActivity(new YogaUser(null, "Евгений", "Улитин"));
        assertNotNull(userLastActivity);
    }

    @Test
    public void testPresenceSheet() throws Exception {
        SheetDataService service = new SheetDataService();
        List<PresenceItem> presenceItems = service.getPresenceItems();
        assertNotNull(presenceItems);
    }
}
