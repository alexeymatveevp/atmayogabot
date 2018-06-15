package com.alexeym.atmayoga.thoughts.impl;

import com.alexeym.atmayoga.StickerConstants;
import com.alexeym.atmayoga.thoughts.Thought;
import com.alexeym.atmayoga.thoughts.ThoughtType;
import com.alexeym.atmayoga.thoughts.ThoughtVariant;

import java.util.ArrayList;
import java.util.List;

import static com.alexeym.atmayoga.thoughts.ThoughtsMaker.*;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class LoveYogaThought extends Thought {

    private List<ThoughtVariant> variants = new ArrayList<>();

    {
        variants.add(makeText("люблю йогу)"));
        variants.add(makeText("люблю йогу, особенно по утрам!"));
        variants.add(makeText("без йоги я бы не существовал (люблю йогу)"));
        variants.add(makeText("практикую йогу, значит - существую!"));
        variants.add(makeSticker(StickerConstants.PUSHEENJJ_KOTIK_SERENE_YOGA));
    }

    @Override
    public List<ThoughtVariant> getVariants() {
        return variants;
    }

    @Override
    public int getProbability() {
        return 60;
    }

    @Override
    public int getAppearPeriodDays() {
        return 45;
    }

    @Override
    public ThoughtType getType() {
        return ThoughtType.SHORT_ROFL;
    }
}
