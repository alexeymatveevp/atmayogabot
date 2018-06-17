package com.alexeym.atmayoga.checkvist;

import com.alexeym.atmayoga.checkvist.model.Task;
import org.junit.Test;

import java.util.List;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class TestCheckvist {

    @Test
    public void testCheckvist() throws Exception {
        CheckvistService service = new CheckvistService();
        List<Task> tasks = service.getTasks(CheckvistService.AMTAYOGA_NEWS_LIST_ID);
        System.out.println(tasks);
    }

}
