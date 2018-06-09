package com.alexeym.atmayoga.accuweather.forecast;

import java.util.Arrays;

/**
 * @author Alexey Matveev on 07.06.2018
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = new int[100_000_000];
        long s = System.currentTimeMillis();
        for (int i=0; i<arr.length; i++) {
            arr[i] = (int)Math.round(Math.random() * 100_000_000);
        }
        long e = System.currentTimeMillis();
        System.out.println(e-s);
        s = System.currentTimeMillis();
        Arrays.sort(arr);
        e = System.currentTimeMillis();
        System.out.println(e-s);
    }
}
