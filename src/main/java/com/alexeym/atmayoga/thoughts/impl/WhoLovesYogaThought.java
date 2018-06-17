//package com.alexeym.atmayoga.thoughts.impl;
//
//import com.alexeym.atmayoga.StickerConstants;
//import com.alexeym.atmayoga.thoughts.Thought;
//import com.alexeym.atmayoga.thoughts.ThoughtType;
//import com.alexeym.atmayoga.thoughts.ThoughtVariant;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.alexeym.atmayoga.thoughts.ThoughtsMaker.makeDelayStep;
//import static com.alexeym.atmayoga.thoughts.ThoughtsMaker.makeSeq;
//import static com.alexeym.atmayoga.thoughts.ThoughtsMaker.makeStickerStep;
//import static com.alexeym.atmayoga.thoughts.ThoughtsMaker.makeTextStep;
//
///**
// * @author Alexey Matveev on 15.06.2018
// */
//public class WhoLovesYogaThought extends Thought {
//
//    private List<ThoughtVariant> variants = new ArrayList<>();
//
//    {
//        variants.add(makeSeq(
//                makeTextStep("кто любит йогу?"),
//                makeDelayStep(10),
//                makeTextStep("Я!")
//        ));
//        variants.add(makeSeq(
//                makeTextStep("кто любит йогу?"),
//                makeDelayStep(10),
//                makeStickerStep(StickerConstants.PUTINSTICKERS_PRESIDENTASANA),
//                makeDelayStep(5),
//                makeTextStep("президентасана (приподнятая на коленях ваджрасана)")
//        ));
//        variants.add(makeSeq(
//                makeTextStep("кто любит йогу?"),
//                makeDelayStep(10),
//                makeStickerStep(StickerConstants.PUHLYA_CUTE_ON_BACK),
//                makeDelayStep(20),
//                makeTextStep("(акхм...)")
//        ));
//    }
//
//    @Override
//    public List<ThoughtVariant> getVariants() {
//        return variants;
//    }
//
//    @Override
//    public int getProbability() {
//        return 80;
//    }
//
//    @Override
//    public int getAppearPeriodDays() {
//        return 60;
//    }
//
//    @Override
//    public int getPriority() {
//        return 0;
//    }
//
//    @Override
//    public ThoughtType getType() {
//        return ThoughtType.ROFL;
//    }
//}
