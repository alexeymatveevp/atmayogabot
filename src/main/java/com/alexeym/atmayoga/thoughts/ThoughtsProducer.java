package com.alexeym.atmayoga.thoughts;

import com.alexeym.atmayoga.YogaUtils;
import com.alexeym.atmayoga.thoughts.impl.LoveYogaThought;
import com.alexeym.atmayoga.thoughts.impl.WhoLovesYogaThought;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class ThoughtsProducer {

    private List<Thought> THOUGHTS = new ArrayList<>();

    {
        THOUGHTS.add(new LoveYogaThought());
        THOUGHTS.add(new WhoLovesYogaThought());
    }

    public Thought produceMostAppropriateThought() {
        return null;
    }

    public Thought produceSuitableThought() {
        return null;
    }

    public Thought produceRandomThought() {
        return YogaUtils.getRandomItem(THOUGHTS);
    }

}
