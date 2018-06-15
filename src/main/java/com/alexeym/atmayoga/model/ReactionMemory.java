package com.alexeym.atmayoga.model;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.util.Date;

/**
 * @author Alexey Matveev on 15.06.2018
 */
@Document(collection = "reactionMemory", schemaVersion= "1.0")
public class ReactionMemory {

    @Id
    private Date date;
    private String name;
    private String request;
    private String response;

    public ReactionMemory() {
    }

    public ReactionMemory(Date date, String name, String request, String response) {
        this.date = date;
        this.name = name;
        this.request = request;
        this.response = response;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
