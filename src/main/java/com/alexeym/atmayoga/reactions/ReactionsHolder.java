package com.alexeym.atmayoga.reactions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public class ReactionsHolder {

    private List<Reaction> REACTIONS_LIST = new ArrayList<>();

    public ReactionsHolder() {
        REACTIONS_LIST.add(new YaZaReaction());
    }

    public List<Reaction> getAvailabelReactions() {
        return REACTIONS_LIST;
    }

}
