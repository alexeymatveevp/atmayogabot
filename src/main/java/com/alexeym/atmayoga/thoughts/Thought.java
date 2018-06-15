package com.alexeym.atmayoga.thoughts;

import java.util.List;

/**
 * A thought is something bot is generated without user request.
 * Something that it "thinks of", or programmed to do.
 * I will use this term now for scheduled pre-defined responses.
 * Alternatives:
 * - random messages
 * - improvisation / impromptu
 * - ideas
 * - pre-defined reactions
 *
 * A thought may contain statistical output.
 * It's purpose is to provoke funny / interesting reaction of users, ideally - conversation / hype.
 *
 * @author Alexey Matveev on 15.06.2018
 */
public abstract class Thought {

    public abstract List<ThoughtVariant> getVariants();

    public abstract int getProbability();

    public abstract int getAppearPeriodDays();

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public abstract ThoughtType getType();

    /**
     * Override to make it unavailable.
     * @return
     */
    public boolean enabled() {
        return true;
    }

}
