package com.alexeym.atmayoga.reactions;

import java.util.List;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public interface Reaction {
    boolean matches(ReactionContext context);
    List<String> getReactionVariants();
    void processManually();
    ReactionType getType();
    int delay();
    int baseProbability();

    enum ReactionType {
        MANUAL, AUTO
    }
}
