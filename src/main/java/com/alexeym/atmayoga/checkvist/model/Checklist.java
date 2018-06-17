package com.alexeym.atmayoga.checkvist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alexey Matveev on 6/16/2018.
 */
public class Checklist {

    Long id;
    @JsonProperty("percent_completed")
    Double percentCompleted;
    @JsonProperty("task_count")
    Integer taskCount;


}
