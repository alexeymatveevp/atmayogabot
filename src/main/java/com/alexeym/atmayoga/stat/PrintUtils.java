package com.alexeym.atmayoga.stat;

import java.util.Iterator;
import java.util.List;

public class PrintUtils {

    public static final String DEFAULT_DELIMITER = ", ";

    public static <T> String printList(List<T> list) {
        return printListWithDelimiter(list, Object::toString);
    }

    public static <T> String printListWithDelimiter(List<T> list, Printable<T> toStringFn) {
        return printListWithDelimiter(list, toStringFn, DEFAULT_DELIMITER);
    }

    public static <T> String printListWithDelimiter(List<T> list, Printable<T> toStringFn, String delimiter) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder("");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T item = it.next();
            String str = toStringFn.toString(item);
            result.append(str);
            if (it.hasNext()) {
                result.append(delimiter);
            }
        }
        return result.toString();
    }

}
