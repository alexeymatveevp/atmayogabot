package com.alexeym.atmayoga;

import java.util.List;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public class YogaUtils {

    public static <T> T getRandomItem(List<T> list) {
        int size = list.size();
        int index = (int) Math.round(Math.random() * size);
        return list.get(index);
    }

    public static double rollTheDice() {
        return Math.random() * 100;
    }

    /**
     * Makes delay 10 work as 7, 8, 12 or 15.
     * @param delay
     * @return
     */
    public static int getDelayNaturalDeviation(int delay) {
        if (Math.random() > 0.5) {
            return (int) (delay - Math.random() * delay / 2);
        } else {
            return (int) (delay + Math.random() * delay / 2);
        }
    }

}
