package com.alexeym.atmayoga.scheduler;

import it.sauronsoftware.cron4j.Scheduler;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class Cron4j {

    public static final String EVERY_MONTH = "0 10 L * *"; // top-3, top-1, all stats per month
    public static final String EVERY_HOUR_DAYTIME = "0 10-21 * * *"; // for thoughts checking, should check already produced thoughts (maybe de-schedule if there were some)
    public static final String EVERY_WEEK = "0 18 * * 7"; // weekly results
    public static final String EVERY_MINUTE = "* * * * *"; // just for testing

    public static Scheduler scheduler = new Scheduler();

}
