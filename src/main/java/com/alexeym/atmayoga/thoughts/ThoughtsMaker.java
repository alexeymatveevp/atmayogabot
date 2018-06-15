package com.alexeym.atmayoga.thoughts;

import java.util.Arrays;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class ThoughtsMaker {

    public static ThoughtVariant makeText(String text) {
        return new TextThoughtVariant(text);
    }

    public static ThoughtVariant makeSticker(String stickerId) {
        return new StickerThoughtVariant(stickerId);
    }

    public static ThoughtVariant makeSeq(ThoughtStep... actions) {
        return new SequenceThoughtVariant(Arrays.asList(actions));
    }

    public static ThoughtStep makeTextStep(String text) {
        return new SendTextThougthStep(text);
    }

    public static ThoughtStep makeDelayStep(int seconds) {
        return new DelayThoughtStep(seconds);
    }

    public static ThoughtStep makeStickerStep(String stickerId) {
        return new SendStickerThoughtStep(stickerId);
    }

}
