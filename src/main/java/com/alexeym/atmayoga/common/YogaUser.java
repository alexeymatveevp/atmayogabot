package com.alexeym.atmayoga.common;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Alexey Matveev on 05.06.2018
 */
@Document(collection = "yogaUsers", schemaVersion= "1.0")
public class YogaUser {

    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private int numberOfMessagesThisWeek;
    private Map<String, Practice> practiceMap = new TreeMap<>();
    private List<String> messages = new ArrayList<>();

    public YogaUser() {
    }

    public YogaUser(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, Practice> getPracticeMap() {
        return practiceMap;
    }

    public void setPracticeMap(Map<String, Practice> practiceMap) {
        this.practiceMap = practiceMap;
    }

    public int getNumberOfMessagesThisWeek() {
        return numberOfMessagesThisWeek;
    }

    public void setNumberOfMessagesThisWeek(int numberOfMessagesThisWeek) {
        this.numberOfMessagesThisWeek = numberOfMessagesThisWeek;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
