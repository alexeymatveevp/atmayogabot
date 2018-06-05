package com.alexeym.atmayoga.common;

import java.util.Map;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class PrettyPrinter {

    public static String prettyUserPractice(YogaUser yogaUser) {
        StringBuilder result = new StringBuilder("Вы занимались:\n");
        Map<String, Practice> practiceMap = yogaUser.getPracticeMap();
        if (practiceMap != null && !practiceMap.isEmpty()) {
            for (Map.Entry<String, Practice> entry : practiceMap.entrySet()) {
                String date = entry.getKey();
                Practice practice = entry.getValue();
                String individual = practice.getIndividual();
                String physical = practice.getPhysical();
                // if any practice
                if ((individual != null && !individual.equals("")) || (physical != null && !physical.equals(""))) {
                    result.append(date).append(" -");
                }
                if (individual != null && !individual.equals("")) {
                    result.append(" йога: ").append(individual);
                    if (physical != null && !physical.equals("")) {
                        result.append(" +");
                    }
                }
                if (physical != null && !physical.equals("")) {
                    result.append(" физ.активность: ").append(physical);
                }
                result.append("\n");
            }
        } else {
            result.append("(нет записей)");
        }
        return result.toString();
    }

}
