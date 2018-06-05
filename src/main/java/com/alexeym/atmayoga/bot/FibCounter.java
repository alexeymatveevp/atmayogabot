package com.alexeym.atmayoga.bot;

/**
 * Created by Alexey Matveev on 6/4/2018.
 */
public class FibCounter {

    static Integer visits = 0;
    static Integer previousFib = 1;
    static Integer currentFib = 2;

    static boolean visit() {
        visits++;
        if (visits.equals(currentFib)) {
            recalcFib();
            return true;
        }
        return false;
    }

    static void recalcFib() {
        int buf = currentFib;
        currentFib = currentFib + previousFib;
        previousFib = buf;
    }
}
