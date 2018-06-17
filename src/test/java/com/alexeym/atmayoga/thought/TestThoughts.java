package com.alexeym.atmayoga.thought;

import com.alexeym.atmayoga.ScheduledThoughtsProducer;
import org.junit.Test;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class TestThoughts {

    @Test
    public void testWeeklyThoughts() throws Exception {
        ScheduledThoughtsProducer producer = new ScheduledThoughtsProducer();
        producer.periodicalWeeklyThoughts();
    }
}
