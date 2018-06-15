package com.alexeym.atmayoga.reactions;

import java.util.List;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public abstract class Reaction {

    public abstract boolean matches(ReactionContext context);
    public abstract List<String> getReactionVariants();
    public abstract void processManually();
    public abstract ReactionType getType();
    public abstract int delay();
    public abstract int baseProbability();

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public enum ReactionType {
        MANUAL, AUTO
    }
}
