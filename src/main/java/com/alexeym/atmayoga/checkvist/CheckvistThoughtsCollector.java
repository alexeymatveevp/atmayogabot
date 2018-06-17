package com.alexeym.atmayoga.checkvist;

import com.alexeym.atmayoga.checkvist.model.Task;
import com.alexeym.atmayoga.checkvist.model.TaskColor;
import com.alexeym.atmayoga.thoughts.*;
import com.alexeym.atmayoga.thoughts.impl.CheckvistThought;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class CheckvistThoughtsCollector {

    public static final String PRIORITY_COLOR = "red";
    public static final String STICKER_COLOR = "blue";
    public static final String DELAY_BG_COLOR = "red";

    CheckvistService checkvistService = new CheckvistService();

    public List<Thought> getPriorityThoughts() throws Exception {
        List<Thought> thoughts = getThoughts();
        return thoughts.stream()
                .filter(t -> t.getPriority() == 1)
                .collect(Collectors.toList());
    }

    public List<Thought> getThoughts() throws Exception {
        // TODO hacky way to set the tag - need to read tag from checklist automatically
        List<Task> newsTasks = checkvistService.getTasks(CheckvistService.AMTAYOGA_NEWS_LIST_ID);
        newsTasks.forEach(t -> t.setTagsAsText("NEWS"));
        List<Task> roflTasks = checkvistService.getTasks(CheckvistService.AMTAYOGA_SPONTANEOUS_LIST_ID);
        roflTasks.forEach(t -> t.setTagsAsText("ROFL"));

        return newsTasks.stream()
                .filter(t -> t.getStatus() == 0)
                .map(this::map2Thought)
                .collect(Collectors.toList());
    }

    boolean priorityPredicate(Task task) {
        TaskColor color = task.getColor();
        return color != null && color.getText() != null && color.getText().equals(PRIORITY_COLOR);
    }

    boolean stickerPredicate(Task task) {
        TaskColor color = task.getColor();
        return color != null && color.getText() != null && color.getText().equals(STICKER_COLOR);
    }

    boolean delayPredicate(Task task) {
        TaskColor color = task.getColor();
        return color != null && color.getBackground() != null && color.getBackground().equals(DELAY_BG_COLOR);
    }

    int color2Priotiry(TaskColor color) {
        return (color != null && color.getText() != null && color.getText().equals(PRIORITY_COLOR)) ? 1 : 0;
    }

    ThoughtType tag2ThoughtType(String tag) {
        if (tag != null) {
            for (ThoughtType tt : ThoughtType.values()) {
                if (tt.name().equalsIgnoreCase(tag)) {
                    return tt;
                }
            }
        }
        return null;
    }

    Thought map2Thought(Task task) {
        CheckvistThought thought = new CheckvistThought(
                task.getId(),
                task.getChecklistId(),
                color2Priotiry(task.getColor()),
                task.getDue(),
                tag2ThoughtType(task.getTagsAsText())
        );
        List<Task> subTasks = task.getTasks();
        if (CollectionUtils.isNotEmpty(subTasks)) {
            for (Task subTask : subTasks) {
                thought.addStep(map2ThoughtStep(subTask));
            }
        } else {
            // single step
            thought.addStep(map2ThoughtStep(task));
        }
        return thought;
    }

    ThoughtStep map2ThoughtStep(Task task) {
        TaskColor color = task.getColor();
        if (color != null) {
            if (stickerPredicate(task)) {
                return new StickerThoughtStep(task.getContent());
            }
            if (delayPredicate(task)) {
                // make default delay
                int delay = 2;
                try {
                    delay = Integer.valueOf(task.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new DelayThoughtStep(delay);
            }
        }
        return new TextThougthStep(task.getContent());
    }

}
