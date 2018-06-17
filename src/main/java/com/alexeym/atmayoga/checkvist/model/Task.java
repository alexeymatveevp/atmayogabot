package com.alexeym.atmayoga.checkvist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    Long id;
    @JsonProperty("parent_id")
    Long parentId;
    @JsonProperty("checklist_id")
    Long checklistId;
    int status; // 0 normal, 1 done, 2 invalidated
    @JsonProperty("tasks")
    List<Long> taskIds;
    List<Task> tasks;
    @JsonFormat(pattern = "yyyy/MM/dd")
    Date due;
    String content;
    @JsonProperty("tags_as_text")
    String tagsAsText; // comma-separated: funny,rofl
    TaskColor color; // can be null
    List<TaskNote> notes; // nullable

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagsAsText() {
        return tagsAsText;
    }

    public void setTagsAsText(String tagsAsText) {
        this.tagsAsText = tagsAsText;
    }

    public TaskColor getColor() {
        return color;
    }

    public void setColor(TaskColor color) {
        this.color = color;
    }

    public List<TaskNote> getNotes() {
        return notes;
    }

    public void setNotes(List<TaskNote> notes) {
        this.notes = notes;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }
}
