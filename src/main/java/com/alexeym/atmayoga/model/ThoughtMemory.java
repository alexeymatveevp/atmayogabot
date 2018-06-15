package com.alexeym.atmayoga.model;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.util.Date;

/**
 * @author Alexey Matveev on 15.06.2018
 */
@Document(collection = "thoughtMemory", schemaVersion= "1.0")
public class ThoughtMemory {

    @Id
    private Date date;
    private String name;
    private String type;

    public ThoughtMemory() {
    }

    public ThoughtMemory(Date date, String name, String type) {
        this.date = date;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
