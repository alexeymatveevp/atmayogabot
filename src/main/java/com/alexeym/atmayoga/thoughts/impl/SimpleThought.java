package com.alexeym.atmayoga.thoughts.impl;

import com.alexeym.atmayoga.thoughts.TextThougthStep;
import com.alexeym.atmayoga.thoughts.Thought;
import com.alexeym.atmayoga.thoughts.ThoughtStep;
import com.alexeym.atmayoga.thoughts.ThoughtType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class SimpleThought extends Thought {

    private List<ThoughtStep> steps = new ArrayList<>();
    private ThoughtType type;

    public SimpleThought() {
    }

    public SimpleThought(String thought, ThoughtType type) {
        this.steps.add(new TextThougthStep(thought));
        this.type = type;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public List<ThoughtStep> getSteps() {
        return steps;
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
        return 0;
    }

    @Override
    public Date getDue() {
        return null;
    }

    @Override
    public ThoughtType getType() {
        return type;
    }
}
