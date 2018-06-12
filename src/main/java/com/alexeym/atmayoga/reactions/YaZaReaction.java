package com.alexeym.atmayoga.reactions;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Matveev on 6/10/2018.
 */
public class YaZaReaction implements Reaction {

    public static final String YA_ZA = "я за";

    List<String> variants = new ArrayList<>();

    {
        variants.add("и я за)");
        variants.add("я тоже");
        variants.add("+1");
        variants.add("а я пас");
        variants.add("развлекайтесь");
    }

    @Override
    public boolean matches(ReactionContext context) {
        String text = context.getText();
        if (text != null) {
            if (StringUtils.containsIgnoreCase(text, YA_ZA)) {
                int idx = StringUtils.indexOfIgnoreCase(text, YA_ZA);
                int indexAfterYaZa = idx + YA_ZA.length();
                if (text.length() == indexAfterYaZa) {
                    // end of msg - ok
                    return true;
                }
                if (text.length() > indexAfterYaZa) {
                    // check next symbol is not a part of a word
                    boolean alphanumeric = StringUtils.isAlphanumeric(text.substring(indexAfterYaZa, indexAfterYaZa + 1));
                    return !alphanumeric;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> getReactionVariants() {
        return variants;
    }

    @Override
    public void processManually() {

    }

    @Override
    public ReactionType getType() {
        return ReactionType.AUTO;
    }

    @Override
    public int delay() {
        return 5;
    }

    @Override
    public int baseProbability() {
        return 50;
    }

}
