package com.alexeym.atmayoga.thoughts.impl;

import com.alexeym.atmayoga.thoughts.Thought;
import com.alexeym.atmayoga.thoughts.ThoughtStep;
import com.alexeym.atmayoga.thoughts.ThoughtType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class CheckvistThought extends Thought {

    private Long id;
    private Long checklistId;
    private String name; // any custom indicator for logging
    private List<ThoughtStep> steps;
    private int priority;
    private Date due;
    private ThoughtType type;

    public CheckvistThought() {
    }

    public CheckvistThought(Long id, Long checklistId, int priority, Date due, ThoughtType type) {
        this.id = id;
        this.checklistId = checklistId;
        this.priority = priority;
        this.due = due;
        this.type = type;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public List<ThoughtStep> getSteps() {
        return steps;
    }

    public void addStep(ThoughtStep step) {
        if (steps == null) {
            steps = new ArrayList<>();
        }
        steps.add(step);
    }

    @Override
    public int getProbability() {
        return 100;
    }

    @Override
    public int getAppearPeriodDays() {
        return 0;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Date getDue() {
        return due;
    }

    @Override
    public String toString() {
        if (steps == null) {
            return getName() + " " + type + " " + priority;
        } else {
            StringBuilder result = new StringBuilder();
            result.append(getName()).append(" ");
            for (ThoughtStep step : steps) {
                result.append(step.toString()).append(" ");
            }
            result.append(type).append(" ").append(priority);
            return result.toString();
        }
    }

    @Override
    public ThoughtType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChecklistId() {
        return checklistId;
    }

    @Override
    public String getName() {
        return name;
    }
}
