package com.alexeym.atmayoga.thoughts;

import java.util.List;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class SequenceThoughtVariant extends ThoughtVariant {

    private List<ThoughtStep> thoughtSteps;

    public SequenceThoughtVariant() {
    }

    public SequenceThoughtVariant(List<ThoughtStep> thoughtSteps) {
        this.thoughtSteps = thoughtSteps;
    }

    public List<ThoughtStep> getThoughtSteps() {
        return thoughtSteps;
    }

    public void setThoughtSteps(List<ThoughtStep> thoughtSteps) {
        this.thoughtSteps = thoughtSteps;
    }
}
